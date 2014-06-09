package learning.feature;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

import learning.core.Dictionary;

public class SVMBigramStateAndWordsFeatureFormatter implements IFeatureFormatter{
	Dictionary dict;
	@Override
	public void format(String fileName, String extension) throws Exception{
		
		dict = Dictionary.getInstance();
		//dict.buildBigram(fileName);
		
		
		format(dict, fileName, extension);
	}
	
	private String bagOfWords(String[] words){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		
		for(int i = 1; i < words.length; i++){
			int index = dict.getIndex(words[i - 1] + words[i]);
			if(!indexes.contains(index))
				indexes.add(index);
		}
		
		Collections.sort(indexes);
		
		String bagOfWordsFormatted = "";
		
		for(Integer index : indexes)
			bagOfWordsFormatted += Integer.toString(index) + ":1 ";
		
		return bagOfWordsFormatted;	
	}

	@Override
	public void format(Dictionary dict, String fileName, String extension) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "." + extension));
		
		this.dict = dict;
		String line = "";
		String[] prevStateInput = null;
		String[] stateInput = null;
		
		while((line = reader.readLine()) != null){
			if(line.equals("") || line.equals("<maze>") || line.equals("</maze>")){
				if(!line.equals(""))
					prevStateInput = null;
				continue;
			}
			String[] words = line.trim().toLowerCase().replace(",", "").split("\\s+");
			String input = bagOfWords(words);
			
			line = reader.readLine();
			stateInput = line.split("\\s+");
			
			for(int i = 0; i < stateInput.length - 1; i++)
				if(stateInput[i].equals("1"))
					input += Integer.toString(dict.size() + 1 + 1 + i) + ":1 ";
			
			input += Integer.toString(dict.size() + 1 + stateInput.length 
					+ Integer.parseInt(stateInput[stateInput.length - 1])) + ":1 ";
			
			if(prevStateInput != null){
				for(int i = 0; i < prevStateInput.length - 1; i++)
					if(prevStateInput[i].equals("1"))
						input += Integer.toString(dict.size() + 1 + stateInput.length - 1 + 5 + i) + ":1 ";
				input += Integer.toString(dict.size() + 1 + 2 * (stateInput.length - 1) + 5 
						+ Integer.parseInt(stateInput[prevStateInput.length - 1])) + ":1 ";
			}
			else
				input += Integer.toString(dict.size() + 1 + 2 * (stateInput.length - 1) + 8 + 1) + ":1";
			
			String output = reader.readLine().trim();
			writer.append(output + " " + input + "\n").flush();
			
			prevStateInput = stateInput.clone();
		}
		
		reader.close();
		writer.close();
		
	}

}
