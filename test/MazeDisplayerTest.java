import java.util.ArrayList;

import maze.Cell;
import maze.Maze;
import maze.MazeGenerator;
import maze.MazeSolver;
import maze.display.MazeDisplayer;


public class MazeDisplayerTest {

	public static void main(String[] args) {
		Maze maze = MazeGenerator.getInstance().getRandomMaze(20, 20);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
		
		MazeDisplayer.display(maze, null);
		MazeDisplayer.display(maze, path);

	}

}
