import java.util.ArrayList;

import action.ActionDetector;
import action.CellPair;
import action.Direction;
import maze.Cell;
import maze.Maze;
import maze.MazeGenerator;
import maze.MazeSolver;
import maze.display.MazeDisplayer;


public class ActionDetectorTest {

	public static void main(String[] args) {
		//testGetDirection();
		//testGetDirectionError();
		testStraightPaths();
		//testCrossRoads();
		//testCorners();
		//testTurnDirections();
	}
	
	public static void testGetDirection(){
		Maze maze = MazeGenerator.getInstance().getRandomMaze(20, 20);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
		
		MazeDisplayer.display(maze, path);
		
		try {
			Direction d = ActionDetector.getDirection(path.get(0), path.get(1));
			System.out.println(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testGetDirectionError(){
		Maze maze = MazeGenerator.getInstance().getRandomMaze(20, 20);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
		
		MazeDisplayer.display(maze, path);
		
		try {
			Direction d = ActionDetector.getDirection(path.get(0), path.get(0));
			System.out.println(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testStraightPaths(){
		Maze maze = MazeGenerator.getInstance().getRandomMaze(10, 10);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
		
		MazeDisplayer.display(maze, path);
		ArrayList<CellPair> pairs = ActionDetector.getStraightPaths(path);
		
		for(CellPair pair : pairs){
			System.out.println("Start(row, column): (" + pair.start.getRow() + ", " + pair.start.getColumn() + ")");
			System.out.println("End(row, column): (" + pair.end.getRow() + ", " + pair.end.getColumn() + ")");
			System.out.println();
		}
		
	}
	
	public static void testCrossRoads(){
		Maze maze = MazeGenerator.getInstance().getRandomMaze(10, 10);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
		
		MazeDisplayer.display(maze, path);
		
		for(int i = 0; i < path.size(); i++)
			System.out.println(i + ": " + ActionDetector.isCrossRoads(path.get(i)));
		
	}
	
	public static void testCorners(){
		Maze maze = MazeGenerator.getInstance().getRandomMaze(10, 10);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
		
		MazeDisplayer.display(maze, path);
		
		for(int i = 0; i < path.size(); i++)
			if(ActionDetector.isCorner(path.get(i)))
				System.out.println(i);
	}
	
	public static void testTurnDirections(){
		Maze maze = MazeGenerator.getInstance().getRandomMaze(10, 10);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
		
		MazeDisplayer.display(maze, path);
		
		for(int i = 1; i < path.size() - 1; i++)
			System.out.println(i + ": " + ActionDetector.detectTurnDirection(path.get(i - 1), path.get(i), path.get(i + 1)));
	}

}
