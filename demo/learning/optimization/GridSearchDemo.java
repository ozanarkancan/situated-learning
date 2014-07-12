package learning.optimization;

import learning.classifier.ClassifierContract;
import learning.feature.FeatureFormatterContract;
import learning.optimization.GridSearch.SearchSettings;

public class GridSearchDemo {
	public static void main(String[] args){
		//optimizeMixedBagOfWords(args[0]);
		//optimizeTrigramAction("./resources/InstructionsForGridSearch.xml.aikudata");
		optimizeTrigramAction(args[0]);
	}
	
	public static  void optimizeMixedBagOfWords(String fileName){
		GridSearch gs = new GridSearch();
		
		SearchSettings settings = new SearchSettings();
		settings.paramForC = new Double[]{1.0, 100.0, 0.1};
		settings.paramForEpsilon = new Double[]{0.001, 0.001, 0.1};
		settings.paramForGamma = new Double[]{0.01, 0.1, 0.005};
		
		ClassifierContract classifierContract = new ClassifierContract();
		classifierContract.kernelType = 3;
		
		FeatureFormatterContract formatterContract = new FeatureFormatterContract();
		formatterContract.vocabularyFileName = fileName;
		formatterContract.actionHistory = 0;
		formatterContract.stateHistory = 2;
		formatterContract.ngram = 2;
		
		Double[] result = gs.search(fileName, settings, classifierContract, formatterContract);
		System.out.println(result[0] + ", " + result[1] + ", " + result[2] + " (Gamma, C, Epsilon)");
	}
	
	public static  void optimizeTrigramAction(String fileName){
		GridSearch gs = new GridSearch();
		
		SearchSettings settings = new SearchSettings();
		settings.paramForC = new Double[]{1.0, 1.0, 0.1};
		settings.paramForEpsilon = new Double[]{0.001, 0.001, 0.1};
		settings.paramForGamma = new Double[]{0.01, 0.1, 0.005};
		
		ClassifierContract classifierContract = new ClassifierContract();
		classifierContract.kernelType = 3;
		
		FeatureFormatterContract formatterContract = new FeatureFormatterContract();
		formatterContract.vocabularyFileName = fileName;
		formatterContract.actionHistory = 1;
		formatterContract.stateHistory = 2;
		formatterContract.ngram = 3;
		
		Double[] result = gs.search(fileName, settings, classifierContract, formatterContract);
		System.out.println(result[0] + ", " + result[1] + ", " + result[2] + " (Gamma, C, Epsilon)");
	}

}
