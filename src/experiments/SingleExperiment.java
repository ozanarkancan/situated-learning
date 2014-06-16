package experiments;

import java.io.File;

import learning.evaluation.Evaluator;



public class SingleExperiment extends AbstractExperiment{
	
	public SingleExperiment(ExperimentConfiguration configuration) {
		super(configuration);
	}

	@Override
	public void run() {
		
		try{
			configuration.classifier.train(configuration.trainFile, "model");
			
			System.out.println("\nTraining:");
			configuration.classifier.test(configuration.trainFile, "predict");
		
			Evaluator eval = new Evaluator(configuration.resultFile);
			eval.evaluate(configuration.trainFile + ".formatted",
					configuration.trainFile + ".predict");
			
			if(configuration.showAtomicAccuracy){
				System.out.println();
				eval.printAtomicActionAccuracy();
			}
			
			if(configuration.showActionAccuracy){
				System.out.println();
				eval.printActionAccuracy();
			}
			
			if(configuration.showConfusionMatrix){
				System.out.println();
				eval.printConfusionMatrix();
			}
			
			if(configuration.showMissPredictedInstances){
				System.out.println();
				eval.printMissClassifiedInstances();
			}
		
			System.out.println("\nTesting:");
			configuration.classifier.test(configuration.testFile, "predict");
			eval = new Evaluator(configuration.resultFile);
			eval.evaluate(configuration.testFile + ".formatted",
					configuration.testFile + ".predict");
			
			//Delete files
			new File(configuration.trainFile + ".formatted").delete();
			new File(configuration.trainFile + ".model").delete();
			new File(configuration.trainFile + ".predict").delete();
			new File(configuration.testFile + ".formatted").delete();
			new File(configuration.testFile + ".predict").delete();
			
			if(configuration.showAtomicAccuracy){
				System.out.println();
				eval.printAtomicActionAccuracy();
			}
			
			if(configuration.showActionAccuracy){
				System.out.println();
				eval.printActionAccuracy();
			}
			
			if(configuration.showConfusionMatrix){
				System.out.println();
				eval.printConfusionMatrix();
			}
			
			if(configuration.showMissPredictedInstances){
				System.out.println();
				eval.printMissClassifiedInstances();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
