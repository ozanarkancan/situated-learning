package learning.feature;

import learning.core.Dictionary;

public class FeatureFormatterFactory {
	private static FeatureFormatterFactory instance = null;
	private FeatureFormatterContract contract = null;
	
	private FeatureFormatterFactory(){
	}
	
	public static FeatureFormatterFactory getInstance(){
		if(instance == null)
			instance = new FeatureFormatterFactory();
		return instance;
	}
	
	public void setContract(FeatureFormatterContract contract){
		instance.contract = contract;
	}
	
	public IFeatureFormatter getFeatureFormatter() throws Exception{
		if(instance.contract == null)
			throw new Exception("Feature formatter contract is null");
		
		IFeatureFormatter formatter = null;
		Dictionary dictionary = new Dictionary();
		
		if(contract.ngram == 1){
			dictionary.build(contract.vocabularyFileName);
			
			if(contract.stateHistory == 1)
				formatter = new SVMUnigramStateAndWordsFeatureFormatter(dictionary);
			else if(contract.stateHistory == 2)
				formatter = new SVMBigramStateFeatureFormatter(dictionary);
		}else if(contract.ngram == 2){
			dictionary.buildBigram(contract.vocabularyFileName);
			
			if(contract.stateHistory == 2)
				formatter = new SVMBigramStateAndWordsFeatureFormatter(dictionary);
		}
		
		return formatter;
	}

}
