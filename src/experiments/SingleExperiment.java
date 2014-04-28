package experiments;

import experiments.ExperimentConfiguration.ClassifierType;


public class SingleExperiment implements IExperiment{
	ExperimentConfiguration config;
	
	public SingleExperiment(String outputFile){
		config = new ExperimentConfiguration();
		config.outputFile = outputFile;
	}
	
	
	@Override
	public void run(String trainingFile, String testFile){
		System.out.println("Experiment has been started.\n");
		BaseExperiment experiment = new BaseExperiment(config);
		experiment.run(trainingFile, testFile);
		System.out.println("Experiment has been completed.\n");
	}
	
	public void setClassifier(ClassifierType classifierType, String[] options){
		this.config.classifierType = classifierType;
		if(classifierType == ClassifierType.LIBSVM)
			this.config.svmOptions = options;
	}

}
