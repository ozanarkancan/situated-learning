package learning.classifier;

import learning.instance.IClassifiable;
import learning.instance.IClassificationLabel;

public interface IClassifier {

	public void train(String trainFile, String extension);
	public void loadModel(String modelFile);
	public void test(String testFile, String extension);
	public IClassificationLabel classify(IClassifiable instance);
}
