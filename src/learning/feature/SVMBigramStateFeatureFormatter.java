package learning.feature;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import learning.core.Dictionary;

public class SVMBigramStateFeatureFormatter extends SVMDictionaryBasedFeatureFormatter{
	
	public SVMBigramStateFeatureFormatter(Dictionary dictionary) {
		super(dictionary);
		System.out.println("Formatter: SVMBigramStateFeatureFormatter");
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
			String[] words = line.trim().toLowerCase().replace(",", "").split("\\s+");
			String input = bagOfWords(words);
			
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
		return null;
	}
}
