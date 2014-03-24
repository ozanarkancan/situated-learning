package learning.classifier;

import learning.evaluation.Evaluator;

public class LiblinearClassifierTest {

	public static void main(String[] args) {
		//train();
		//loadModel();
		pipe();
	}
	
	public static void train(){
		LiblinearClassifier classifier = new LiblinearClassifier();
		classifier.train("/home/cano/situatedLearning/data/unstructured.train.formatted", "model");
	}
	
	public static void loadModel(){
		LiblinearClassifier classifier = new LiblinearClassifier();
		classifier.loadModel("/home/cano/situatedLearning/data/unstructured."
				+ "train.formatted.model");
	}
	
	public static void test(){
		LiblinearClassifier classifier = new LiblinearClassifier();
		classifier.test("/home/cano/situatedLearning/experiments/unstructured/unstructured.test", "predict");
	}
	
	public static void pipe(){
		
		try {
			LiblinearClassifier classifier = new LiblinearClassifier();
			
			classifier.train("/home/cano/situatedLearning/experiments/unstructured/unstructured.train", "model");
			
			System.out.println("\nTrain:");
			classifier.test("/home/cano/situatedLearning/experiments/unstructured/unstructured.train", "predict");
			
			Evaluator eval = new Evaluator();
			eval.evaluate("/home/cano/situatedLearning/experiments/unstructured/unstructured.train.formatted", 
					"/home/cano/situatedLearning/experiments/unstructured/unstructured.train.predict");
			
			eval.printAccuracy();
			System.out.println();
			eval.printConfusionMatrix();
			/*System.out.println();
			eval.printMissClassifiedInstances();*/
			
			System.out.println("\nTest:");
			classifier.test("/home/cano/situatedLearning/experiments/unstructured/unstructured.test", "predict");
			eval = new Evaluator();
			eval.evaluate("/home/cano/situatedLearning/experiments/unstructured/unstructured.test.formatted", 
					"/home/cano/situatedLearning/experiments/unstructured/unstructured.test.predict");
			
			eval.printAccuracy();
			System.out.println();
			eval.printConfusionMatrix();
			/*System.out.println();
			eval.printMissClassifiedInstances();*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
