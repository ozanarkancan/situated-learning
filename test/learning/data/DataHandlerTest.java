package learning.data;
import learning.data.DataHandler;


public class DataHandlerTest {

	public static void main(String[] args) {
		trainingDataGenerationTest();
	}
	
	public static void trainingDataGenerationTest(){
		DataHandler.generateTransitionBasedTrainingData("/home/cano/situatedLearning/data/unstructured.test", 25, 12, 12);
	}

}
