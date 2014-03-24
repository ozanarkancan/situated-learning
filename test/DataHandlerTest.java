import learning.DataHandler;


public class DataHandlerTest {

	public static void main(String[] args) {
		trainingDataGenerationTest();
	}
	
	public static void trainingDataGenerationTest(){
		DataHandler.generateTransitionBasedTrainingData("/home/cano/Desktop/unstructured.test", 25, 12, 12);
	}

}
