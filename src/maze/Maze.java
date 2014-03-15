package maze;

import java.util.ArrayList;

public class Maze {
	private int height;
	private int width;
	
	private Cell[][] maze;
	private Cell start;
	private Cell destination;
	
	public Maze(int h, int w){
		this.setHeight(h);
		this.setWidth(w);
		
		maze = new Cell[h][w];
		
		for(int i = 0; i < h; i++){
			for(int j = 0; j < w; j++){
				if(maze[i][j] == null)
					maze[i][j] = new Cell();
				
				maze[i][j].setRow(i);
				maze[i][j].setColumn(j);
				
				if(i != getHeight() - 1){
					if(maze[i+1][j] == null){
						maze[i+1][j] = new Cell();
						maze[i+1][j].setRow(i+1);
						maze[i+1][j].setColumn(j);
					}
					Wall wall = maze[i][j].getWall(2);
					wall.setSource(maze[i][j]);
					wall.setTarget(maze[i+1][j]);
					maze[i+1][j].setWall(0, wall);
				}
				
				if(j != getWidth() - 1){
					if(maze[i][j+1] == null){
						maze[i][j+1] = new Cell();
						maze[i][j+1].setRow(i);
						maze[i][j+1].setColumn(j+1);
					}
					Wall wall = maze[i][j].getWall(1);
					wall.setSource(maze[i][j]);
					wall.setTarget(maze[i][j+1]);
					maze[i][j+1].setWall(3, wall);
				}
			}
		}
	}
	
	public ArrayList<Cell> getAllCells(){
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		for(int i = 0; i < getHeight(); i++){
			for(int j = 0; j < getWidth(); j++){
				cells.add(maze[i][j]);
			}
		}
		
		return cells;
	}
	
	public Cell getCell(int row, int column){
		return maze[row][column];
	}

	public Cell getStart() {
		return start;
	}

	public void setStart(Cell start) {
		this.start = start;
	}

	public Cell getDestination() {
		return destination;
	}

	public void setDestination(Cell destination) {
		this.destination = destination;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
