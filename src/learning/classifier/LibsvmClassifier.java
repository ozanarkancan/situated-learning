package learning.classifier;

import java.io.IOException;

import learning.core.Dictionary;
import learning.feature.IFeatureFormatter;
import libsvm.svm;
import libsvm.svm_model;

public class LibsvmClassifier implements IClassifier{
	svm_model model;
	String modelFile;
	IFeatureFormatter formatter;
	Dictionary dict;
	String[] args;
	
	public LibsvmClassifier(IFeatureFormatter formatter) {
		this.formatter = formatter;
		dict = Dictionary.getInstance();
	}
	
	@Override
	public void train(String trainFile, String extension) {
		try {
			dict.build(trainFile);
			
			formatter.format(dict, trainFile, "formatted");
			
			String[] argv = new String[args.length + 2];
			for(int i = 0; i < args.length; i++)
				argv[i] = args[i];
			argv[argv.length - 2] = trainFile + ".formatted";
			argv[argv.length - 1] = trainFile + ".model";
			
			svm_train trainer = new svm_train();
			trainer.run(argv);
			model = trainer.getModel();
			modelFile = trainFile + ".model";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadModel(String modelFile) {
		try {
			model = svm.svm_load_model(modelFile);
			this.modelFile = modelFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void test(String testFile, String extension) {
		try {
			formatter.format(dict, testFile, "formatted");
			svm_predict.main(new String[]{testFile + ".formatted", this.modelFile
					, testFile + "." + extension});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setArgs(String[] args){
		this.args = args;
	}

}
