package maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import utils.Randomizer;

public class MazeGenerator {
	
	public static MazeGenerator instance = null;
	
	private int height = 0;
	private int width = 0;
	private Stack<Cell> cellStack;
	private ArrayList<Cell> notVisitedCells;
	
	private MazeGenerator(){
	}
	
	public static MazeGenerator getInstance(){
		if(instance == null)
			instance = new MazeGenerator();
		return instance;
	}
	
	public Maze getRandomMaze(int height, int width){
		this.height = height;
		this.width = width;
		cellStack = new Stack<Cell>();
		
		Maze maze = new Maze(height, width);
		
		notVisitedCells = maze.getAllCells();
		
		Cell initialCell = maze.getCell(Randomizer.nextInt(height), Randomizer.nextInt(width));
		initialCell.setVisited(true);
		
		notVisitedCells.remove(initialCell);
		
		recursiveBacktracker(maze, initialCell);
		defineRandomStartAndDestination(maze);
		
		return maze;
	}
	
	private void defineRandomStartAndDestination(Maze maze){
		Cell start = maze.getCell(Randomizer.nextInt(height), Randomizer.nextInt(width));
		maze.setStart(start);
		
		Cell destination = null;
		
		do{
			destination = maze.getCell(Randomizer.nextInt(height), Randomizer.nextInt(width));
		}while(start == destination || start.euclideanDistance(destination) < height / 3 + width / 3);
		
		maze.setDestination(destination);
	}
	
	private void recursiveBacktracker(Maze maze, Cell currentCell){
		while(!notVisitedCells.isEmpty()){
			ArrayList<Integer> notVisitedNeighbours = currentCell.getNotVisitedNeighbours();
			
			if(notVisitedNeighbours.size() != 0){
				int randomNeighbour = notVisitedNeighbours.get(
						Randomizer.nextInt(notVisitedNeighbours.size()));
				cellStack.push(currentCell);
				Wall wall = currentCell.getWall(randomNeighbour);
				wall.setCarved(true);
				
				currentCell = (randomNeighbour == 0 || randomNeighbour == 3) 
						? wall.getSource() : wall.getTarget();
				currentCell.setVisited(true);
				notVisitedCells.remove(currentCell);
			}
			else if(!cellStack.isEmpty()){
				currentCell = cellStack.pop();
			}else{
				if(notVisitedCells.size() != 0){
					currentCell = notVisitedCells.get(
							Randomizer.nextInt(notVisitedCells.size()));
					notVisitedCells.remove(currentCell);
				}
			}
			
		}
	}
}
