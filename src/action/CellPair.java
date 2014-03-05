package action;

import maze.Cell;

public class CellPair {
	public Cell start;
	public Cell end;
	
	public int startIndexInPath;
	public int endIndexInPath;
	
	public CellPair(Cell start, Cell end, int startIndex, int endIndex){
		this.start = start;
		this.end = end;
		this.startIndexInPath = startIndex;
		this.endIndexInPath = endIndex;
	}

}
