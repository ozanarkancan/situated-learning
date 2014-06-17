package learning.feature;

import org.junit.Test;

public class FeatureFormatterFactoryTest {
	@Test
	public void testSVMUnigramStateAndWords(){
		FeatureFormatterContract contract = new FeatureFormatterContract();
		contract.vocabularyFileName = "./resources/aikudata";
		contract.ngram = 1;
		contract.stateHistory = 1;
		contract.actionHistory = 1;
		
		FeatureFormatterFactory.getInstance().setContract(contract);
		
		try {
			IFeatureFormatter formatter = FeatureFormatterFactory.getInstance().getFeatureFormatter();
			org.junit.Assert.assertEquals(true, formatter instanceof SVMUnigramStateAndWordsFeatureFormatter);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.assertFalse(true);
		}
	}
	
	@Test
	public void testSVMBigramState(){
		FeatureFormatterContract contract = new FeatureFormatterContract();
		contract.vocabularyFileName = "./resources/aikudata";
		contract.ngram = 1;
		contract.stateHistory = 2;
		contract.actionHistory = 1;
		
		FeatureFormatterFactory.getInstance().setContract(contract);
		
		try {
			IFeatureFormatter formatter = FeatureFormatterFactory.getInstance().getFeatureFormatter();
			org.junit.Assert.assertEquals(true, formatter instanceof SVMBigramStateFeatureFormatter);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.assertFalse(true);
		}
	}
	
	@Test
	public void testSVMBigramStateAndWords(){
		FeatureFormatterContract contract = new FeatureFormatterContract();
		contract.vocabularyFileName = "./resources/aikudata";
		contract.ngram = 2;
		contract.stateHistory = 2;
		contract.actionHistory = 1;
		
		FeatureFormatterFactory.getInstance().setContract(contract);
		
		try {
			IFeatureFormatter formatter = FeatureFormatterFactory.getInstance().getFeatureFormatter();
			org.junit.Assert.assertEquals(true, formatter instanceof SVMBigramStateAndWordsFeatureFormatter);
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.assertFalse(true);
		}
	}
	
	@Test
	public void testUnknown(){
		FeatureFormatterContract contract = new FeatureFormatterContract();
		contract.vocabularyFileName = "./resources/aikudata";
		contract.ngram = 3;
		contract.stateHistory = 3;
		contract.actionHistory = 2;
		
		FeatureFormatterFactory.getInstance().setContract(contract);
		
		try {
			FeatureFormatterFactory.getInstance().getFeatureFormatter();
			org.junit.Assert.assertFalse(true);
		} catch (Exception e) {
			org.junit.Assert.assertTrue(true);
		}
	}

}
