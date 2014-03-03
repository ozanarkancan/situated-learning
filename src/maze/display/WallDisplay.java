package maze.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import maze.Cell;
import maze.Wall;

public class WallDisplay extends JPanel{
	
	private static final long serialVersionUID = -2495629044315490644L;
	
	Wall wall = null;
	boolean isVertical;
	
	public WallDisplay(Wall wall, boolean isVertical){
		super(new BorderLayout());
		this.wall = wall;
		this.isVertical = isVertical;

	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		if(!wall.isCarved()){
			g.setColor(Color.BLACK);
			Cell target = wall.getTarget();
		
			if(isVertical){//Vertical
				g.fillRect(target.getColumn() * 30, target.getRow() * 30, 5, 30);
			}
			else{//Horizontal
				g.fillRect(target.getColumn() * 30 + 5, target.getRow() * 30, 25, 5);
			}
		}
	}
}
