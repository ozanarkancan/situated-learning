package agent;

import maze.Cell;
import maze.Wall;
import action.Direction;
import action.DirectionDefiner;

public class Agent {
	private Cell location;
	private Direction direction;
	
	public Agent(Cell location, Direction direction){
		this.location = location;
		this.setDirection(direction);
	}
	
	public void turnRight(){
		direction = DirectionDefiner.rightOf(direction);
	}
	
	public void turnLeft(){
		direction = DirectionDefiner.leftOf(direction);
	}
	
	public void setLocation(Cell location){
		this.location = location;
	}
	
	public Cell getLocation(){
		return location;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public String getStateString(){//Forward, Right, Backward, Left, Direction
		String state = "";
		
		Wall forward = location.getWall(direction.ordinal());
		state += forward.isCarved() ? "0 " : "1 ";
		
		Wall right = location.getWall(DirectionDefiner.rightOf(direction).ordinal());
		state += right.isCarved() ? "0 " : "1 ";
		
		Wall backward = location.getWall(DirectionDefiner.oppositeOf(direction).ordinal());
		state += backward.isCarved() ? "0 " : "1 ";
		
		Wall left = location.getWall(DirectionDefiner.leftOf(direction).ordinal());
		state += left.isCarved() ? "0 " : "1 ";
		
		state += Integer.toString(direction.ordinal());
		return state;
	}

}
