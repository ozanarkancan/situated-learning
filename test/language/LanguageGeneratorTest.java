package language;
import java.util.ArrayList;

import language.LanguageGenerator;
import maze.Cell;
import maze.Maze;
import maze.MazeGenerator;
import maze.MazeSolver;
import maze.display.MazeDisplayer;


public class LanguageGeneratorTest {

	public static void main(String[] args) {
		testGeneration();
	}
	
	public static void testGeneration(){
		Maze maze = MazeGenerator.getInstance().getRandomMaze(8, 8);
		ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
		
		MazeDisplayer.display(maze, path);
		
		LanguageGenerator generator = new LanguageGenerator(maze, path);
		
		for(String instruction : generator.generateInstructions())
			System.out.println(instruction);
		
	}

}
