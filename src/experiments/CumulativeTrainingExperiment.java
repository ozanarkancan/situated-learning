package experiments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;

import learning.evaluation.Evaluator;

public class CumulativeTrainingExperiment extends AbstractExperiment{
	
	public CumulativeTrainingExperiment(ExperimentConfiguration configuration) {
		super(configuration);
	}

	public int splitTrainingFileCumulative(String trainingFile, int atomicActionLimit){
		int count = 0;
		
		while(true){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(trainingFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(trainingFile 
					+ ".part" + Integer.toString(count)));
			
				String line = "";
				int numberOfAtomicActions = 0;
				boolean flagForLimit = false;			
			
				while((line = reader.readLine()) != null){
					writer.append(line).append("\n").flush();
				
					if(line.equals("</maze>")){
						if(numberOfAtomicActions >= atomicActionLimit * (count + 1)){
							count++;
							flagForLimit = true;
							break;
						}
					}
					else if(line.equals(""))
						numberOfAtomicActions++;
				}
				
				reader.close();
				writer.close();
				
				if(!flagForLimit)
					break;
			
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return count;
	}

	@Override
	public void run() {
		try{
			int count = splitTrainingFileCumulative(configuration.trainFile, configuration.atomicActionLimit);
			
			for(int i = 0; i <= count; i++){
				File trainFile = new File(configuration.trainFile + ".part" + Integer.toString(i));
				if(!trainFile.exists()){
					System.out.println(configuration.trainFile + ".part" + Integer.toString(i) + " does not exist!");
					break;
				}
				
				System.out.println("Part" + Integer.toString(i) + "\n");
				
				configuration.classifier.train(trainFile.getPath(), "model");
			
				System.out.println("\nTraining:");
				configuration.classifier.test(trainFile.getPath(), "predict");
			
				Evaluator eval = new Evaluator(configuration.resultFile);
				eval.evaluate(trainFile.getPath() + ".formatted", trainFile.getPath() + ".predict");
				
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
				Files.copy(new File(configuration.testFile).toPath(), 
						new File(configuration.testFile + Integer.toString(i)).toPath());
				String copyTest = configuration.testFile + Integer.toString(i);
				configuration.classifier.test(copyTest, "predict");
				eval = new Evaluator(configuration.resultFile);
				eval.evaluate(copyTest + ".formatted", copyTest + ".predict");
				
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
				
				//Delete files
				new File(trainFile + ".formatted").delete();
				new File(trainFile + ".model").delete();
				new File(trainFile + ".predict").delete();
				trainFile.delete();
				new File(copyTest + ".predict").delete();
				new File(copyTest + ".formatted").delete();
				new File(copyTest).delete();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
