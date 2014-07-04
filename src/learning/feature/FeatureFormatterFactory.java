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
		dictionary.build(contract.vocabularyFileName, contract.ngram);
		
		if(contract.ngram == 1){
			if(contract.stateHistory == 1)
				formatter = new SVMUnigramStateAndWordsFeatureFormatter(dictionary);
			else if(contract.stateHistory == 2)
				formatter = new SVMBigramStateFeatureFormatter(dictionary);
			else
				throw new Exception("Unknown feature formatter");
		}else if(contract.ngram == 2){
			if(contract.stateHistory == 2){
				if(contract.actionHistory == 0)
					formatter = new SVMBigramStateAndWordsFeatureFormatter(dictionary);
				else if(contract.actionHistory == 1)
					formatter = new SVMBigramStateAndWordsActionFeatureFormatter(dictionary);
				else
					throw new Exception("Unknown feature formatter");
			}
			else
				throw new Exception("Unknown feature formatter");
		}else if(contract.ngram == 3){
			if(contract.stateHistory == 2){
				if(contract.actionHistory == 0)
					formatter = new SVMBigramStateTrigramWordsFeatureFormatter(dictionary);
				else if(contract.actionHistory == 1)
					formatter = new SVMBigramStateTrigramWordsActionFeatureFormatter(dictionary);
				else
					throw new Exception("Unknown feature formatter");
			}
			else
				throw new Exception("Unknown feature formatter");
		}else
			throw new Exception("Unknown feature formatter");
		return formatter;
	}

}
