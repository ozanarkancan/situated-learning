package learning.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import utils.Randomizer;
import language.LanguageGenerator;
import maze.Cell;
import maze.Maze;
import maze.MazeGenerator;
import maze.MazeSolver;
import maze.Wall;
import maze.display.MazeDisplayer;
import action.ActionDefiner;
import action.CellPair;
import action.Direction;
import action.TurnDirection;
import agent.Agent;

public class DataHandler {
	public static void generateTransitionBasedTrainingData(String fileName){
		generateTransitionBasedData(fileName, 1000, 12, 12, false);
	}
	
	public static void generateTransitionBasedTestData(String fileName){
		generateTransitionBasedData(fileName, 500, 12, 12, true);
	}
	
	public static void generateTransitionBasedData(String fileName, int numberOfMaze, int height, int width, boolean isTestData){
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			DataStatistics.initialize();
			
			for(int m = 0; m < numberOfMaze; m++){
				Maze maze = MazeGenerator.getInstance().getRandomMaze(height, width);
				ArrayList<Cell> path = MazeSolver.getInstance().solveAStar(maze);
				
				writer.append("<maze>\n");
				
				Agent agent = new Agent(maze.getStart(), Randomizer.nextDirection());
				LanguageGenerator langGen = new LanguageGenerator(maze, path);
				if(isTestData)
					langGen.enableTestState();
				
				startState(writer, langGen.startDirection(), agent, path.get(0), path.get(1));
				
				ArrayList<CellPair> pairs = ActionDefiner.getStraightPaths(path);
				
				int cellPairIndex = 0;
				CellPair cellPair = pairs.size() == 0 ? null : pairs.get(cellPairIndex);
				ArrayList<String> instructions;
				String instruction;
				
				for(int i = 0; i < path.size() - 1; i++){
					Cell current = path.get(i);
					
					if(cellPair == null || !current.equals(cellPair.start)){
						if(current.equals(maze.getStart())){
							instruction = langGen.instructionsAboutOneStep(null, current, path.get(i + 1)).get(0);
							
							moveInput(writer, instruction, agent);
							
							DataStatistics.updateUniqueInstruction(instruction);
							DataStatistics.increaseNumberOfActions();
							
							agent.setLocation(path.get(i + 1));
							
							stopInput(writer, instruction, agent);
							DataStatistics.increaseNumberOfActions();
						}
						else{
							
							TurnDirection turnDirection = ActionDefiner
									.detectTurnDirection(path.get(i - 1), current, path.get(i + 1));
							
							if(path.get(i + 1).equals(maze.getDestination()))
								instructions = langGen.finalOneStep(path.get(i - 1), current, path.get(i + 1));
							else
								instructions = langGen.instructionsAboutOneStep(path.get(i - 1), current, path.get(i + 1));
							
							writer.append(instructions.get(0)).append("\n");
							writer.append(agent.getStateString()).append("\n");
							
							if(turnDirection == TurnDirection.FORWARD)
								writer.append("1\n\n");
							else{
								if(turnDirection == TurnDirection.LEFT){
									writer.append("2\n\n");
									agent.turnLeft();
								}
								else{
									writer.append("3\n\n");
									agent.turnRight();
								}
								if(instructions.size() == 2 && !path.get(i + 1).equals(maze.getDestination())){
									stopInput(writer, instructions.get(0), agent);
									DataStatistics.increaseNumberOfActions();
									
									moveInput(writer, instructions.get(1), agent);
									DataStatistics.updateUniqueInstruction(instructions.get(1));
									DataStatistics.increaseNumberOfActions();
								}
								else{
									moveInput(writer, instructions.get(0), agent);
									DataStatistics.increaseNumberOfActions();
								}
							}
							
							DataStatistics.updateUniqueInstruction(instructions.get(0));
							DataStatistics.increaseNumberOfActions();
							
							agent.setLocation(path.get(i + 1));							
							
							if(instructions.size() == 2){
								if(path.get(i + 1).equals(maze.getDestination())){
										stopInput(writer, instructions.get(0), agent);
										DataStatistics.increaseNumberOfActions();
								}
								stopInput(writer, instructions.get(1), agent);
								DataStatistics.updateUniqueInstruction(instructions.get(1));
							}
							else
								stopInput(writer, instructions.get(0), agent);
							
							DataStatistics.increaseNumberOfActions();
						}
					}
					else{//TODO
						instructions = langGen.instructionsAboutStraightPath(cellPair);
						
						int numberOfSteps = cellPair.endIndexInPath - cellPair.startIndexInPath;
						
						for(int j = 0; j < numberOfSteps; j++){
							moveInput(writer, instructions.get(0), agent);
							agent.setLocation(path.get(cellPair.startIndexInPath + j + 1));
							DataStatistics.increaseNumberOfActions();
						}
						
						DataStatistics.updateUniqueInstruction(instructions.get(0));
						
						stopInput(writer, instructions.get(0), agent);
						DataStatistics.increaseNumberOfActions();
						
						if(cellPair.end.equals(maze.getDestination())){
							stopInput(writer, instructions.get(1), agent);
							
							DataStatistics.updateUniqueInstruction(instructions.get(1));
							DataStatistics.increaseNumberOfActions();
						}else{
							TurnDirection turnDirection = ActionDefiner
									.detectTurnDirection(path.get(cellPair.endIndexInPath - 1),
											path.get(cellPair.endIndexInPath), path.get(cellPair.endIndexInPath + 1));
							
							int turnInsIndex = cellPair.endIndexInPath + 1 == path.size() - 1 
									? instructions.size() - 2 : instructions.size() - 1;
							
							if(turnDirection == TurnDirection.LEFT){
								turnLeftInput(writer, instructions.get(turnInsIndex), agent);
								agent.turnLeft();
							}
							else{
								turnRightInput(writer, instructions.get(turnInsIndex), agent);
								agent.turnRight();
							}
							
							if(turnInsIndex != 0)
								DataStatistics.updateUniqueInstruction(instructions.get(turnInsIndex));
							
							DataStatistics.increaseNumberOfActions();
							
							moveInput(writer, instructions.get(turnInsIndex), agent);
							agent.setLocation(path.get(cellPair.endIndexInPath + 1));
							
							DataStatistics.increaseNumberOfActions();
							
							stopInput(writer, instructions.get(turnInsIndex), agent);
							DataStatistics.increaseNumberOfActions();
							
							if(instructions.size() == 3){
								stopInput(writer, instructions.get(2), agent);
								
								DataStatistics.updateUniqueInstruction(instructions.get(2));
								DataStatistics.increaseNumberOfActions();
							}
						}
						
						
						i = cellPair.endIndexInPath;
						cellPairIndex++;
						if(cellPairIndex < pairs.size())
							cellPair = pairs.get(cellPairIndex);
						else
							cellPair = null;
					}
				}
				writer.append("</maze>\n").flush();
			}
			
			writer.close();
			
			DataStatistics.printStatistics();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void stopInput(BufferedWriter writer, String instruction, Agent agent) throws Exception{
		writer.append(instruction).append("\n");
		writer.append(agent.getStateString()).append("\n");
		writer.append("0\n\n");
	}
	
	private static void moveInput(BufferedWriter writer, String instruction, Agent agent) throws Exception{
		writer.append(instruction).append("\n");
		writer.append(agent.getStateString()).append("\n");
		writer.append("1\n\n");
	}
	
	private static void turnLeftInput(BufferedWriter writer, String instruction, Agent agent) throws Exception{
		writer.append(instruction).append("\n");
		writer.append(agent.getStateString()).append("\n");
		writer.append("2\n\n");
	}
	
	private static void turnRightInput(BufferedWriter writer, String instruction, Agent agent) throws Exception{
		writer.append(instruction).append("\n");
		writer.append(agent.getStateString()).append("\n");
		writer.append("3\n\n");
	}
	
	private static void startState(BufferedWriter writer, String instruction, Agent agent, Cell start, Cell next) throws Exception{
		Direction direction = ActionDefiner.getDirection(start, next);
		
		int numberOfTurnLeft = ActionDefiner.numberOfTurnLeft(agent, direction);
		int numberOfTurnRight = ActionDefiner.numberOfTurnRight(agent, direction);
		int randDirect = Randomizer.nextInt(2);
		
		DataStatistics.updateUniqueInstruction(instruction);
		
		if(numberOfTurnLeft != 0){
			if(numberOfTurnLeft < numberOfTurnRight || (numberOfTurnLeft == numberOfTurnRight && randDirect == 0)) {
				for(int i = 0; i < numberOfTurnLeft; i++){
					turnLeftInput(writer, instruction, agent);
					agent.turnLeft();
					DataStatistics.increaseNumberOfActions();
				}
			}else{
				for(int i = 0; i < numberOfTurnRight; i++){
					turnRightInput(writer, instruction, agent);
					agent.turnRight();
					DataStatistics.increaseNumberOfActions();
				}
				
				
			}
		}
		stopInput(writer, instruction, agent);
		agent.setDirection(direction);
		DataStatistics.increaseNumberOfActions();
	}
}
