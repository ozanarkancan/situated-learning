package learning.data;
import learning.data.DataHandler;


public class DataHandlerTest {

	public static void main(String[] args) {
		trainingDataGenerationTest();
	}
	
	public static void trainingDataGenerationTest(){
		DataHandler.generateTransitionBasedData("/home/cano/situatedLearning/data/unstructuredDifferentFromTrain.test", 50, 12, 12, true);
	}

}
