package maze;

import java.util.ArrayList;

public class Maze {
	private int height;
	private int width;
	
	private Cell[][] maze;
	
	public Maze(int h, int w){
		this.height = h;
		this.width = w;
		
		maze = new Cell[h][w];
		
		for(int i = 0; i < h; i++){
			for(int j = 0; j < w; j++){
				if(maze[i][j] == null)
					maze[i][j] = new Cell();
				
				maze[i][j].setRow(i);
				maze[i][j].setColumn(j);
				
				if(i != height - 1){
					if(maze[i+1][j] == null){
						maze[i+1][j] = new Cell();
						maze[i+1][j].setRow(i+1);
						maze[i+1][j].setColumn(j);
					}
					Wall wall = maze[i][j].getWall(1);
					wall.setSource(maze[i][j]);
					wall.setTarget(maze[i+1][j]);
					maze[i+1][j].setWall(0, wall);
				}
				
				if(j != width - 1){
					if(maze[i][j+1] == null){
						maze[i][j+1] = new Cell();
						maze[i][j+1].setRow(i);
						maze[i][j+1].setColumn(j+1);
					}
					Wall wall = maze[i][j].getWall(3);
					wall.setSource(maze[i][j]);
					wall.setTarget(maze[i][j+1]);
					maze[i][j+1].setWall(2, wall);
				}
			}
		}
	}
	
	public ArrayList<Cell> getAllCells(){
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				cells.add(maze[i][j]);
			}
		}
		
		return cells;
	}
	
	public Cell getCell(int row, int column){
		return maze[row][column];
	}
	
	public void display(){
		for(int i = 0; i < width * 2 + 1; i++){
			if(i % 2 != 0)
				System.out.print('_');
			else
				System.out.print(' ');
		}
		
		System.out.println();
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				Wall[] walls = maze[i][j].getWalls();
				if(!walls[2].isCarved())
					System.out.print('|');
				else
					System.out.print(' ');
				
				if(!walls[1].isCarved())
					System.out.print('_');
				else
					System.out.print(' ');
				
				if(j == width - 1)
					System.out.print('|');
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		Maze maze = new Maze(5, 5);
		maze.display();
	}

}
