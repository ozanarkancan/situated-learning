package experiments;

import java.util.HashMap;

import learning.classifier.IClassifier;

public class ExperimentConfiguration {
	public boolean showAtomicAccuracy = true;
	public boolean showActionAccuracy = true;
	public boolean showConfusionMatrix = true;
	public boolean showMissPredictedInstances = false;
	public String trainFile = null;
	public String testFile = null;
	public String resultFile = null;
	public HashMap<String, String> mapFiles = null;
	public int fold = 0;
	public int atomicActionLimit = 0;
	public IClassifier classifier = null;

}
