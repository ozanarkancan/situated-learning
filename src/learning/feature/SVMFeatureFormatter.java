package learning.feature;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

import learning.core.Dictionary;

public class SVMFeatureFormatter implements IFeatureFormatter {

	Dictionary dict;
	@Override
	public void format(String fileName, String extension) throws Exception{
		
		dict = Dictionary.getInstance();
		dict.build(fileName);
		
		
		format(dict, fileName, extension);
	}
	
	private String bagOfWords(String[] words){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		
		for(String word : words){
			int index = dict.getIndex(word);
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
		
		while((line = reader.readLine()) != null){
			if(line.equals(""))
				continue;
			String[] words = line.trim().toLowerCase().split("\\s+");
			String input = bagOfWords(words);
			
			line = reader.readLine();
			String[] stateInput = line.split("\\s+");
			
			for(int i = 0; i < stateInput.length - 1; i++)
				if(stateInput[i].equals("1"))
					input += Integer.toString(dict.size() + 3 + i) + ":1 ";
			input += Integer.toString(dict.size() + 7) + ":" + stateInput[4];
			String output = reader.readLine().trim();
			writer.append(output + " " + input + "\n").flush();
		}
		
		reader.close();
		writer.close();
		
	}

}
