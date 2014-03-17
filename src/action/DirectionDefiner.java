package action;

public class DirectionDefiner {
	public static Direction leftOf(Direction direction){
		int ordinal = direction.ordinal(); 
		int left = ordinal == 0 ? 3 : ordinal - 1;
		
		return Direction.values()[left];	
	}
	
	public static Direction rightOf(Direction direction){
		int ordinal = direction.ordinal(); 
		int left = ordinal == 3 ? 0 : ordinal + 1;
		
		return Direction.values()[left];	
	}
	
	public static Direction oppositeOf(Direction direction){
		int ordinal = direction.ordinal();
		return Direction.values()[(ordinal + 2) % 4];
	}
}
