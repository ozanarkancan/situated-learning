package learning.classifier;

import de.bwaldvogel.liblinear.SolverType;

public class ClassifierContract {
	public double C;
	public double epsilon;
	public int d;
	public double gamma;
	public SolverType solverType;
	public int kernelType;
}
