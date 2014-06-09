package learning.instance;

public class SVMFormatInput implements IClassificationInput{
	public String indexedFeatureVector = null;
	
	public SVMFormatInput(String indexedFeatureVector){
		this.indexedFeatureVector = indexedFeatureVector;
	}

}
