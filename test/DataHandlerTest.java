import learning.DataHandler;


public class DataHandlerTest {

	public static void main(String[] args) {
		trainingDataGenerationTest();
	}
	
	public static void trainingDataGenerationTest(){
		DataHandler.generateTransitionBasedTrainingData("/home/cano/Desktop/unstructured.train", 100, 12, 12);
	}

}
