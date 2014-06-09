package agent;

public class MacMahonAgent {
	private int x;
	private int y;
	
	/*
	 * 0: North
	 * 1: East
	 * 2: South
	 * 3: West
	 * */
	private int orientation;
	
	public MacMahonAgent(int x, int y, int orientation){
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setOrientation(int orientation){
		this.orientation = orientation;
	}
	
	public boolean atLocation(int x, int y){
		return this.x == x && this.y == y;
	}
	
	public boolean isOrientation(int orientation){
		return this.orientation == orientation;
	}
	
	public boolean move(){
		switch (orientation) {
		case 0:
			y--;
			break;
		case 1:
			x++;
			break;
		case 2:
			y++;
			break;
		case 3:
			x--;
			break;
		}
		
		return x < 0 || y < 0;
	}
	
	public void turnLeft(){
		if(orientation == 0)
			orientation = 3;
		else
			orientation--;
	}
	
	public void turnRight(){
		if(orientation == 3)
			orientation = 0;
		else
			orientation++;
	}

}
