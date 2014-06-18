package learning.feature;

public class SVMBigramStateAndWordsFeatureFormatterDemo {
	public static void main(String[] args){
		String train = "/home/cano/situatedLearning/experiments/macmahon_svmrbf/SingleSentence.xml.aikudata";
		FeatureFormatterContract contract = new FeatureFormatterContract();
		contract.vocabularyFileName = train;
		contract.ngram = 2;
		contract.actionHistory = 1;
		contract.stateHistory = 2;
		
		FeatureFormatterFactory.getInstance().setContract(contract);
		
		try {
			IFeatureFormatter formatter = FeatureFormatterFactory.getInstance().getFeatureFormatter();
			formatter.format(train, "formatted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
