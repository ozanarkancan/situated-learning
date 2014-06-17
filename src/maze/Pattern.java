package maze;

import java.awt.Color;

public class Pattern {
	private boolean isActive = true;
	private String name;
	private Color color;
	private int index;
	//private String color;
	
	public Pattern() {
		this.index = 0;
		this.name = "default";
		this.color = Color.GRAY;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;	
		return;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		
		this.index = index;
	
		switch (index) {
		//case 0 : this.name = "default";  this.color = Color.GRAY; break;
		case 0 : this.name = "laminate"; this.color = Color.ORANGE; 	break;
		case 1 : this.name = "cement";   this.color = Color.WHITE;   	break;
		case 2 : this.name = "leafy";    this.color = Color.GREEN;		break;
		case 3 : this.name = "brick";    this.color = Color.LIGHT_GRAY;	break;
		
		}
	}
	
	
}
