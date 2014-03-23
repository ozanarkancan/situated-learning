package learning.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class Dictionary {
	private static Dictionary instance = null;
	private HashMap<String, Integer> dict;
	
	private Dictionary(){
		dict = new HashMap<String, Integer>();
	}
	
	public static Dictionary getInstance(){
		if(instance == null)
			instance = new Dictionary();
		return instance;
	}
	
	public void build(String fileName) throws Exception{
		
		dict = new HashMap<String, Integer>();
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = "";
		
		int inputLine = 0;
		
		while((line = reader.readLine()) != null){
			if(line.equals("")){
				inputLine = 0;
				continue;
			}
			
			if(inputLine == 0){
				String[] words = line.trim().toLowerCase().split("\\s+");
				for(String word : words){
					if(!dict.containsKey(word))
						dict.put(word, dict.size() + 1);
				}
			}else
				inputLine++;
		}
		
		reader.close();
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
			return dict.size() + 2;//Unknown word index
	}

}
