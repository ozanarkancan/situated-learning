package learning.classifier;

import learning.evaluation.Evaluator;
import learning.feature.SVMBigramStateFeatureFormatter;
import learning.feature.SVMFeatureFormatter;

public class LiblinearClassifierTest {

	public static void main(String[] args) {
		//train();
		//loadModel();
		pipe();
	}
	
	public static void train(){
		LiblinearClassifier classifier = new LiblinearClassifier(new SVMFeatureFormatter());
		classifier.train("/home/cano/situatedLearning/data/unstructured.train.formatted", "model");
	}
	
	public static void loadModel(){
		LiblinearClassifier classifier = new LiblinearClassifier(new SVMFeatureFormatter());
		classifier.loadModel("/home/cano/situatedLearning/data/unstructured."
				+ "train.formatted.model");
	}
	
	public static void test(){
		LiblinearClassifier classifier = new LiblinearClassifier(new SVMFeatureFormatter());
		classifier.test("/home/cano/situatedLearning/experiments/unstructured/unstructured.test", "predict");
	}
	
	public static void pipe(){
		
		try {
			LiblinearClassifier classifier = new LiblinearClassifier(new SVMBigramStateFeatureFormatter());
			
			classifier.train("/home/cano/situatedLearning/experiments/unstructured/unstructured.train", "model");
			
			System.out.println("\nTrain:");
			classifier.test("/home/cano/situatedLearning/experiments/unstructured/unstructured.train", "predict");
			
			Evaluator eval = new Evaluator();
			eval.evaluate("/home/cano/situatedLearning/experiments/unstructured/unstructured.train.formatted", 
					"/home/cano/situatedLearning/experiments/unstructured/unstructured.train.predict");
			
			eval.printAtomicActionAccuracy();
			System.out.println();
			eval.printActionAccuracy();
			System.out.println();
			eval.printConfusionMatrix();
			/*System.out.println();
			eval.printMissClassifiedInstances();*/
			
			System.out.println("\nTest:");
			classifier.test("/home/cano/situatedLearning/experiments/unstructured/unstructuredSimilarTrain.test", "predict");
			eval = new Evaluator();
			eval.evaluate("/home/cano/situatedLearning/experiments/unstructured/unstructuredSimilarTrain.test.formatted", 
					"/home/cano/situatedLearning/experiments/unstructured/unstructuredSimilarTrain.test.predict");
			
			eval.printAtomicActionAccuracy();
			System.out.println();
			eval.printActionAccuracy();
			System.out.println();
			eval.printConfusionMatrix();
			/*System.out.println();
			eval.printMissClassifiedInstances();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
