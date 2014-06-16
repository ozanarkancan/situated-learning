package learning.classifier;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;

import learning.feature.IFeatureFormatter;
import learning.instance.IClassifiable;
import learning.instance.IClassificationLabel;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;

public class LiblinearClassifier implements IClassifier{
	Model model;
	IFeatureFormatter formatter;
	ClassifierContract contract;
	
	public LiblinearClassifier(IFeatureFormatter formatter, ClassifierContract contract) {
		this.formatter = formatter;
		this.contract = contract;
	}
	
	@Override
	public void train(String trainFile, String extension) {
		try {
			
			formatter.format(trainFile, "formatted");
			Problem problem = Problem.readFromFile(new File(trainFile + ".formatted"), -1);
			Parameter parameter = new Parameter(contract.solverType, contract.C, contract.epsilon);
			model = Linear.train(problem, parameter);
			
			model.save(new File(trainFile + "." + extension));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadModel(String modelFile) {
		try {
			model = Model.load(new File(modelFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void test(String testFile, String extension) {
		try {
			formatter.format(testFile, "formatted");
			Problem problem = Problem.readFromFile(new File(testFile + ".formatted"), -1);
			
			Formatter out = new Formatter(testFile + "." + extension);
			
			for(int i = 0; i < problem.l; i++){
				double prediction = Linear.predict(model, problem.x[i]);
				out.format(Double.toString(prediction) + "\n");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public IClassificationLabel classify(IClassifiable instance) {
		return null;
	}

}
