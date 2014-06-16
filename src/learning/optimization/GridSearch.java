package learning.optimization;

import java.io.File;

import learning.classifier.ClassifierContract;
import learning.classifier.ClassifierFactory;
import learning.classifier.IClassifier;
import learning.evaluation.Evaluator;
import learning.feature.FeatureFormatterContract;
import learning.feature.FeatureFormatterFactory;
import experiments.CVExperiment;

public class GridSearch {
	public class SearchSettings{
		public Double[] paramForC = null;
		public Double[] paramForEpsilon = null;
		public Double[] paramForGamma = null;
	}
	
	//Returns optimum gamma, C, epsilon
	public Double[] search(String fileName, SearchSettings settings, 
			ClassifierContract classifierContract, FeatureFormatterContract formatterContract){
		Double[] results = new Double[3];
		double maxAcc = 0;
		
		try{
			CVExperiment.splitFileCV(fileName, 5);
			FeatureFormatterFactory.getInstance().setContract(formatterContract);
			
			for(double gamma = settings.paramForGamma[0]; gamma <= settings.paramForGamma[1];
					gamma += settings.paramForGamma[2]){
				for(double c = settings.paramForC[0]; c <= settings.paramForC[1];
						c += settings.paramForC[2]){
					for(double epsilon = settings.paramForEpsilon[0]; epsilon <= settings.paramForEpsilon[1];
							epsilon += settings.paramForEpsilon[2]){
						classifierContract.C = c;
						classifierContract.epsilon = epsilon;
						classifierContract.gamma = gamma;
						
						ClassifierFactory.getInstance().setContract(classifierContract);
						IClassifier classifier = ClassifierFactory.getInstance().getClassifier();
						
						for(int i = 0; i < 5; i++){
							File tempTrainFile = new File(fileName + ".trainpart" 
									+ Integer.toString(i));
							File tempTestFile = new File(fileName + ".testpart" 
									+ Integer.toString(i));
							
							classifier.train(tempTrainFile.getPath(), "model");
							classifier.test(tempTestFile.getPath(), "predict");
							
							Evaluator eval = new Evaluator();
							eval.evaluate(tempTestFile.getPath() + ".formatted"
									, tempTestFile.getPath() + ".predict");
							
							if(eval.actionAccuracy() > maxAcc){
								maxAcc = eval.actionAccuracy();
								results[0] = gamma;
								results[1] = c;
								results[2] = epsilon;
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
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return results;
	}

}
