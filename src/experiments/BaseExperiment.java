package experiments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;

import experiments.ExperimentConfiguration.ClassifierType;
import learning.classifier.IClassifier;
import learning.classifier.LiblinearClassifier;
import learning.classifier.LibsvmClassifier;
import learning.evaluation.Evaluator;
import learning.feature.SVMBigramStateFeatureFormatter;

public class BaseExperiment implements IExperiment{
	ExperimentConfiguration configuration;
	
	public BaseExperiment(ExperimentConfiguration configuration){
		this.configuration = configuration;
	}
	
	@Override
	public void run(String trainingFile, String testFile) {
		PrintStream out = null;
		try {
			if(configuration.outputFile != null){
				out = System.out;
				PrintStream stream = new PrintStream(configuration.outputFile);
				System.setOut(stream);
			}
			
			IClassifier classifier;
			
			if(configuration.classifierType == ClassifierType.LIBLINEAR)
				classifier = new LiblinearClassifier(new SVMBigramStateFeatureFormatter());
			else{
				classifier = new LibsvmClassifier(new SVMBigramStateFeatureFormatter());
				String[] args = new String[]{"-t", "1", "-d", "2"};
				((LibsvmClassifier)classifier).setArgs(args);
			}
			
			if(configuration.singleTraining){
				classifier.train(trainingFile, "model");
			
				System.out.println("\nTraining:");
				classifier.test(trainingFile, "predict");
			
				Evaluator eval = new Evaluator();
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
				eval = new Evaluator();
				eval.evaluate(testFile + ".formatted", testFile + ".predict");
				
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
					
					classifier = new LiblinearClassifier(new SVMBigramStateFeatureFormatter());
					
					classifier.train(trainFile.getPath(), "model");
				
					System.out.println("\nTraining:");
					classifier.test(trainFile.getPath(), "predict");
				
					Evaluator eval = new Evaluator();
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
					classifier.test(testFile, "predict");
					eval = new Evaluator();
					eval.evaluate(testFile + ".formatted", testFile + ".predict");
					
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
					
					//trainFile.delete();
					File modelFile = new File(trainFile.getPath() + ".model");
					modelFile.delete();
				}
			}
			else if(configuration.enableCV){
				
			}else{
				System.out.println("No valid choice for experiment!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(configuration.outputFile != null)//Reset default stdout
				System.setOut(out);
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
				
				if(!flagForLimit){
					count++;
					break;
				}
			
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	public void setCLassifier(ClassifierType classifierType){
		this.configuration.classifierType = classifierType;
	}

}
