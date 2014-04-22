package maze;
import java.util.ArrayList;

import maze.Cell;
import maze.Maze;
import maze.MazeGenerator;
import maze.MazeSolver;


public class MazeSolverTest {

	public static void main(String[] args) {
		Maze maze = MazeGenerator.getInstance().getRandomMaze(12, 11);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);

	}

}
