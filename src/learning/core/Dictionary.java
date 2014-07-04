package learning.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import utils.Tokenizer;

public class Dictionary {
	private static Dictionary builded = null;
	private HashMap<String, Integer> dict;
	
	public Dictionary(){
		dict = new HashMap<String, Integer>();
	}
	
	public static Dictionary getBuildedInstance(){
		return builded;
	}
	
	public void build(String fileName, int ngram) throws Exception{
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = "";
		
		while((line = reader.readLine()) != null){
			if(line.equals("") || line.equals("<maze>") || line.equals("</maze>"))
				continue;
			
			String[] words = Tokenizer.clean(line).toLowerCase().trim().split("\\s+");
			for(int i = 1; i <= ngram; i++){
				for(int j = 0; j <= words.length - i; j++){
					String word = "";
					for(int k = j; k < j + i; k++)
						word += words[k];
					if(!dict.containsKey(word))
						dict.put(word, dict.size() + 1);
				}
			}
			line = reader.readLine();
			line = reader.readLine();
		}
		
		reader.close();
		builded = this;
	}
	
	public void save(String fileName) throws Exception{
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		
		for(String word : dict.keySet()){
			writer.append(word).append(":")
				.append(Integer.toString(dict.get(word))).append("\n");
			writer.flush();
		}
		
		writer.close();
	}
	
	public void load(String fileName) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = "";
		
		dict = new HashMap<String, Integer>();
		
		while((line = reader.readLine()) != null){
			if(line.equals(""))
				continue;
			
			String[] parts = line.split(":");
			dict.put(parts[0], Integer.parseInt(parts[1]));
		}
		
		reader.close();
	}
	
	public int size(){
		return dict.size();
	}
	
	public int getIndex(String word){
		if(dict.containsKey(word))
			return dict.get(word);
		else
			return dict.size() + 1;//Unknown word index
	}

}
