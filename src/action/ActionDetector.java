package action;

import java.util.ArrayList;

import maze.Cell;
import maze.Wall;

public class ActionDetector {
	
	public static Direction getDirection(Cell source, Cell target) throws Exception{
		if(source.equals(target))
			throw new Exception("Cells are same.");
		if(source.getRow() == target.getRow()){
			if(source.getColumn() > target.getColumn())
				return Direction.WEST;
			else
				return Direction.EAST;
		}
		else{
			if(source.getRow() > target.getRow())
				return Direction.NORTH;
			else
				return Direction.SOUTH;
		}
	}
	
	public static ArrayList<CellPair> getStraightPaths(ArrayList<Cell> path){
		ArrayList<CellPair> paths = new ArrayList<CellPair>();
		
		Cell start = path.get(0);
		Cell end = path.get(1);
		
		try {
			Direction prev = ActionDetector.getDirection(start, end);
			Direction curr;
			int length = 2;
			int startIndex = 0;
			
			for(int i = 2; i < path.size(); i++){
				curr = ActionDetector.getDirection(end, path.get(i));
				if(prev != curr){
					if(length > 2)
						paths.add(new CellPair(start, end, startIndex, i - 1));
					
					if(i < path.size() - 2){
						startIndex = i;
						start = path.get(i);
						end = path.get(i);
						length = 1;
					}
				}
				else{
					end = path.get(i);
					length++;
				}
				
				prev = curr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return paths;
	}
	
	public static boolean isCrossRoads(Cell cell){
		int carvedWalls = 0;
		for(Wall w : cell.getWalls())
			if(w.isCarved())
				carvedWalls++;
		return carvedWalls > 2;
	}
	
	public static boolean isCorner(Cell cell){
		int carvedWalls = 0;
		for(Wall w : cell.getWalls())
			if(w.isCarved())
				carvedWalls++;
		return carvedWalls == 2;
	}
	
	public static TurnDirection detectTurnDirection(Cell previous, Cell current, Cell next){
		try {
			Direction cameFrom = getDirection(previous, current);
			Direction goTo = getDirection(current, next);
			
			if(cameFrom == goTo)
				return TurnDirection.FORWARD;
			else{
				if(cameFrom == Direction.NORTH && goTo == Direction.WEST)
					return TurnDirection.LEFT;
				else if(cameFrom.ordinal() < goTo.ordinal())
					return TurnDirection.RIGHT;
				else if(cameFrom == Direction.WEST && goTo == Direction.NORTH)
					return TurnDirection.RIGHT;
				else
					return TurnDirection.LEFT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int numberOfSameTurnAlongStraightPath(ArrayList<Cell> path, Direction direction){
		int total = 0;
		
		for(Cell cell : path){
			if(isCrossRoads(cell)){
				switch (direction) {
				case NORTH:
					total = cell.getWall(0).isCarved() ? total + 1 : total;
					break;
				case EAST:
					total = cell.getWall(3).isCarved() ? total + 1 : total;
					break;
				case SOUTH:
					total = cell.getWall(1).isCarved() ? total + 1 : total;
					break;
				case WEST:
					total = cell.getWall(2).isCarved() ? total + 1 : total;
					break;
				}
			}
		}
		
		return total;
	}
}
