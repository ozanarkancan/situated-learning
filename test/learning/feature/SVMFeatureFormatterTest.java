package learning.feature;


public class SVMFeatureFormatterTest {
	public static void main(String[] args){
		String train = "/home/cano/situatedLearning/experiments/macmahon_svmrbf/SingleSentence.xml.aikudata";
		IFeatureFormatter formatter = new SVMBigramStateAndWordsFeatureFormatter();
		try {
			formatter.format(train, "formatted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
