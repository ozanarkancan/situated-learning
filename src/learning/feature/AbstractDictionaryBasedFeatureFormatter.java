package learning.feature;

import learning.core.Dictionary;

public abstract class AbstractDictionaryBasedFeatureFormatter implements IFeatureFormatter{
	Dictionary dictionary;
	
	public AbstractDictionaryBasedFeatureFormatter(Dictionary dictionary){
		this.dictionary = dictionary;
	}
}
