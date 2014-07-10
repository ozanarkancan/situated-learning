package io;

import graph.MacMahonGraph;

import java.util.HashMap;

public class MacMahonSingleInstructionFileReaderDemo {

	public static void main(String[] args) {
		//testReadMap();
		testConvertInstructions();
		//testRawText();
		//testSplitFile();
	}
	
	public static void testSplitFile(){
		String fileName = "/home/cano/situatedLearning/data/SingleSentence.xml";
		MacMahonSingleInstructionFileReader.splitInstructionToRelatedMap(fileName);
	}
	
	public static void testReadMap(){
		String fileName = "/home/cano/situatedLearning/LearningNavigationInstructions/maps/map-grid.xml";
		MacMahonSingleInstructionFileReader.readMap(fileName);
	}
	
	public static void testConvertInstructions(){
		String grid = "/home/cano/situatedLearning/data/map-grid.xml";
		String l = "/home/cano/situatedLearning/data/map-l.xml";
		String jelly = "/home/cano/situatedLearning/data/map-jelly.xml";
		String fileName = "/home/cano/situatedLearning/data/InstructionsForGridSearch.xml";
		
		HashMap<String, MacMahonGraph> maps = new HashMap<String, MacMahonGraph>();
		maps.put("Grid", MacMahonSingleInstructionFileReader.readMap(grid));
		maps.put("L", MacMahonSingleInstructionFileReader.readMap(l));
		maps.put("Jelly", MacMahonSingleInstructionFileReader.readMap(jelly));
		
		
		MacMahonSingleInstructionFileReader.convertInstructionsToAIKUFormat(maps, fileName);
	}
	
	public static void testRawText(){
		String fileName = "/home/cano/situatedLearning/experiments/corpussimilarity/SingleSentence.xml";
		MacMahonSingleInstructionFileReader.rawInstructionText(fileName);
	}

}
