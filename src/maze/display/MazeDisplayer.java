package maze.display;

import java.util.ArrayList;

import javax.swing.JFrame;

import maze.Cell;
import maze.Maze;

public class MazeDisplayer{
	
	public static void display(Maze maze){
		JFrame frame = new JFrame();
		frame.setSize(maze.getWidth() * 30, (maze.getHeight() + 1) * 30);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MazeDisplay mazeDisp = new MazeDisplay(maze, null);
		frame.getContentPane().add(mazeDisp);
		frame.setVisible(true);
	}
	
	public static void display(Maze maze, ArrayList<Cell> solution){
		JFrame frame = new JFrame();
		frame.setSize(maze.getWidth() * 30, (maze.getHeight() + 1) * 30);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MazeDisplay mazeDisp = new MazeDisplay(maze, solution);
		frame.getContentPane().add(mazeDisp);
		frame.setVisible(true);
	}

}
