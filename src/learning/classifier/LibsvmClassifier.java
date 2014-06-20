package learning.classifier;

import java.io.IOException;
import java.util.StringTokenizer;

import learning.feature.IFeatureFormatter;
import learning.instance.IClassifiable;
import learning.instance.IClassificationLabel;
import learning.instance.IndexedLabel;
import learning.instance.SVMFormatInput;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;

public class LibsvmClassifier implements IClassifier{
	svm_model model;
	String modelFile;
	IFeatureFormatter formatter;
	String[] args;
	
	public LibsvmClassifier(IFeatureFormatter formatter, ClassifierContract contract) {
		this.formatter = formatter;
		//-t, -d, -g, -p, -c
		args = new String[8];
		
		if(contract.kernelType == 2){
			args[0] = "-d";
			args[1] = Integer.toString(contract.d);
		}else if(contract.kernelType == 3){
			args[0] = "-g";
			args[1] = Double.toString(contract.gamma);
		}
		
		args[2] = "-t";
		args[3] = Integer.toString(contract.kernelType - 1);
		args[4] = "-c";
		args[5] = Double.toString(contract.C);
		args[6] = "-p";
		args[7] = Double.toString(contract.epsilon);
	}
	
	@Override
	public void train(String trainFile, String extension) {
		try {
			formatter.format(trainFile, "formatted");
			
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
			formatter.format(testFile, "formatted");
			svm_predict.main(new String[]{testFile + ".formatted", this.modelFile
					, testFile + "." + extension});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setArgs(String[] args){
		this.args = args;
	}
	
	private double classify(String instance){
		double result = 0;
		
		StringTokenizer st = new StringTokenizer(instance," \t\n\r\f:");
		
		int m = st.countTokens()/2;
		svm_node[] x = new svm_node[m];
		
		for(int j=0;j<m;j++)
		{
			x[j] = new svm_node();
			x[j].index = Integer.parseInt(st.nextToken());
			x[j].value = Double.parseDouble(st.nextToken());
		}
		
		result = svm.svm_predict(model,x);
		
		return result;
	}

	@Override
	public IClassificationLabel classify(IClassifiable instance) {
		String input = ((SVMFormatInput)(instance.getInput())).indexedFeatureVector;
		IndexedLabel label = new IndexedLabel((int)classify(input));
		return label;
	}

}
