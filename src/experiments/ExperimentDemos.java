package experiments;

import experiments.ExperimentConfiguration.ClassifierType;

public class ExperimentDemos {

	public static void main(String[] args) {
		//test1();
		//test2();
		test3();
		//test4();
		test5();
		test6();
		test7();
		test8();
	}
	
	public static void test1(){
		/*
		 * Train 10k
		 * Test includes similar instruction sentences with train has
		 * Single run
		 * Classifier: liblinear
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single/unstructuredSimilarToTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single/unstructured10k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single/unstructuredSimilarToTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.run(trainingFile, testFile);
	}
	
	public static void test2(){
		/*
		 * Train 10k
		 * Test includes different instruction sentences from train has
		 * Single run
		 * Classifier: liblinear
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single/unstructuredDifferentFromTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single/unstructured10k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single/unstructuredDifferentFromTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.run(trainingFile, testFile);
	}
	
	public static void test3(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: liblinear
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/unstructuredDifferentFromTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/unstructured200k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/unstructuredDifferentFromTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/stdout.out";
		int atomicActionLimit = 10000;
		
		CumulativeTrainingExperiment experiment = new CumulativeTrainingExperiment(outputFile, atomicActionLimit);
		experiment.run(trainingFile, testFile);
	}
	
	public static void test4(){
		/*
		 * Train 200k
		 * Test includes similar instruction sentences with train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: liblinear
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/unstructuredSimilarToTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/unstructured200k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/unstructuredSimilarToTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/stdout.out";
		int atomicActionLimit = 10000;
		
		CumulativeTrainingExperiment experiment = new CumulativeTrainingExperiment(outputFile, atomicActionLimit);
		experiment.run(trainingFile, testFile);
	}
	
	public static void test5(){
		/*
		 * Train 10k
		 * Test includes similar instruction sentences with train has
		 * Single run
		 * Classifier: svm, polynomial kernel, degree = 2
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/unstructuredSimilarToTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/unstructured10k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/unstructuredSimilarToTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setCLassifier(ClassifierType.LIBSVM);
		experiment.run(trainingFile, testFile);
	}
	
	public static void test6(){
		/*
		 * Train 10k
		 * Test includes different instruction sentences from train has
		 * Single run
		 * Classifier: svm, polynomial kernel, degree = 2
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructuredDifferentFromTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructured10k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructuredDifferentFromTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setCLassifier(ClassifierType.LIBSVM);
		experiment.run(trainingFile, testFile);
	}
	
	public static void test7(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, polynomial kernel, degree = 2
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/unstructuredDifferentFromTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/unstructured200k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/unstructuredDifferentFromTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/stdout.out";
		int atomicActionLimit = 10000;
		
		CumulativeTrainingExperiment experiment = new CumulativeTrainingExperiment(outputFile, atomicActionLimit);
		experiment.run(trainingFile, testFile);
	}
	
	public static void test8(){
		/*
		 * Train 200k
		 * Test includes similar instruction sentences with train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, polynomial kernel, degree = 2
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/unstructuredSimilarToTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/unstructured200k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/unstructuredSimilarToTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/stdout.out";
		int atomicActionLimit = 10000;
		
		CumulativeTrainingExperiment experiment = new CumulativeTrainingExperiment(outputFile, atomicActionLimit);
		experiment.run(trainingFile, testFile);
	}

}
