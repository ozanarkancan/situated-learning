package learning.feature;

public interface IFeatureFormatter {
	public void format(String fileName, String extension) throws Exception;
	public String formatSingleInstance(String instruction, String environment);
}
