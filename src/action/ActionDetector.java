package action;

import java.util.ArrayList;

import maze.Cell;

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
			
			for(int i = 2; i < path.size(); i++){
				curr = ActionDetector.getDirection(end, path.get(i));
				if(prev != curr){
					if(length > 2){
						paths.add(new CellPair(start, end));
					}
					start = end;
					end = path.get(i);
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
}
