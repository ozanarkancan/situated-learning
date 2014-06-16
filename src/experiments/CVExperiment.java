package experiments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import learning.evaluation.Evaluator;

public class CVExperiment extends AbstractExperiment{

	public CVExperiment(ExperimentConfiguration configuration) {
		super(configuration);
	}

	@Override
	public void run() {
		try {
			splitFileCV(configuration.trainFile, configuration.fold);
			for(int i = 0; i < configuration.fold; i++){
				File tempTrainFile = new File(configuration.trainFile + ".trainpart" 
						+ Integer.toString(i));
				File tempTestFile = new File(configuration.trainFile + ".testpart" 
						+ Integer.toString(i));
				
				System.out.println("Part" + Integer.toString(i) + "\n");
				
				configuration.classifier.train(tempTrainFile.getPath(), "model");
			
				System.out.println("\nTraining:");
				configuration.classifier.test(tempTrainFile.getPath(), "predict");
			
				Evaluator eval = new Evaluator(configuration.resultFile);
				eval.evaluate(tempTrainFile.getPath() + ".formatted", tempTrainFile.getPath() + ".predict");
				
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
				configuration.classifier.test(tempTestFile.getPath(), "predict");
				eval = new Evaluator(configuration.resultFile);
				eval.evaluate(tempTestFile.getPath() + ".formatted"
						, tempTestFile.getPath() + ".predict");
				
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
				
				new File(tempTrainFile.getPath() + ".formatted").delete();
				new File(tempTrainFile.getPath() + ".model").delete();
				new File(tempTrainFile.getPath() + ".predict").delete();
				new File(tempTestFile.getPath() + ".predict").delete();
				new File(tempTestFile.getPath() + ".formatted").delete();
				new File(tempTestFile.getPath()).delete();
				tempTrainFile.delete();
				tempTestFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void splitFileCV(String fileName, int numberOfFolds){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
		
			String line = "";
			int numberOfAtomicActions = 0;		
		
			while((line = reader.readLine()) != null){
				if(line.equals(""))
					numberOfAtomicActions++;
			}
			
			reader.close();
			
			int unit = numberOfAtomicActions / numberOfFolds;
			
			for(int i = 0; i < numberOfFolds; i++){
				reader = new BufferedReader(new FileReader(fileName));
				BufferedWriter writerTrain = new BufferedWriter(new FileWriter(fileName + ".train" 
						+ "part" + Integer.toString(i)));
				BufferedWriter writerTest = new BufferedWriter(new FileWriter(fileName + ".test" 
						+ "part" + Integer.toString(i)));
				
				int atomicActionCounter = 0;
				line = "";
				boolean writingToTest = false;
				boolean inInstructionSubset = false;
				
				while((line = reader.readLine()) != null){
					if(atomicActionCounter >= i * unit && atomicActionCounter < (i + 1) * unit){
						if(!writingToTest && inInstructionSubset)
							writerTrain.append(line + "\n").flush();
						else{
							writerTest.append(line + "\n").flush();
							writingToTest = true;
						}
					}else if(i == 4 && atomicActionCounter >= (i + 1) * unit)
						writerTest.append(line + "\n").flush();
					else{
						if(writingToTest && inInstructionSubset)
							writerTest.append(line + "\n").flush();
						else{
							writerTrain.append(line + "\n").flush();
							writingToTest = false;
						}
					}
					
					if(line.equals("<maze>"))
						inInstructionSubset = true;
					else if(line.equals(""))
						atomicActionCounter++;
					else if(line.equals("</maze>"))
						inInstructionSubset = false;
				}
				
				reader.close();
				writerTrain.close();
				writerTest.close();
			}
		
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
