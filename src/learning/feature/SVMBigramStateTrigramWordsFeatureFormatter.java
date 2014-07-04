package learning.feature;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import utils.Tokenizer;
import learning.core.Dictionary;

public class SVMBigramStateTrigramWordsFeatureFormatter extends SVMDictionaryBasedFeatureFormatter{
	public static String[] prevStateForSingle = null;
	
	public SVMBigramStateTrigramWordsFeatureFormatter(Dictionary dictionary) {
		super(dictionary);
		System.out.println("Formatter: SVMBigramStateTrigramWordsFeatureFormatter");
		 prevStateForSingle = null;
	}

	@Override
	public void format(String fileName, String extension) throws Exception{
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "." + extension));
		
		String line = "";
		String[] prevStateInput = null;
		String[] stateInput = null;
		
		while((line = reader.readLine()) != null){
			if(line.equals("") || line.equals("<maze>") || line.equals("</maze>")){
				if(!line.equals(""))
					prevStateInput = null;
				continue;
			}
			String[] words = Tokenizer.clean(line).toLowerCase().trim().split("\\s+");
			ArrayList<String> bigram = new ArrayList<String>();
			
			for(int i = 1; i <= 3; i++){
				for(int j = 0; j <= words.length - i; j++){
					String word = "";
					for(int k = j; k < j + i; k++)
						word += words[k];
					if(!bigram.contains(word))
						bigram.add(word);
				}
			}
			String[] conversion = new String[bigram.size()];
			conversion = bigram.toArray(conversion);
			String input = bagOfWords(conversion);
			
			line = reader.readLine();
			stateInput = line.split("\\s+");
			
			for(int i = 0; i < stateInput.length - 1; i++)
				if(stateInput[i].equals("1"))
					input += Integer.toString(dictionary.size() + 1 + 1 + i) + ":1 ";
			
			input += Integer.toString(dictionary.size() + 1 + stateInput.length 
					+ Integer.parseInt(stateInput[stateInput.length - 1])) + ":1 ";
			
			if(prevStateInput != null){
				for(int i = 0; i < prevStateInput.length - 1; i++)
					if(prevStateInput[i].equals("1"))
						input += Integer.toString(dictionary.size() + 1 + stateInput.length - 1 + 5 + i) + ":1 ";
				input += Integer.toString(dictionary.size() + 1 + 2 * (stateInput.length - 1) + 5 
						+ Integer.parseInt(stateInput[prevStateInput.length - 1])) + ":1 ";
			}
			else
				input += Integer.toString(dictionary.size() + 1 + 2 * (stateInput.length - 1) + 8 + 1) + ":1";
			
			String output = reader.readLine().trim();
			writer.append(output + " " + input + "\n").flush();
			
			prevStateInput = stateInput.clone();
		}
		
		reader.close();
		writer.close();
	}

	@Override
	public String formatSingleInstance(String instruction, String environment) {
		
		String[] words = Tokenizer.clean(instruction).toLowerCase().trim().split("\\s+");
		ArrayList<String> bigram = new ArrayList<String>();
		
		for(int i = 1; i <= 3; i++){
			for(int j = 0; j <= words.length - i; j++){
				String word = "";
				for(int k = j; k < j + i; k++)
					word += words[k];
				if(!bigram.contains(word))
					bigram.add(word);
			}
		}
		String[] conversion = new String[bigram.size()];
		conversion = bigram.toArray(conversion);
		String formatted = bagOfWords(conversion);
		
		String[] stateInput = environment.split("\\s+");
		
		for(int i = 0; i < stateInput.length - 1; i++)
			if(stateInput[i].equals("1"))
				formatted += Integer.toString(dictionary.size() + 1 + 1 + i) + ":1 ";
		
		formatted += Integer.toString(dictionary.size() + 1 + stateInput.length 
				+ Integer.parseInt(stateInput[stateInput.length - 1])) + ":1 ";
		
		if(prevStateForSingle != null){
			for(int i = 0; i < prevStateForSingle.length - 1; i++)
				if(prevStateForSingle[i].equals("1"))
					formatted += Integer.toString(dictionary.size() + 1 + stateInput.length - 1 + 5 + i) + ":1 ";
			formatted += Integer.toString(dictionary.size() + 1 + 2 * (stateInput.length - 1) + 5 
					+ Integer.parseInt(stateInput[prevStateForSingle.length - 1])) + ":1 ";
		}
		else
			formatted += Integer.toString(dictionary.size() + 1 + 2 * (stateInput.length - 1) + 8 + 1) + ":1";
		prevStateForSingle = stateInput;
		
		return formatted;
	}
}
