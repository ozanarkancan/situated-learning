package utils;

import java.util.Random;

import action.Direction;

public class Randomizer {
	public static int nextInt(int upperBound){
		Random rand = new Random();
		return rand.nextInt(upperBound);
	}
	
	public static Direction nextDirection(){
		Random rand = new Random();
		int random = rand.nextInt(4);
		
		Direction d = null;
		switch(random){
			case 0:
				d = Direction.NORTH;
				break;
			case 1:
				d = Direction.EAST;
				break;
			case 2:
				d = Direction.SOUTH;
				break;
			case 3:
				d = Direction.WEST;
				break;
		}
		
		return d;
	}

}
