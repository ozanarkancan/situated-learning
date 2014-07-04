package experiments;

import java.io.File;
import java.util.HashMap;

import graph.MacMahonGraph;
import io.MacMahonSingleInstructionFileReader;
import io.MacMahonSingleInstructionReader;
import learning.evaluation.Evaluator;
import learning.feature.FeatureFormatterFactory;
import learning.instance.SingleInstruction;
import learning.simulation.Simulator;

public class SimulationExperiment extends AbstractExperiment{

	public SimulationExperiment(ExperimentConfiguration configuration) {
		super(configuration);
	}

	@Override
	public void run() {
		try {
			configuration.classifier.train(configuration.trainFile, "model");
			
			HashMap<String, MacMahonGraph> maps = new HashMap<String, MacMahonGraph>();
			for(String mapName : configuration.mapFiles.keySet())
				maps.put(mapName, MacMahonSingleInstructionFileReader.readMap(configuration.mapFiles.get(mapName)));
			
			MacMahonSingleInstructionReader reader = new MacMahonSingleInstructionReader(configuration.testFile);
			
			Evaluator eval = new Evaluator(configuration.resultFile);
			String prevFileName = "";
			Simulator simulator = null;
			int singleInstructionIndex = 0;
			while(reader.hasInstruction()){
				SingleInstruction singleInstruction = reader.nextInstruction();
				singleInstructionIndex++;
				if(!prevFileName.equals(singleInstruction.fileName)){
					simulator = new Simulator(configuration.classifier, maps.get(singleInstruction.mapName), 
						FeatureFormatterFactory.getInstance().getFeatureFormatter());
					prevFileName = singleInstruction.fileName;
				}
				simulator.setAgentLocation(singleInstruction.startX, singleInstruction.startY);
				simulator.setAgentOrientation(singleInstruction.startOrientation);
				boolean validation = simulator.commandAgent(singleInstruction);
				if(validation){
					eval.increaseMissedAtomicAction();
					eval.addMissClassified(singleInstructionIndex);
				}
				else{
					if(simulator.agentAtLocation(singleInstruction.endX, singleInstruction.endY) && 
							simulator.isAgentOrientation(singleInstruction.endOrientation))
						eval.increaseCorrectAtomicAction();
					else{
						eval.increaseMissedAtomicAction();
						eval.addMissClassified(singleInstructionIndex);
					}
				}
			}
			if(configuration.showAtomicAccuracy)
				eval.printAtomicActionAccuracy();
			if(configuration.showMissPredictedInstances)
				eval.printMissClassifiedInstances();
			
			//Delete Files
			new File(configuration.trainFile + ".formatted").delete();
			new File(configuration.trainFile + ".model").delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
