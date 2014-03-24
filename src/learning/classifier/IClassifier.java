package learning.classifier;

public interface IClassifier {

	public void train(String trainFile, String extension);
	public void loadModel(String modelFile);
	public void test(String testFile, String extension);
}
