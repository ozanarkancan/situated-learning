package learning.optimization;

import learning.classifier.ClassifierContract;
import learning.feature.FeatureFormatterContract;
import learning.optimization.GridSearch.SearchSettings;

import org.junit.Test;

public class GridSearchTest {
	
	@Test
	public void testSearch(){
		GridSearch gs = new GridSearch();
		String fileName = "./resources/aikudatasmall";
		
		SearchSettings settings = new SearchSettings();
		settings.paramForC = new Double[]{1.0, 100.0, 0.1};
		settings.paramForEpsilon = new Double[]{0.001, 0.2, 0.0005};
		settings.paramForGamma = new Double[]{0.01, 0.1, 0.005};
		
		ClassifierContract classifierContract = new ClassifierContract();
		classifierContract.kernelType = 3;
		
		FeatureFormatterContract formatterContract = new FeatureFormatterContract();
		formatterContract.vocabularyFileName = fileName;
		formatterContract.actionHistory = 1;
		formatterContract.stateHistory = 2;
		formatterContract.ngram = 2;
		
		
		Double[] result = gs.search(fileName, settings, classifierContract, formatterContract);
		org.junit.Assert.assertEquals(true, result[0] != 0 && result[1] != 0 && result[2] != 0);
		
	}

}
