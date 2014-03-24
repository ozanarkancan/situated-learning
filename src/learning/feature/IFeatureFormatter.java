package learning.feature;

import learning.core.Dictionary;

public interface IFeatureFormatter {
	public void format(String fileName, String extension) throws Exception;
	public void format(Dictionary dict, String fileName, String extension) throws Exception;

}
