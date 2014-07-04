package learning.simulation;

import graph.MacMahonEdge;
import graph.MacMahonGraph;
import graph.MacMahonNode;
import learning.classifier.IClassifier;
import learning.feature.IFeatureFormatter;
import learning.feature.SVMBigramStateAndWordsActionFeatureFormatter;
import learning.feature.SVMBigramStateAndWordsFeatureFormatter;
import learning.feature.SVMBigramStateTrigramWordsActionFeatureFormatter;
import learning.feature.SVMBigramStateTrigramWordsFeatureFormatter;
import learning.instance.IndexedLabel;
import learning.instance.SVMClassifiable;
import learning.instance.SVMFormatInput;
import learning.instance.SingleInstruction;
import agent.MacMahonAgent;

public class Simulator {
	MacMahonAgent agent;
	IClassifier classifier;
	MacMahonGraph map;
	IFeatureFormatter formatter;
	
	public Simulator(IClassifier classifier, MacMahonGraph map, 
			IFeatureFormatter formatter){
		agent = new MacMahonAgent(0, 0, 0);
		this.classifier = classifier;
		this.map = map;
		this.formatter = formatter;
	}
	
	public void setMap(MacMahonGraph map){
		this.map = map;
	}
	
	public void setAgentLocation(int x, int y){
		agent.setLocation(x, y);
	}
	
	public void setAgentOrientation(int orientation){
		agent.setOrientation(orientation);
	}
	
	public boolean agentAtLocation(int x, int y){
		return agent.atLocation(x, y);
	}
	
	public boolean isAgentOrientation(int orientation){
		return agent.isOrientation(orientation);
	}
	
	public boolean commandAgent(SingleInstruction instruction){
		boolean isTerminated = false;
		int actionCount = 0;
		
		String formatted;		
		SVMClassifiable instance;
		IndexedLabel prediction;
		if(formatter instanceof SVMBigramStateTrigramWordsActionFeatureFormatter)
			((SVMBigramStateTrigramWordsActionFeatureFormatter)formatter).previousAction = 0;
		else if(formatter instanceof SVMBigramStateAndWordsActionFeatureFormatter)
			((SVMBigramStateAndWordsActionFeatureFormatter)formatter).previousAction = 0;
		do{
			formatted = formatter.formatSingleInstance(instruction.instruction, 
						map.stateOfNode(map.getNode(agent.getX(), agent.getY()), agent.getOrientation()));

			instance = new SVMClassifiable(new SVMFormatInput(formatted), null);
			prediction = (IndexedLabel) (classifier.classify(instance));
			if(formatter instanceof SVMBigramStateTrigramWordsActionFeatureFormatter)
				((SVMBigramStateTrigramWordsActionFeatureFormatter)formatter).previousAction = prediction.label;
			else if(formatter instanceof SVMBigramStateAndWordsActionFeatureFormatter)
				((SVMBigramStateAndWordsActionFeatureFormatter)formatter).previousAction = prediction.label;
			actionCount++;
			switch (prediction.label) {
			case 0:
				break;
			case 1:
				if(!checkEdge(agent.getOrientation(), agent.getX(), agent.getY()))
					isTerminated = true;
				else{
					agent.move();
					if(!checkMapLimits(agent.getX(), agent.getY()))
						isTerminated = true;
				}
				break;
			case 2:
				agent.turnLeft();
				break;
			case 3:
				agent.turnRight();
				break;
			}
			
			if(actionCount > 100)
				isTerminated = true;
			if(isTerminated)
				break;
		}while(prediction.label != 0);
		if(formatter instanceof SVMBigramStateAndWordsFeatureFormatter)
			((SVMBigramStateAndWordsFeatureFormatter)formatter).prevStateForSingle = map.stateOfNode(map.getNode(instruction.endX,
					instruction.endY), instruction.endOrientation).split("\\s+");
		else if(formatter instanceof SVMBigramStateTrigramWordsFeatureFormatter)
			((SVMBigramStateTrigramWordsFeatureFormatter)formatter).prevStateForSingle = map.stateOfNode(map.getNode(instruction.endX,
					instruction.endY), instruction.endOrientation).split("\\s+");
		else if(formatter instanceof SVMBigramStateTrigramWordsActionFeatureFormatter)
			((SVMBigramStateTrigramWordsActionFeatureFormatter)formatter).prevStateForSingle = map.stateOfNode(map.getNode(instruction.endX,
					instruction.endY), instruction.endOrientation).split("\\s+");
		return isTerminated;
	}
	
	private boolean checkMapLimits(int x, int y){
		return (map.getNode(x, y) != null);
	}
	
	private boolean checkEdge(int direction, int x, int y){
		MacMahonNode source = map.getNode(x, y);
		MacMahonNode target = null;
		
		switch (direction) {
		case 0:
			target = map.getNode(x, y - 1);
			break;
		case 1:
			target = map.getNode(x + 1, y);
			break;
		case 2:
			target = map.getNode(x, y + 1);
			break;
		case 3:
			target = map.getNode(x - 1, y);
			break;
		}
		
		if(target == null)
			return false;
		MacMahonEdge edge = map.getEdge(source, target);
		return edge != null;
	}

}
