package experiments;

import experiments.ExperimentConfiguration.ClassifierType;
import experiments.ExperimentConfiguration.FeatureFormatterType;

public class CVExperiment implements IExperiment{
	ExperimentConfiguration config;
	
	public CVExperiment(String outputFile, int fold){
		config = new ExperimentConfiguration();
		config.singleTraining = false;
		config.enableCV = true;
		config.fold = fold;
		config.outputFile = outputFile;
	}

	@Override
	public void run(String trainingFile, String testFile) {
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
	
	public void setFeatureFormatter(FeatureFormatterType featureFormmaterType){
		this.config.featureFormatterType = featureFormmaterType;
	}
}
