package experiment;

import de.bwaldvogel.liblinear.SolverType;
import experiments.AbstractExperiment;
import experiments.CVExperiment;
import experiments.CumulativeTrainingExperiment;
import experiments.ExperimentFactory;
import experiments.SingleExperiment;

public class ExperimentDemos {

	public static void main(String[] args) {
		experiment1();
		//test2();
		//test3();
		//test4();
		//test5();
		//test6();
		//test7();
		//test8();
		//test9();
		//test10();
		//test11();
		//test12();
		//test13();
		//test14();
		//test15();
		//test16();
		//test17();
		//test18();
		//test19();
		//test20();
		//test21();
		//test22();
	}
	
	public static void experiment1(){
		/*
		 * Train 10k
		 * Test includes similar instruction sentences with train has
		 * Single run
		 * Classifier: liblinear
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single/unstructuredSimilarToTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 1 is running");
		exp.run();
		System.out.println("Experiment has finished.");
		//SolverType.L1R_L2LOSS_SVC;
		
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
		
		String trainingFile = "./experiments/unstructured_Train10k_TestDifferentFromTrain_single/unstructured10k.train";
		String testFile = "./experiments/unstructured_Train10k_TestDifferentFromTrain_single/unstructuredDifferentFromTrain.test";
		String outputFile = "./experiments/unstructured_Train10k_TestDifferentFromTrain_single/stdout.out";
		
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
		
		String trainingFile = "./experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/unstructured200k.train";
		String testFile = "./experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/unstructuredDifferentFromTrain.test";
		String outputFile = "./experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/stdout.out";
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
		
		String trainingFile = "./experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/unstructured200k.train";
		String testFile = "./experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/unstructuredSimilarToTrain.test";
		String outputFile = "./experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/stdout.out";
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
		
		String trainingFile = "./experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/unstructured10k.train";
		String testFile = "./experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/unstructuredSimilarToTrain.test";
		String outputFile = "./experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "2"});
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
		
		String trainingFile = "./experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructured10k.train";
		String testFile = "./experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructuredDifferentFromTrain.test";
		String outputFile = "./experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "2"});
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
		
		String trainingFile = "./experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/unstructured200k.train";
		String testFile = "./experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/unstructuredDifferentFromTrain.test";
		String outputFile = "./experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/stdout.out";
		int atomicActionLimit = 10000;
		
		CumulativeTrainingExperiment experiment = new CumulativeTrainingExperiment(outputFile, atomicActionLimit);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "2"});
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
		
		String trainingFile = "./experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/unstructured200k.train";
		String testFile = "./experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/unstructuredSimilarToTrain.test";
		String outputFile = "./experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/stdout.out";
		int atomicActionLimit = 10000;
		
		CumulativeTrainingExperiment experiment = new CumulativeTrainingExperiment(outputFile, atomicActionLimit);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "2"});
		experiment.run(trainingFile, testFile);
	}
	
	public static void test9(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, polynomial kernel, degree = 3
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d3/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d3/unstructuredDifferentFromTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d3/unstructured200k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d3/unstructuredDifferentFromTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d3/stdout.out";
		int atomicActionLimit = 10000;
		
		CumulativeTrainingExperiment experiment = new CumulativeTrainingExperiment(outputFile, atomicActionLimit);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "3"});
		experiment.run(trainingFile, testFile);
	}
	
	public static void test10(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, polynomial kernel, degree = 4
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d4/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d4/unstructuredDifferentFromTrain.test
		 * */
		
		String trainingFile = "./experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d4/unstructured200k.train";
		String testFile = "./experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d4/unstructuredDifferentFromTrain.test";
		String outputFile = "./experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d4/stdout.out";
		int atomicActionLimit = 10000;
		
		CumulativeTrainingExperiment experiment = new CumulativeTrainingExperiment(outputFile, atomicActionLimit);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "3"});
		experiment.run(trainingFile, testFile);
	}
	
	public static void test11(){
		/*
		 * Train 10k
		 * Test includes different instruction sentences from train has
		 * Single run
		 * Classifier: svm, polynomial kernel, degree = 3
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructuredDifferentFromTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_d3/unstructured10k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_d3/unstructuredDifferentFromTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_d3/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "3"});
		experiment.run(trainingFile, testFile);
	}
	
	public static void test12(){
		/*
		 * Train 10k
		 * Test includes similar instruction sentences with train has
		 * Single run
		 * Classifier: svm, polynomial kernel, degree = 3
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_d3/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_d3/unstructuredSimilarToTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_d3/unstructured10k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_d3/unstructuredSimilarToTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_d3/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "3"});
		experiment.run(trainingFile, testFile);
	}
	
	public static void test13(){
		/*
		 * Train 10k
		 * Test includes similar instruction sentences with train has
		 * Single run
		 * Classifier: svm, rbf
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_rbf/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_rbf/unstructuredSimilarToTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_rbf/unstructured10k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_rbf/unstructuredSimilarToTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_rbf/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "2"});
		experiment.run(trainingFile, testFile);
	}
	
	public static void test14(){
		/*
		 * Train 10k
		 * Test includes different instruction sentences from train has
		 * Single run
		 * Classifier: svm, rbf kernel
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_rbf/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_rbf/unstructuredDifferentFromTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_rbf/unstructured10k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_rbf/unstructuredDifferentFromTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_rbf/stdout.out";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "2"});
		experiment.run(trainingFile, testFile);
	}
	
	public static void test15(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, rbf kernel
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_rbf/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_rbf/unstructuredDifferentFromTrain.test
		 * */
		
		String trainingFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_rbf/unstructured200k.train";
		String testFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_rbf/unstructuredDifferentFromTrain.test";
		String outputFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_rbf/stdout.out";
		int atomicActionLimit = 10000;
		
		CumulativeTrainingExperiment experiment = new CumulativeTrainingExperiment(outputFile, atomicActionLimit);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "2"});
		experiment.run(trainingFile, testFile);
	}
	
	public static void test16(){
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_svmd2/SingleSentence.xml.aikudata
		 * Classifier: svm, Polynomial degree = 2
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String fileName = "/home/cano/situatedLearning/experiments/macmahon_svmd2/SingleSentence.xml.aikudata";
		String outputFile = "/home/cano/situatedLearning/experiments/macmahon_svmd2/stdout";
		int fold = 5;
		
		CVExperiment experiment = new CVExperiment(outputFile, fold);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "2", "-c", "8"});
		experiment.run(fileName, null);
	}
	
	public static void test17(){
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_svmd3/SingleSentence.xml.aikudata
		 * Classifier: svm, Polynomial degree = 3
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String fileName = "/home/cano/situatedLearning/experiments/macmahon_svmd3/SingleSentence.xml.aikudata";
		String outputFile = "/home/cano/situatedLearning/experiments/macmahon_svmd3/stdout";
		int fold = 5;
		
		CVExperiment experiment = new CVExperiment(outputFile, fold);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "1", "-d", "3", "-c", "8"});
		experiment.run(fileName, null);
	}
	
	public static void test18(){
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_svmrbf/SingleSentence.xml.aikudata
		 * Classifier: svm, rbf
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String fileName = "/home/cano/situatedLearning/experiments/macmahon_svmrbf/SingleSentence.xml.aikudata";
		String outputFile = "/home/cano/situatedLearning/experiments/macmahon_svmrbf/stdout";
		int fold = 5;
		
		CVExperiment experiment = new CVExperiment(outputFile, fold);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "2", "-c", "8", "-g", "0.0625"});
		experiment.run(fileName, null);
	}
	
	public static void test19(){
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_liblinear/SingleSentence.xml.aikudata
		 * Classifier: liblinear
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String fileName = "/home/cano/situatedLearning/experiments/macmahon_liblinear/SingleSentence.xml.aikudata";
		String outputFile = "/home/cano/situatedLearning/experiments/macmahon_liblinear/stdout";
		int fold = 5;
		
		CVExperiment experiment = new CVExperiment(outputFile, fold);
		experiment.run(fileName, null);
	}
	
	public static void test20(){
		/*
		 * Leave-one-map-out
		 * Classifier: SVM RBF kernel
		 * 
		 * 
		 * */
		
		String grid = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/SingleSentence.xml.grid";
		String l = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/SingleSentence.xml.l";
		String jelly = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/SingleSentence.xml.jelly";
		String grid_jelly = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/SingleSentence.xml.grid_jelly";
		String grid_l = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/SingleSentence.xml.grid_l";
		String jelly_l = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/SingleSentence.xml.jelly_l";
		String outputFile = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/stdout";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "2", "-c", "22.627416998", "-g", "0.03125"});
		experiment.run(grid_jelly, l);
		experiment.run(grid_l, jelly);
		experiment.run(jelly_l, grid);
		
	}
	
	public static void test21(){
		/*
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_svmrbf/SingleSentence.xml.aikudata
		 * Classifier: svm, rbf
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String fileName = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_bigramwords/SingleSentence.xml.aikudata";
		String outputFile = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_bigramwords/stdout";
		int fold = 5;
		
		CVExperiment experiment = new CVExperiment(outputFile, fold);
		experiment.setFeatureFormatter(FeatureFormatterType.BIGRAMSTATEANDWORD);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "2", "-c", "22.627416998", "-g", "0.03125"});
		experiment.run(fileName, null);
	}
	
	public static void test22(){
		/*
		 * Leave-one-map-out
		 * Classifier: SVM RBF kernel
		 * 
		 * 
		 * */
		
		String grid = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/SingleSentence.xml.grid";
		String l = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/SingleSentence.xml.l";
		String jelly = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/SingleSentence.xml.jelly";
		String grid_jelly = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/SingleSentence.xml.grid_jelly";
		String grid_l = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/SingleSentence.xml.grid_l";
		String jelly_l = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/SingleSentence.xml.jelly_l";
		String outputFile = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/stdout";
		
		SingleExperiment experiment = new SingleExperiment(outputFile);
		experiment.setFeatureFormatter(FeatureFormatterType.BIGRAMSTATEANDWORD);
		experiment.setClassifier(ClassifierType.LIBSVM, new String[]{"-t", "2", "-c", "22.627416998", "-g", "0.03125"});
		experiment.run(grid_jelly, l);
		experiment.run(grid_l, jelly);
		experiment.run(jelly_l, grid);
	}
}
