package learning.instance;

public class Instance {
	private String instruction;
	private String stateVector;
	private String action;
	
	public Instance(String instruction, String stateVector, String action){
		this.instruction = instruction;
		this.stateVector = stateVector;
		this.action = action;
	}
	
	public String getInstruction() {
		return instruction;
	}
	
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	public String getStateVector() {
		return stateVector;
	}
	
	public void setStateVector(String stateVector) {
		this.stateVector = stateVector;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
