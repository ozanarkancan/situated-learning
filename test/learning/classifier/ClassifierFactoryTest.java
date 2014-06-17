package learning.classifier;

import learning.feature.FeatureFormatterContract;
import learning.feature.FeatureFormatterFactory;

import org.junit.Test;

import de.bwaldvogel.liblinear.SolverType;

public class ClassifierFactoryTest {
	
	@Test
	public void testLiblinear(){
		
		setFeatureFormatterContract();
		
		ClassifierContract contract = new ClassifierContract();
		contract.kernelType = 1;
		contract.solverType = SolverType.L1R_L2LOSS_SVC;
		
		ClassifierFactory.getInstance().setContract(contract);
		try {
			IClassifier classifier = ClassifierFactory.getInstance().getClassifier();
			org.junit.Assert.assertEquals(true, classifier instanceof LiblinearClassifier);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testLibsvm(){
		
		setFeatureFormatterContract();
		
		ClassifierContract contract = new ClassifierContract();
		contract.kernelType = 2;
		
		ClassifierFactory.getInstance().setContract(contract);
		try {
			IClassifier classifier = ClassifierFactory.getInstance().getClassifier();
			org.junit.Assert.assertEquals(true, classifier instanceof LibsvmClassifier);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUnknown(){
		
		setFeatureFormatterContract();
		
		ClassifierContract contract = new ClassifierContract();
		contract.kernelType = 5;
		
		ClassifierFactory.getInstance().setContract(contract);
		try {
			IClassifier classifier = ClassifierFactory.getInstance().getClassifier();
			org.junit.Assert.assertFalse(true);
		} catch (Exception e) {
			org.junit.Assert.assertTrue(true);
		}
		
	}
	
	private void setFeatureFormatterContract(){
		FeatureFormatterContract contract = new FeatureFormatterContract();
		contract.vocabularyFileName = "fileName";
		FeatureFormatterFactory.getInstance().setContract(contract);
	}

}
