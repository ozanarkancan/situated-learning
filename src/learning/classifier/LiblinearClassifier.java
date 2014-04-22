package learning.classifier;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;

import learning.core.Dictionary;
import learning.feature.IFeatureFormatter;
import learning.feature.SVMFeatureFormatter;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;

public class LiblinearClassifier implements IClassifier{
	Model model;
	IFeatureFormatter formatter;
	Dictionary dict;
	
	public LiblinearClassifier(IFeatureFormatter formatter) {
		this.formatter = formatter;
		dict = Dictionary.getInstance();
	}
	
	@Override
	public void train(String trainFile, String extension) {
		try {
			dict.build(trainFile);
			
			formatter.format(dict, trainFile, "formatted");
			Problem problem = Problem.readFromFile(new File(trainFile + ".formatted"), -1);
			SolverType solverType = SolverType.L1R_L2LOSS_SVC;
			
			double C = 1.2;    // cost of constraints violation
			double eps = 0.001; // stopping criteria
			
			Parameter parameter = new Parameter(solverType, C, eps);
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
			formatter.format(dict, testFile, "formatted");
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

}
