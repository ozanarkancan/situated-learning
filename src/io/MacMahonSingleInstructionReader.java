package io;

import java.io.File;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import learning.instance.SingleInstruction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Tokenizer;

public class MacMahonSingleInstructionReader {
	private NodeList examples;
	private int index = 0;
	
	public MacMahonSingleInstructionReader(String fileName){
		File singleIns = new File(fileName);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(singleIns);
			
			Node root = doc.getDocumentElement();
			examples = root.getChildNodes();
			
		} catch (Exception e) {
		}
	}
	
	public SingleInstruction nextInstruction(){
		Node node = null;
		do{
			if(index >= examples.getLength())
				break;
			node = examples.item(index);
			index++;
		}while(node.getNodeType() != Node.ELEMENT_NODE);
		
		if(node == null)
			return null;
		else{
			SingleInstruction singleInstruction = new SingleInstruction();
			Element elem = (Element)node;
			singleInstruction.mapName = elem.getAttribute("map");
			Element instructionElement = (Element)(elem.getElementsByTagName("instruction").item(0));
			Element pathElement = (Element)(elem.getElementsByTagName("path").item(0));
			singleInstruction.instruction = Tokenizer.clean(instructionElement.getTextContent().trim());
			singleInstruction.fileName = instructionElement.getAttribute("filename");
			
			String[] pathStr = pathElement.getTextContent()
					.replace("(", "").replace(")", "")
					.replace("[", "").replace("]", "")
					.replace(",", "").trim().split("\\s+");
			
			singleInstruction.startX = Integer.parseInt(pathStr[0]);
			singleInstruction.startY = Integer.parseInt(pathStr[1]);
			int orientation = Integer.parseInt(pathStr[2]);
			
			if(orientation == -1){
				if(pathStr.length > 3){
					int rand = new Random().nextInt(2);
					int nextDirection = Integer.parseInt(pathStr[5]);
					
					if(rand == 0){
						orientation = nextDirection == 0 ? 270 : nextDirection - 90;
					}else{
						orientation = nextDirection == 270 ? 0 : nextDirection + 90;
					}
				}else
					orientation = new Random().nextInt(4) * 90;
			}
			
			singleInstruction.startOrientation = orientation / 90;
			
			singleInstruction.endX = Integer.parseInt(pathStr[pathStr.length - 3]);
			singleInstruction.endY = Integer.parseInt(pathStr[pathStr.length - 2]);
			orientation = Integer.parseInt(pathStr[pathStr.length - 1]);
			singleInstruction.endOrientation = orientation == -1 ? singleInstruction.startOrientation
					: orientation / 90;
			
			return singleInstruction;
		}
	}
	
	public boolean hasInstruction(){
		return index < examples.getLength();
	}
	
	
}
