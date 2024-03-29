package language;

import java.util.ArrayList;

import maze.Cell;
import maze.Maze;
import utils.Randomizer;
import action.ActionDefiner;
import action.CellPair;
import action.Direction;
import action.TurnDirection;

public class LanguageGenerator {
	
	Maze maze;
	ArrayList<Cell> path;
	boolean testStateEnabled;
	
	public LanguageGenerator(Maze maze, ArrayList<Cell> path){
		this.maze = maze;
		this.path = path;
		testStateEnabled = false;
	}
	
	public ArrayList<String> generateInstructions(){
		ArrayList<String> instructions = new ArrayList<String>();
		
		instructions.add(startDirection());
		
		ArrayList<CellPair> pairs = ActionDefiner.getStraightPaths(path);
		
		int cellPairIndex = 0;
		CellPair cellPair = pairs.size() == 0 ? null : pairs.get(cellPairIndex);
		
		for(int i = 0; i < path.size() - 1; i++){
			Cell current = path.get(i);
			
			if(cellPair == null || !current.equals(cellPair.start)){
				if(current.equals(maze.getStart()))
					instructions.addAll(instructionsAboutOneStep(null, current, path.get(i + 1)));
				else if(path.get(i + 1).equals(maze.getDestination()))
					instructions.addAll(finalOneStep(path.get(i - 1), current, path.get(i + 1)));
				else
					instructions.addAll(instructionsAboutOneStep(path.get(i - 1), current, path.get(i + 1)));
			}
			else{
				instructions.addAll(instructionsAboutStraightPath(cellPair));
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
	
	public String startDirection(){
		String start = Randomizer.nextInt(2) == 0 ? "Turn your face to the " : "Turn to the ";
		Direction startDirect;
		try {
			startDirect = ActionDefiner.getDirection(path.get(0), path.get(1));
			start += startDirect.toString().toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return start;
	}
	
	public ArrayList<String> instructionsAboutOneStep(Cell prev, Cell current, Cell next){
		ArrayList<String> instructions = new ArrayList<String>();
		int rand;
		
		TurnDirection turnDirection = prev == null ? null : ActionDefiner.detectTurnDirection(prev, current, next);
		
		try {
			if (prev != null && turnDirection != TurnDirection.FORWARD) {
				rand = Randomizer.nextInt(9);
				if(rand > 4)
					rand = 0;
				else if(rand > 0)
					rand = 2;
				else
					rand = 1;

				switch (rand) {
				case 0:
					if(testStateEnabled)
						instructions.add("Go " + turnDirection.toString().toLowerCase());
					else
						instructions.add("Turn " + turnDirection.toString().toLowerCase());
					break;
				case 1:
					if(testStateEnabled){
						instructions.add("Turn to face the street on your "
								+ ActionDefiner.getDirection(current, next).toString().toLowerCase());
						instructions.add("Go one block");
					}else{
						instructions.add("Turn your face to the "
								+ ActionDefiner.getDirection(current, next).toString().toLowerCase());
						instructions.add("Take a step");
					}
					break;
				case 2:
					if(testStateEnabled){
						String instruction = "Go " + turnDirection.toString().toLowerCase() + " at the";
						instruction += ActionDefiner.isCorner(current) ? " corner" : " intersection";
						instructions.add(instruction);
					}else{
						String instruction = "Turn " + turnDirection.toString().toLowerCase() + " at the";
						instruction += ActionDefiner.isCorner(current) ? " corner" : " crossroads";
						instructions.add(instruction);
					}
					break;
				}
			}
			else{
				rand = Randomizer.nextInt(3);
				switch (rand){
				case 0:
					if(testStateEnabled)
						instructions.add("Go one block");
					else
						instructions.add("Pass one block");
					break;
				case 1:
					if(testStateEnabled)
						instructions.add("Walk one block");
					else
						instructions.add("Move one step");
					break;
				case 2:
					if(testStateEnabled)
						instructions.add("Move to forward block");
					else
						instructions.add("Take a step");
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return instructions;
	}
	
	public ArrayList<String> finalOneStep(Cell prev, Cell current, Cell next){
		ArrayList<String> instructions = new ArrayList<String>();
		int rand;
		
		TurnDirection turnDirection = ActionDefiner.detectTurnDirection(prev, current, next);
		
		if (turnDirection == TurnDirection.FORWARD){
			rand = Randomizer.nextInt(3);
			
			switch (rand) {
			case 0:
				if(testStateEnabled)
					instructions.add("You will be at the destination one block later");
				else
					instructions.add("You will reach the destination after one step");
				break;
			case 1:
				if(testStateEnabled)
					instructions.add("One step later you will be at the destination");
				else
					instructions.add("Destination is one block later");
				break;
			case 2:
				if(testStateEnabled){
					instructions.add("Go one block");
					instructions.add("You will reach the destination");
				}else{
					instructions.add("Take a step");
					instructions.add("You will be at the destination");
				}
				break;
			}
		}
		else{
			rand = Randomizer.nextInt(2);
			
			switch (rand) {
			case 0:
				if(testStateEnabled)
					instructions.add("One block on the " + turnDirection.toString().toLowerCase() + " is your destination");
				else
					instructions.add("Destination is on the " + turnDirection.toString().toLowerCase());
				break;
			case 1:
				if(testStateEnabled){
					instructions.add("Take a turn on your " + turnDirection.toString().toLowerCase());
					instructions.add("You will reach the destination block");
				}else{
					instructions.add("Turn " + turnDirection.toString().toLowerCase());
					instructions.add("You will be at the destination");
				}
				break;
			}
		}
		return instructions;
	}
	
	public ArrayList<String> instructionsAboutStraightPath(CellPair pair){
		ArrayList<String> instructions = new ArrayList<String>();
		try{
			if(pair.end.equals(maze.getDestination()))
				instructions.addAll(finalStraightMove(pair.endIndexInPath - pair.startIndexInPath));
			else{
				String turnDirection = ActionDefiner.detectTurnDirection(path.get(pair.endIndexInPath - 1),
						path.get(pair.endIndexInPath), path.get(pair.endIndexInPath + 1)).toString().toLowerCase();
				if(ActionDefiner.isCrossRoads(pair.end)){
				
					ArrayList<Cell> subPath = new ArrayList<Cell>();
					subPath.addAll(path.subList(pair.startIndexInPath, pair.endIndexInPath));
				
					int numberOfSameTurn = ActionDefiner.numberOfSameTurnAlongStraightPath(
							subPath, ActionDefiner.getDirection(pair.end, path.get(pair.endIndexInPath + 1)));
					
					if(numberOfSameTurn == 0 || numberOfSameTurn > 4){
						if(Randomizer.nextInt(2) == 0){
							instructions.add(movingForwardInstruction(pair.endIndexInPath - pair.startIndexInPath));
							if(testStateEnabled)
								instructions.add("Take a turn on your " + turnDirection + " at the crossroads");
							else
								instructions.add("At the crossroads turn " + turnDirection);
						}
						else{
							ArrayList<String> moveUntilIns = moveUntil(false);
							String turnIns = moveUntilIns.get(moveUntilIns.size() - 1);
							turnIns += turnDirection;
							moveUntilIns.remove(moveUntilIns.size() - 1);
							moveUntilIns.add(turnIns);
							instructions.addAll(moveUntilIns);
						}
					}
					else{
						if(testStateEnabled)
							instructions.add("Move along the street and " + 
									"turn at the " + orderOfTurn(numberOfSameTurn + 1) + " " + turnDirection);
						else
							instructions.add("Go along the street and " +
									"take the " + orderOfTurn(numberOfSameTurn + 1) + " on the " + turnDirection);
					}
					
				}
				else if(ActionDefiner.isCorner(pair.end)){
					if(Randomizer.nextInt(2) == 0){
						instructions.add(movingForwardInstruction(pair.endIndexInPath - pair.startIndexInPath));
						if(testStateEnabled)
							instructions.add("Take a turn on your " + turnDirection + " at the corner");
						else
							instructions.add("At the corner turn " + turnDirection);
					}
					else{
						ArrayList<String> moveUntilIns = moveUntil(true);
						String turnIns = moveUntilIns.get(moveUntilIns.size() - 1);
						turnIns += turnDirection;
						moveUntilIns.remove(moveUntilIns.size() - 1);
						moveUntilIns.add(turnIns);
						instructions.addAll(moveUntilIns);
					}
				}
				
				if(pair.endIndexInPath + 1 == path.size() - 1){
					if(testStateEnabled)
						instructions.add("You will reach the destination");
					else
						instructions.add("You will be at the destination");
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return instructions;
	}
	
	public String movingForwardInstruction(int numberOfSteps){
		int rand = Randomizer.nextInt(4);
		String instruction = "";
		
		switch(rand){
		case 0:
			if(testStateEnabled)
				instruction = "Move along the street";
			else
				instruction = "Go along the street";
			break;
		case 1:
			if(testStateEnabled)
				instruction = "Go straight";
			else
				instruction = "Go ahead";
			break;
		case 2:
			if(testStateEnabled)
				instruction = "Take " + Integer.toString(numberOfSteps) + " steps";
			else
				instruction = "Walk along the " + Integer.toString(numberOfSteps) + " blocks";
		case 3:
			if(testStateEnabled)
				instruction = "Go forward";
			else
				instruction = "Walk forward";
		}
		return instruction;
	}
	
	public ArrayList<String> moveUntil(boolean isEndCorner){
		int rand = Randomizer.nextInt(2);
		ArrayList<String> instructions = new ArrayList<String>();
		
		switch(rand){
			case 0:
				if(isEndCorner){
					if(testStateEnabled)
						instructions.add("Walk until you reach the wall");
					else
						instructions.add("Move until the wall");
				}
				else{
					if(testStateEnabled)
						instructions.add("Walk until you reach the crossroads");
					else
						instructions.add("Move until the crossroads");
				}
				
				if(testStateEnabled)
					instructions.add("Go ");
				else
					instructions.add("Turn ");
				break;
			case 1:
				if(testStateEnabled){
					instructions.add("Move along");
					String instruction = isEndCorner ? "When you face the wall turn " : "At the intersection turn ";
					instructions.add(instruction);
				}
				else{
					instructions.add("Walk forward");
					String instruction = "When you reach";
					instruction += isEndCorner ? " the wall turn " : " the intersection turn ";
					instructions.add(instruction);
				}
				break;
		}
		return instructions;
	}
	
	private ArrayList<String> finalStraightMove(int numberOfSteps){
		int rand = Randomizer.nextInt(2);
		ArrayList<String> instructions = new ArrayList<String>(); 
		instructions.add(movingForwardInstruction(numberOfSteps));
		
		if(testStateEnabled)
			instructions.add("You will reach the destination at the end of the street");
		else{
			switch(rand){
			case 0:
				instructions.add("At the end of the street you will be at the destination");
				break;
			case 1:
				instructions.add("Destination is at the end of the street");
				break;
			}
		}
		return instructions;
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
	
	public void enableTestState(){
		testStateEnabled = true;
	}
	
	public void disableTestState(){
		testStateEnabled = false;
	}

}
