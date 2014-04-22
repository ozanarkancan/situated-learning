package learning.feature;
import learning.feature.SVMFeatureFormatter;


public class SVMFeatureFormatterTest {
	public static void main(String[] args){
		String train = "/home/cano/situatedLearning/data/unstructured.train";
		SVMFeatureFormatter formatter = new SVMFeatureFormatter();
		try {
			formatter.format(train, "formatted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
