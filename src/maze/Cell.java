package maze;

import java.util.ArrayList;

public class Cell {
	private boolean isVisited = false;
	private int row;
	private int column;
	private Wall[] walls;//North, South, West, East
	
	public Cell(){
		walls = new Wall[4];
		
		for(int i = 0; i < 4; i++)
			walls[i] = new Wall();
		
		walls[0].setTarget(this);
		walls[1].setSource(this);
		walls[2].setTarget(this);
		walls[3].setSource(this);
	}
	
	public boolean isVisited() {
		return isVisited;
	}
	
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Wall[] getWalls() {
		return walls;
	}

	public void setWalls(Wall[] walls) {
		this.walls = walls;
	}
	
	public Wall getWall(int index){
		return walls[index];
	}
	
	public void setWall(int index, Wall wall){
		walls[index] = wall;
	}
	
	public ArrayList<Integer> getNotVisitedNeighbours(){
		ArrayList<Integer> notVisitedNeighbours = new ArrayList<Integer>();
		
		Wall northWall = walls[0];
		Cell northCell = northWall.getSource();
		if(northCell != null && !northCell.isVisited())
			notVisitedNeighbours.add(0);
		
		Wall southWall = walls[1];
		Cell southCell = southWall.getTarget();
		if(southCell != null && !southCell.isVisited())
			notVisitedNeighbours.add(1);
		
		Wall westWall = walls[2];
		Cell westCell = westWall.getSource();
		if(westCell != null && !westCell.isVisited())
			notVisitedNeighbours.add(2);
		
		Wall eastWall = walls[3];
		Cell eastCell = eastWall.getTarget();
		if(eastCell != null && !eastCell.isVisited())
			notVisitedNeighbours.add(3);
		
		return notVisitedNeighbours;
	}
}
