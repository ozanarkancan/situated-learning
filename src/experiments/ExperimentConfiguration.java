package experiments;

public class ExperimentConfiguration {
	public enum ClassifierType{
		LIBLINEAR, LIBSVM;
	}
	
	public enum FeatureFormatterType{
		BIGRAMSTATEUNIGRAMWORD, BIGRAMSTATEANDWORD;
	}
	
	public boolean singleTraining = true;
	public boolean showAtomicAccuracy = true;
	public boolean showActionAccuracy = true;
	public boolean showConfusionMatrix = true;
	public boolean showMissPredictedInstances = false;
	public boolean enableCV = false;
	public String outputFile = null;
	public int fold = 0;
	public boolean enableCumulativeTrainingTesting = false;
	public int atomicActionLimit = 0;
	public ClassifierType classifierType = ClassifierType.LIBLINEAR;
	public FeatureFormatterType featureFormatterType = FeatureFormatterType.BIGRAMSTATEUNIGRAMWORD;
	public String[] svmOptions;

}
