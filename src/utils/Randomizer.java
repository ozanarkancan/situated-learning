package utils;

import java.util.Random;

public class Randomizer {
	public static int nextInt(int upperBound){
		Random rand = new Random();
		return rand.nextInt(upperBound);
	}

}
