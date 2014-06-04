package experiments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;

import learning.classifier.IClassifier;
import learning.classifier.LiblinearClassifier;
import learning.classifier.LibsvmClassifier;
import learning.evaluation.Evaluator;
import learning.feature.IFeatureFormatter;
import learning.feature.SVMBigramStateAndWordsFeatureFormatter;
import learning.feature.SVMBigramStateFeatureFormatter;
import experiments.ExperimentConfiguration.ClassifierType;
import experiments.ExperimentConfiguration.FeatureFormatterType;

public class BaseExperiment implements IExperiment{
	ExperimentConfiguration configuration;
	
	public BaseExperiment(ExperimentConfiguration configuration){
		this.configuration = configuration;
	}
	
	@Override
	public void run(String trainingFile, String testFile) {
		try {
			IClassifier classifier;
			
			IFeatureFormatter formatter = configuration.featureFormatterType == FeatureFormatterType.BIGRAMSTATEUNIGRAMWORD
					? new SVMBigramStateFeatureFormatter() : new SVMBigramStateAndWordsFeatureFormatter();
			
			if(configuration.classifierType == ClassifierType.LIBLINEAR)
				classifier = new LiblinearClassifier(formatter);
			else{
				classifier = new LibsvmClassifier(formatter);
				((LibsvmClassifier)classifier).setArgs(configuration.svmOptions);
			}
			
			if(configuration.singleTraining){
				classifier.train(trainingFile, "model");
			
				System.out.println("\nTraining:");
				classifier.test(trainingFile, "predict");
			
				Evaluator eval = new Evaluator(configuration.outputFile);
				eval.evaluate(trainingFile + ".formatted", trainingFile + ".predict");
				
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
				classifier.test(testFile, "predict");
				eval = new Evaluator(configuration.outputFile);
				eval.evaluate(testFile + ".formatted", testFile + ".predict");
				
				//Delete files
				new File(trainingFile + ".formatted").delete();
				new File(trainingFile + ".model").delete();
				new File(trainingFile + ".predict").delete();
				new File(testFile + ".formatted").delete();
				new File(testFile + ".predict").delete();
				
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
			}else if(configuration.enableCumulativeTrainingTesting){
				int count = splitTrainingFileCumulative(trainingFile, configuration.atomicActionLimit);
				
				for(int i = 0; i <= count; i++){
					File trainFile = new File(trainingFile + ".part" + Integer.toString(i));
					if(!trainFile.exists()){
						System.out.println(trainingFile + ".part" + Integer.toString(i) + " does not exist!");
						break;
					}
					
					System.out.println("Part" + Integer.toString(i) + "\n");
					
					classifier.train(trainFile.getPath(), "model");
				
					System.out.println("\nTraining:");
					classifier.test(trainFile.getPath(), "predict");
				
					Evaluator eval = new Evaluator(configuration.outputFile);
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
					Files.copy(new File(testFile).toPath(), new File(testFile + Integer.toString(i)).toPath());
					String copyTest = testFile + Integer.toString(i);
					classifier.test(copyTest, "predict");
					eval = new Evaluator(configuration.outputFile);
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
			}
			else if(configuration.enableCV){
				splitFileCV(trainingFile, configuration.fold);
				for(int i = 0; i < configuration.fold; i++){
					File tempTrainFile = new File(trainingFile + ".trainpart" 
							+ Integer.toString(i));
					File tempTestFile = new File(trainingFile + ".testpart" 
							+ Integer.toString(i));
					
					System.out.println("Part" + Integer.toString(i) + "\n");
					
					classifier.train(tempTrainFile.getPath(), "model");
				
					System.out.println("\nTraining:");
					classifier.test(tempTrainFile.getPath(), "predict");
				
					Evaluator eval = new Evaluator(configuration.outputFile);
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
					classifier.test(tempTestFile.getPath(), "predict");
					eval = new Evaluator(configuration.outputFile);
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
				
			}else{
				System.out.println("No valid choice for experiment!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
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
	
	public void splitFileCV(String fileName, int numberOfFolds){
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
	
	public void setClassifier(ClassifierType classifierType){
		this.configuration.classifierType = classifierType;
	}
	
	public void setFeatureFormatter(FeatureFormatterType featureFormmaterType){
		this.configuration.featureFormatterType = featureFormmaterType;
	}

}
