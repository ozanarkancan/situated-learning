package language;

import java.util.ArrayList;
import java.util.Arrays;

import javax.jws.Oneway;

import maze.Cell;
import maze.Maze;
import utils.Randomizer;
import action.ActionDetector;
import action.CellPair;
import action.Direction;
import action.TurnDirection;

public class LanguageGenerator {
	
	Maze maze;
	ArrayList<Cell> path;
	
	public LanguageGenerator(Maze maze, ArrayList<Cell> path){
		this.maze = maze;
		this.path = path;
	}
	
	public ArrayList<String> generateInstructions(){
		ArrayList<String> instructions = new ArrayList<String>();
		
		instructions.add(startDirection());
		
		ArrayList<CellPair> pairs = ActionDetector.getStraightPaths(path);
		
		int cellPairIndex = 0;
		CellPair cellPair = pairs.size() == 0 ? null : pairs.get(cellPairIndex);
		
		for(int i = 0; i < path.size() - 1; i++){
			Cell current = path.get(i);
			
			if(cellPair == null || !current.equals(cellPair.start)){
				if(current.equals(maze.getStart()))
					instructions.add(instructionsAboutOneStep(null, current, path.get(i + 1)));
				else if(path.get(i + 1).equals(maze.getDestination()))
					instructions.add(finalOneStep(path.get(i - 1), current, path.get(i + 1)));
				else
					instructions.add(instructionsAboutOneStep(path.get(i - 1), current, path.get(i + 1)));
			}
			else{
				instructions.add(instructionsAboutStraightPath(cellPair));
				i = cellPair.endIndexInPath;
				cellPairIndex++;
				if(cellPairIndex < pairs.size())
					cellPair = pairs.get(cellPairIndex);
				else
					cellPair = null;
			}
		}
		
		return instructions;
	}
	
