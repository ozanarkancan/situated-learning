package experiments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import learning.classifier.ClassifierContract;
import learning.classifier.ClassifierFactory;
import learning.classifier.IClassifier;
import learning.feature.FeatureFormatterContract;
import learning.feature.FeatureFormatterFactory;
import de.bwaldvogel.liblinear.SolverType;

public class ExperimentFactory {
	private static ExperimentFactory instance = null;
	
	private ExperimentFactory(){}
	
	public static ExperimentFactory getInstance(){
		if(instance == null)
			instance = new ExperimentFactory();
		return instance;
	}
	
	public AbstractExperiment getExperiment(String configurationFileName){
		AbstractExperiment experiment = null;
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(configurationFileName));
			String line = "";
			HashMap<String, String> params = new HashMap<String, String>();
			
			while((line = reader.readLine()) != null){
				if(line.equals(""))
					continue;
				String[] param = line.split("\\s+");
				params.put(param[0], param[1]);
			}
			
			ExperimentConfiguration configuration = new ExperimentConfiguration();
			
			if(!params.containsKey("experimentType"))
				throw new Exception("experimentType is not defined!");
			else{
				String experimentType = params.get("experimentType");
				if(experimentType.equals("single")){
					if(params.containsKey("testFile"))
						configuration.testFile = params.get("testFile");
					else
						throw new Exception("testFile is not defined!");
					experiment = new SingleExperiment(configuration);
						
				}else if(experimentType.equals("cv")){
					if(params.containsKey("fold"))
						configuration.fold = Integer.parseInt(params.get("fold"));
					else
						throw new Exception("Number of folds is not defined!");
					experiment = new CVExperiment(configuration);
				}else if(experimentType.equals("cumulative")){
					if(params.containsKey("atomicActionLimit"))
						configuration.atomicActionLimit = Integer.parseInt(params.get("atomicActionLimit"));
					else
						throw new Exception("atomicActionLimit is not defined!");
					if(params.containsKey("testFile"))
						configuration.testFile = params.get("testFile");
					else
						throw new Exception("testFile is not defined!");
					
					experiment = new CumulativeTrainingExperiment(configuration);
				}else
					throw new Exception("Unknown experimentType");
				
				if(params.containsKey("trainFile"))
					configuration.trainFile = params.get("trainFile");
				else
					throw new Exception("trainFile is not defined!");
				
				if(params.containsKey("resultFile"))
					configuration.resultFile = params.get("resultFile");
				else
					configuration.resultFile = configuration.trainFile + ".results";
				
				if(params.containsKey("showActionAccuracy"))
					configuration.showActionAccuracy = Boolean.parseBoolean(params.get("showActionAccuracy"));
				if(params.containsKey("showAtomicAccuracy"))
					configuration.showAtomicAccuracy = Boolean.parseBoolean(params.get("showAtomicAccuracy"));
				if(params.containsKey("showConfusionMatrix"))
					configuration.showConfusionMatrix = Boolean.parseBoolean(params.get("showConfusionMatrix"));
				if(params.containsKey("showMissPredictedInstances"))
					configuration.showMissPredictedInstances = Boolean.parseBoolean(params.get("showMissPredictedInstances"));
				
				configuration.classifier = getClassifier(params);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return experiment;
	}
	
	private FeatureFormatterContract getFeatureFormatterContract(HashMap<String, String> params) throws Exception{
		FeatureFormatterContract contract = new FeatureFormatterContract();
		
		if(params.containsKey("ngram"))
			contract.ngram = Integer.parseInt(params.get("ngram"));
		else
			throw new Exception("ngram is not defined!");
		
		if(params.containsKey("stateHistory"))
			contract.stateHistory = Integer.parseInt(params.get("stateHistory"));
		else
			throw new Exception("stateHistory is not defined!");
		
		if(params.containsKey("actionHistory"))
			contract.actionHistory = Integer.parseInt(params.get("actionHistory"));
		else
			throw new Exception("actionHistory is not defined!");
		if(params.containsKey("mixed"))
			contract.mixed = true;
		
		if(params.containsKey("trainFile"))
			contract.vocabularyFileName = params.get("trainFile");
		else
			throw new Exception("trainFile is not defined!");
		
		return contract;
	}
	
	private ClassifierContract getClassifierContract(HashMap<String, String> params) throws Exception{
		ClassifierContract contract = new ClassifierContract();
		String classifierType;
		
		if(params.containsKey("classifierType"))
			classifierType = params.get("classifierType");
		else
			throw new Exception("classifierType is not defined!");
		
		if(classifierType.equals("Liblinear")){
			if(params.containsKey("solverType"))
				contract.solverType = SolverType.valueOf(params.get("solverType"));
			else
				throw new Exception("solverType is not defined!");
			contract.kernelType = 1;
		}else if(classifierType.equals("Libsvm")){
			if(params.containsKey("kernelType"))
				contract.kernelType = Integer.parseInt(params.get("kernelType"));
			else
				throw new Exception("kernelType is not defined!");
			
			if(contract.kernelType == 2){
				if(params.containsKey("d"))
					contract.d = Integer.parseInt(params.get("d"));
				else
					throw new Exception("degree of polynomial(d) is not defined!");
			}else if(contract.kernelType == 3){
				if(params.containsKey("gamma"))
					contract.gamma = Double.parseDouble(params.get("gamma"));
				else
					throw new Exception("gamma(sigma squared) is not defined!");
			}else
				throw new Exception("Unknown kernel type");
		}else
			throw new Exception("Unknown classifierType!");
		
		if(params.containsKey("C"))
			contract.C = Double.parseDouble(params.get("C"));
		else
			throw new Exception("Constraint(C) is not defined!");
		
		if(params.containsKey("epsilon"))
			contract.epsilon = Double.parseDouble(params.get("epsilon"));
		else
			throw new Exception("epsilon is not defined!");
		
		return contract;
	}
	
	private IClassifier getClassifier(HashMap<String, String> params) throws Exception{
		FeatureFormatterFactory.getInstance().setContract(getFeatureFormatterContract(params));
		ClassifierFactory.getInstance().setContract(getClassifierContract(params));
		return ClassifierFactory.getInstance().getClassifier();
	}

}