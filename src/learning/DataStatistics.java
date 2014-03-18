package learning;

import java.util.HashMap;

public class DataStatistics {
	private static HashMap<String, Integer> vocabulary;
	private static HashMap<String, Integer> uniqueInstruction;
	private static int numberOfActions;
	
	public static void initialize(){
		vocabulary = new HashMap<String, Integer>();
		uniqueInstruction = new HashMap<String, Integer>();
		numberOfActions = 0;
	}
	
	private static void updateVocabulary(String instruction){
		String[] words = instruction.toLowerCase().split("\\s+");
		
		for(String word : words){
			if(vocabulary.containsKey(word))
				vocabulary.put(word, vocabulary.get(word) + 1);
			else
				vocabulary.put(word, 1);
		}
	}
	
	public static void updateUniqueInstruction(String instruction){
		if(uniqueInstruction.containsKey(instruction))
			uniqueInstruction.put(instruction, uniqueInstruction.get(instruction));
		else
			uniqueInstruction.put(instruction, 1);
		updateVocabulary(instruction);
	}
	
	public static void increaseNumberOfActions(){
		numberOfActions++;
	}
	
	public static int numberOfTokens(){
		int total = 0;
		
		for(String key : vocabulary.keySet())
			total += vocabulary.get(key);
		
		return total;
	}
	
	public static int numberOfInstructions(){
		int total = 0;
		
		for(String key : uniqueInstruction.keySet())
			total += uniqueInstruction.get(key);
		
		return total;
	}
	
	public static void printStatistics(){
		System.out.println("Vocabulary size: " + vocabulary.size());
		System.out.println("Number of tokens: " + numberOfTokens());
		System.out.println("Unique instuction: " + uniqueInstruction.size());
		System.out.println("Number of actions: " + numberOfActions);
	}

}
