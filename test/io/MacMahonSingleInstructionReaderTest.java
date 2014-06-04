package io;

import graph.MacMahonGraph;

import java.util.HashMap;

public class MacMahonSingleInstructionReaderTest {

	public static void main(String[] args) {
		//testReadMap();
		testConvertInstructions();
		testRawText();
	}
	
	public static void testReadMap(){
		String fileName = "/home/cano/situatedLearning/LearningNavigationInstructions/maps/map-grid.xml";
		MacMahonSingleInstructionReader.readMap(fileName);
	}
	
	public static void testConvertInstructions(){
		String grid = "/home/cano/situatedLearning/LearningNavigationInstructions/maps/map-grid.xml";
		String l = "/home/cano/situatedLearning/LearningNavigationInstructions/maps/map-l.xml";
		String jelly = "/home/cano/situatedLearning/LearningNavigationInstructions/maps/map-jelly.xml";
		String fileName = "/home/cano/situatedLearning/LearningNavigationInstructions/data/SingleSentence.xml";
		
		HashMap<String, MacMahonGraph> maps = new HashMap<String, MacMahonGraph>();
		maps.put("Grid", MacMahonSingleInstructionReader.readMap(grid));
		maps.put("L", MacMahonSingleInstructionReader.readMap(l));
		maps.put("Jelly", MacMahonSingleInstructionReader.readMap(jelly));
		
		
		MacMahonSingleInstructionReader.convertInstructionsToAIKUFormat(maps, fileName);
	}
	
	public static void testRawText(){
		String fileName = "/home/cano/situatedLearning/experiments/corpussimilarity/SingleSentence.xml";
		MacMahonSingleInstructionReader.rawInstructionText(fileName);
	}

}
