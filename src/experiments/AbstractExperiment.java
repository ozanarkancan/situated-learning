package experiments;

public abstract class AbstractExperiment {
	ExperimentConfiguration configuration;
	
	public AbstractExperiment(ExperimentConfiguration configuration){
		this.configuration = configuration;
	}
	
	public abstract void run();

}
