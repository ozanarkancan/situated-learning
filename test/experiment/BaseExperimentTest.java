package experiment;

import experiments.BaseExperiment;
import experiments.ExperimentConfiguration;

public class BaseExperimentTest {

	public static void main(String[] args) {
		testSplitCV();
	}
	
	public static void testSplitCV(){
		String fileName = "/home/cano/situatedLearning/experiments/macmahon/SingleSentence.xml.aikudata";
		BaseExperiment experiment = new BaseExperiment(new ExperimentConfiguration());
		experiment.splitFileCV(fileName, 5);
	}

}
