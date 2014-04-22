package maze;
import java.util.ArrayList;

import maze.Cell;
import maze.Maze;
import maze.MazeGenerator;
import maze.MazeSolver;
import maze.display.MazeDisplayer;


public class MazeDisplayerTest {

	public static void main(String[] args) {
		Maze maze = MazeGenerator.getInstance().getRandomMaze(30, 30);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
		
		MazeDisplayer.display(maze, path);
	}

}
