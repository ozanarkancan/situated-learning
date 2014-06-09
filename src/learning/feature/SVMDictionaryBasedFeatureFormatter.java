package learning.feature;

import java.util.ArrayList;
import java.util.Collections;

import learning.core.Dictionary;

public abstract class SVMDictionaryBasedFeatureFormatter extends AbstractDictionaryBasedFeatureFormatter{

	public SVMDictionaryBasedFeatureFormatter(Dictionary dictionary) {
		super(dictionary);
	}
	
	protected String bagOfWords(String[] words){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		
		for(String word : words){
			int index = dictionary.getIndex(word);
			if(!indexes.contains(index))
				indexes.add(index);
		}
		
		Collections.sort(indexes);
		
		String bagOfWordsFormatted = "";
		
		for(Integer index : indexes)
			bagOfWordsFormatted += Integer.toString(index) + ":1 ";
		
		return bagOfWordsFormatted;	
	}

}
