package learning.feature;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import learning.core.Dictionary;

public class SVMUnigramStateAndWordsFeatureFormatter extends SVMDictionaryBasedFeatureFormatter{

	public SVMUnigramStateAndWordsFeatureFormatter(Dictionary dictionary) {
		super(dictionary);
		System.out.println("Formatter: SVMUnigramStateAndWordsFeatureFormatter");
	}

	@Override
	public void format(String fileName, String extension) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "." + extension));
		
		String line = "";
		
		while((line = reader.readLine()) != null){
			if(line.equals("") || line.equals("<maze>") || line.equals("</maze>"))
				continue;
			String[] words = line.trim().toLowerCase().split("\\s+");
			String input = bagOfWords(words);
			
			line = reader.readLine();
			String[] stateInput = line.split("\\s+");
			
			for(int i = 0; i < stateInput.length - 1; i++)
				if(stateInput[i].equals("1"))
					input += Integer.toString(dictionary.size() + 3 + i) + ":1 ";
			input += Integer.toString(dictionary.size() + 7) + ":" + Integer.toString(Integer.parseInt(stateInput[4]) + 1);
			String output = reader.readLine().trim();
			writer.append(output + " " + input + "\n").flush();
		}
		
		reader.close();
		writer.close();
	}
	

}
