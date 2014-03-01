package maze;

public class Wall {
	private Cell source = null;//North or West
	private Cell target = null;//South or East
	private boolean isCarved = false;
	
	public Cell getSource(){
		return this.source;
	}
	
	public void setSource(Cell source){
		this.source = source;
	}
	
	public Cell getTarget(){
		return this.target;
	}
	
	public void setTarget(Cell target){
		this.target = target;
	}

	public boolean isCarved() {
		return isCarved;
	}

	public void setCarved(boolean isCarved) {
		this.isCarved = isCarved;
	}

}