	private String startDirection(){
		String start = Randomizer.nextInt(2) == 0 ? "Turn your face to the " : "Turn to the ";
		Direction startDirect;
		try {
			startDirect = ActionDetector.getDirection(path.get(0), path.get(1));
			start += startDirect.toString().toLowerCase() + " .";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return start;
	}
	
	private String instructionsAboutOneStep(Cell prev, Cell current, Cell next){
		String instruction = "";
		int rand;
		
		if(prev == null){//Current is start point
			rand = Randomizer.nextInt(3);
			switch (rand){
			case 0:
				instruction = "Pass one block .";
				break;
			case 1:
				instruction = "Move one step .";
				break;
			case 2:
				instruction = "Take a step .";
				break;
			}
			
			return instruction;
		}
		
		TurnDirection turnDirection = ActionDetector.detectTurnDirection(prev, current, next);
		
		try {
			if (turnDirection != TurnDirection.FORWARD) {
				rand = Randomizer.nextInt(9);
				if(rand > 4)
					rand = 0;
				else if(rand > 0)
					rand = 2;
				else
					rand = 1;

				switch (rand) {
				case 0:
					instruction = "Turn " + turnDirection.toString().toLowerCase() + " .";
					break;
				case 1:
					instruction = "Turn your face to "
							+ ActionDetector.getDirection(current, next).toString().toLowerCase() + " ."
							+ " Take a step .";
					break;
				case 2:
					instruction = "Turn " + turnDirection.toString().toLowerCase() + " at the ";
					instruction += ActionDetector.isCorner(current) ? " corner ." : " crossroads.";
					break;
				}
			}
			else{
				rand = Randomizer.nextInt(3);

				switch (rand) {
				case 0:
					instruction = "Pass one block .";
					break;
				case 1:
					instruction = "Move one step .";
					break;
				case 2:
					instruction = "Take a step .";
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if(instruction.equals(""))
			System.out.println("instructionsAboutOneStep");
		return instruction;
	}
	
	private String finalOneStep(Cell prev, Cell current, Cell next){
		String instruction = "";
		int rand;
		
		TurnDirection turnDirection = ActionDetector.detectTurnDirection(prev, current, next);
		
		if (turnDirection == TurnDirection.FORWARD){
			rand = Randomizer.nextInt(3);
			
			switch (rand) {
			case 0:
				instruction = "You will reach the destination after one step .";
				break;
			case 1:
				instruction = "Destination is one block later .";
				break;
			case 2:
				instruction = "Take a step . You will be at the destination .";
				break;
			}
		}
		else{
			rand = Randomizer.nextInt(2);
			
			switch (rand) {
			case 0:
				instruction = "Destination is on the " + turnDirection.toString().toLowerCase() + " .";
				break;
			case 1:
				instruction = "Turn " + turnDirection.toString().toLowerCase() 
					+ " . You will be at the destination .";
				break;
			}
		}
		if(instruction.equals(""))
			System.out.println("finalOneStep");
		return instruction;
	}
	
	private String instructionsAboutStraightPath(CellPair pair){
		String instruction = "";
		try{
			if(pair.end.equals(maze.getDestination()))
				instruction = finalStraightMove(pair.endIndexInPath - pair.startIndexInPath);
			else if(ActionDetector.isCrossRoads(pair.end)){
				
				ArrayList<Cell> subPath = new ArrayList<Cell>();
				subPath.addAll(path.subList(pair.startIndexInPath, pair.endIndexInPath));
				
				int numberOfSameTurn = ActionDetector.numberOfSameTurnAlongStraightPath(
						subPath, ActionDetector.getDirection(pair.end, path.get(pair.endIndexInPath + 1)));
			
				if(numberOfSameTurn == 0 || numberOfSameTurn > 4){
					instruction = Randomizer.nextInt(2) == 0 ? 
							movingForwardInstruction(pair.endIndexInPath - pair.startIndexInPath)
							+ " At the crossroads turn " : moveUntil(false);
				}
				else
					instruction = "Go along the street and take the " + orderOfTurn(numberOfSameTurn + 1) + " on the ";
				String turnDirection = ActionDetector.detectTurnDirection(path.get(pair.endIndexInPath - 1),
						path.get(pair.endIndexInPath), path.get(pair.endIndexInPath + 1)).toString().toLowerCase();
				instruction += turnDirection + " .";
			}
			else if(ActionDetector.isCorner(pair.end)){
				instruction = Randomizer.nextInt(2) == 0 ? 
						instruction = movingForwardInstruction(pair.endIndexInPath - pair.startIndexInPath)
						+ " At the corner turn " : moveUntil(true);
				String turnDirection = ActionDetector.detectTurnDirection(path.get(pair.endIndexInPath - 1),
						path.get(pair.endIndexInPath), path.get(pair.endIndexInPath + 1)).toString().toLowerCase();
				instruction += turnDirection + " .";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(instruction.equals(""))
			System.out.println("instructionsAboutStraightPath");
		return instruction;
	}
	
	private String movingForwardInstruction(int numberOfSteps){
		int rand = Randomizer.nextInt(3);
		String instruction = "";
		
		switch(rand){
		case 0:
			instruction = "Go along the street .";
			break;
		case 1:
			instruction = "Go ahead .";
			break;
		case 2:
			instruction = "Walk along the " + Integer.toString(numberOfSteps) + " blocks .";
		}
		if(instruction.equals(""))
			System.out.println("movingForwardInstruction");
		return instruction;
	}
	
	private String moveUntil(boolean isEndCorner){
		int rand = Randomizer.nextInt(2);
		String instruction = "";
		
		switch(rand){
			case 0:
				instruction = isEndCorner ? "Move until the wall . Turn " : "Move until the crossroads . Turn ";
				break;
			case 1:
				instruction = "Walk forward and when you reach ";
				instruction += isEndCorner ? " the wall turn " : " the intersection turn ";
				break;
		}
		if(instruction.equals(""))
			System.out.println("move until");
		return instruction;
	}
	
	private String finalStraightMove(int numberOfSteps){
		int rand = Randomizer.nextInt(2);
		String instruction = movingForwardInstruction(numberOfSteps);
		
		switch(rand){
			case 0: instruction += "At the end of the street you will be at destination .";
				break;
			case 1: instruction += "Destination is at the end of the street .";
				break;
		}
		if(instruction.equals(""))
			System.out.println("finalStraightMove");
		return instruction;
	}
	
	private String orderOfTurn(int count){
		String order = "";
		switch(count){
		case 1:
			order = "first";
			break;
		case 2:
			order = "second";
			break;
		case 3:
			order = "third";
			break;
		case 4:
			order = "fourth";
			break;
		case 5:
			order = "fifth";
		}
		return order;
	}

}
