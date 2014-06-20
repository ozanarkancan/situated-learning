package experiment;

import de.bwaldvogel.liblinear.SolverType;
import experiments.AbstractExperiment;
import experiments.CVExperiment;
import experiments.CumulativeTrainingExperiment;
import experiments.ExperimentFactory;
import experiments.SingleExperiment;

public class ExperimentDemos {

	public static void main(String[] args) {
		//experiment1();
		//experiment2();
		//experiment3();
		//experiment4();
		//experiment5();
		//experiment6();
		//experiment7();
		//experiment8();
		//experiment9();
		//experiment10();
		//experiment11();
		//experiment12();
		//experiment13();
		//experiment14();
		//experiment15();
		//experiment16();
		//experiment17();
		//experiment18();
		//experiment19();
		//experiment20();
		//experiment21();
		experiment22();
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
		
	}
	
	public static void experiment2(){
		/*
		 * Train 10k
		 * Test includes different instruction sentences from train has
		 * Single run
		 * Classifier: liblinear
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single/unstructuredDifferentFromTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 2 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment3(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: liblinear
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/unstructuredDifferentFromTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 3 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment4(){
		/*
		 * Train 200k
		 * Test includes similar instruction sentences with train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: liblinear
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/unstructuredSimilarToTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 4 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment5(){
		/*
		 * Train 10k
		 * Test includes similar instruction sentences with train has
		 * Single run
		 * Classifier: svm, polynomial kernel, degree = 2
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/unstructuredSimilarToTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 5 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment6(){
		/*
		 * Train 10k
		 * Test includes different instruction sentences from train has
		 * Single run
		 * Classifier: svm, polynomial kernel, degree = 2
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructuredDifferentFromTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 6 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment7(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, polynomial kernel, degree = 2
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/unstructuredDifferentFromTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 7 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment8(){
		/*
		 * Train 200k
		 * Test includes similar instruction sentences with train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, polynomial kernel, degree = 2
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/unstructuredSimilarToTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestSimilarToTrain_cumulative_svm/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 8 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment9(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, polynomial kernel, degree = 3
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d3/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d3/unstructuredDifferentFromTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d3/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 9 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment10(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, polynomial kernel, degree = 4
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d4/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d4/unstructuredDifferentFromTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_d4/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 10 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment11(){
		/*
		 * Train 10k
		 * Test includes different instruction sentences from train has
		 * Single run
		 * Classifier: svm, polynomial kernel, degree = 3
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm/unstructuredDifferentFromTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_d3/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 11 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment12(){
		/*
		 * Train 10k
		 * Test includes similar instruction sentences with train has
		 * Single run
		 * Classifier: svm, polynomial kernel, degree = 3
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_d3/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_d3/unstructuredSimilarToTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_d3/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 12 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment13(){
		/*
		 * Train 10k
		 * Test includes similar instruction sentences with train has
		 * Single run
		 * Classifier: svm, rbf
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_rbf/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_rbf/unstructuredSimilarToTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestSimilarToTrain_single_svm_rbf/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 13 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment14(){
		/*
		 * Train 10k
		 * Test includes different instruction sentences from train has
		 * Single run
		 * Classifier: svm, rbf kernel
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_rbf/unstructured10k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_rbf/unstructuredDifferentFromTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train10k_TestDifferentFromTrain_single_svm_rbf/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 14 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment15(){
		/*
		 * Train 200k
		 * Test includes different instruction sentences from train has
		 * Test training and test instances each ~10k training instances
		 * Classifier: svm, rbf kernel
		 * 
		 * Train File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_rbf/unstructured200k.train
		 * Test File: /home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_rbf/unstructuredDifferentFromTrain.test
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/unstructured_Train200k_TestDifferentFromTrain_cumulative_svm_rbf/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 15 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment16(){
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_svmd2/SingleSentence.xml.aikudata
		 * Classifier: svm, Polynomial degree = 2
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/macmahon_svmd2/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 16 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment17(){
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_svmd3/SingleSentence.xml.aikudata
		 * Classifier: svm, Polynomial degree = 3
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/macmahon_svmd3/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 17 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment18(){
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_svmrbf/SingleSentence.xml.aikudata
		 * Classifier: svm, rbf
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/macmahon_svmrbf/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 18 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment19(){
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_liblinear/SingleSentence.xml.aikudata
		 * Classifier: liblinear
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/macmahon_liblinear/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 19 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment20(){
		/*
		 * Leave-one-map-out
		 * Classifier: SVM RBF kernel
		 * 
		 * 
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/experimentConfigFile";
		String experimentConfigFile2 = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/experimentConfigFile2";
		String experimentConfigFile3 = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout/experimentConfigFile3";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 20 is running");
		exp.run();
		exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile2);
		exp.run();
		exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile3);
		exp.run();
		System.out.println("Experiment has finished.");
		
	}
	
	public static void experiment21(){
		/*
		/*
		 * File:  /home/cano/situatedLearning/experiments/macmahon_svmrbf/SingleSentence.xml.aikudata
		 * Classifier: svm, rbf
		 * 
		 * 5-Fold Cross Validation
		 *
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_bigramwords/experimentConfigFile";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 21 is running");
		exp.run();
		System.out.println("Experiment has finished.");
	}
	
	public static void experiment22(){
		/*
		 * Leave-one-map-out
		 * Classifier: SVM RBF kernel
		 * 
		 * 
		 * */
		
		String experimentConfigFile = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/experimentConfigFile";
		String experimentConfigFile2 = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/experimentConfigFile2";
		String experimentConfigFile3 = "/home/cano/situatedLearning/experiments/macmahon_svmrbf_leaveone_mapout_bigramwords/experimentConfigFile3";
		AbstractExperiment exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile);
		
		System.out.println("Experiment 22 is running");
		exp.run();
		exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile2);
		exp.run();
		exp = ExperimentFactory.getInstance().getExperiment(experimentConfigFile3);
		exp.run();
		System.out.println("Experiment has finished.");
	}
}
