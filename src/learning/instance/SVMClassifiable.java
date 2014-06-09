package learning.instance;

public class SVMClassifiable implements IClassifiable{
	SVMFormatInput input;
	IndexedLabel label;
	
	public SVMClassifiable(SVMFormatInput input, IndexedLabel label){
		this.input = input;
		this.label = label;
	}
	
	
	@Override
	public IClassificationInput getInput() {
		return input;
	}

	@Override
	public IClassificationLabel getLabel() {
		return label;
	}

}
