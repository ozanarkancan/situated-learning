package maze.display;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import utils.Randomizer;

import maze.Cell;
import maze.Maze;
import maze.Wall;
import maze.Pattern;

public class MazeDisplay extends JPanel{
	
	private static final long serialVersionUID = -8092634584395971664L;
	
	
	Maze maze;
	ArrayList<Cell> solution;
	
	public MazeDisplay(Maze maze, ArrayList<Cell> solution){
		this.maze = maze;
		this.solution = solution;
	}
	
	public void paint(Graphics g){

		
		for(int i = 0; i < maze.getHeight(); i++){
			for(int j = 0; j < maze.getWidth(); j++){
				
				Cell current = maze.getCell(i, j);
				Pattern pattern = current.getPattern();
					
				g.setColor(pattern.getColor());
				g.fillRect(j * 30, i * 30, 30, 30);
				g.setColor(Color.BLACK);
				
				
				if(current.equals(maze.getStart())){
					g.setColor(Color.RED);
					g.fillRect(j * 30, i * 30, 30, 30);
					g.setColor(Color.BLACK);
				}else if(current.equals(maze.getDestination())){
					g.setColor(Color.BLUE);
					g.fillRect(j * 30, i * 30, 30, 30);
					g.setColor(Color.BLACK);
				}else if(solution != null && solution.contains(current)){

					g.setColor(Color.CYAN);
					g.fillRect(j * 30 + 12, i * 30 + 12, 6, 6);
					g.setColor(Color.BLACK);
				}

				Wall leftWall = current.getWall(3);
				if(!leftWall.isCarved())
				{
					g.fillRect(leftWall.getTarget().getColumn() * 30, leftWall.getTarget().getRow() * 30, 5, 30);
				}
				
				Wall upperWall = current.getWall(0);	
				if(!upperWall.isCarved())
				{
					g.fillRect(upperWall.getTarget().getColumn() * 30, upperWall.getTarget().getRow() * 30, 30, 5);
				}

				
				if(i == maze.getHeight() - 1){
					g.fillRect(j * 30, maze.getHeight() * 30 - 5, 30, 5);
				}
				
				if(j == maze.getWidth() - 1)
					g.fillRect(maze.getWidth() * 30 - 5, i * 30 - 5, 5, 30);
				
			}
		}
	}

}
