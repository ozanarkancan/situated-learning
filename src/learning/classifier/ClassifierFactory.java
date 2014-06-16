package learning.classifier;

import learning.feature.FeatureFormatterFactory;

public class ClassifierFactory {
	private static ClassifierFactory instance = null;
	private ClassifierContract contract = null;
	
	private ClassifierFactory(){}
	
	public static ClassifierFactory getInstance(){
		if(instance == null)
			instance = new ClassifierFactory();
		return instance;
	}
	
	public void setContract(ClassifierContract contract){
		instance.contract = contract;
	}
	
	public IClassifier getClassifier() throws Exception{
		IClassifier classifier;
		
		if(contract.kernelType == 1)
			classifier = new LiblinearClassifier(FeatureFormatterFactory.getInstance()
					.getFeatureFormatter(), contract);
		else
			classifier = new LibsvmClassifier(FeatureFormatterFactory.getInstance()
					.getFeatureFormatter(), contract);
		
		return classifier;
	}

}
