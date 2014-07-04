
package io;

import graph.MacMahonEdge;
import graph.MacMahonEdge.FloorType;
import graph.MacMahonEdge.WallType;
import graph.MacMahonGraph;
import graph.MacMahonNode;
import graph.MacMahonNode.ObjectType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Tokenizer;

public class MacMahonSingleInstructionFileReader {
	
	public static MacMahonGraph readMap(String fileName){
		MacMahonGraph map = null;
		File mapFile = new File(fileName);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mapFile);
			//doc.getDocumentElement().normalize();
			Node root = doc.getDocumentElement();
			String mapName = ((Element)root).getAttribute("name");
			
			map = new MacMahonGraph();
			map.setName(mapName);
			
			NodeList nodes = ((Element)root).getElementsByTagName("nodes").item(0).getChildNodes();
			NodeList edges = ((Element)root).getElementsByTagName("edges").item(0).getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++){
				Node node = nodes.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element elem = (Element)node;
					
					int x = Integer.parseInt(elem.getAttribute("x"));
					int y = Integer.parseInt(elem.getAttribute("y"));
					String objType = elem.getAttribute("item");
					
					MacMahonNode mapNode;
					
					if(!objType.equals("")){
						ObjectType objectType = ObjectType.valueOf(objType);
						mapNode = new MacMahonNode(x, y, objectType);
					}else
						mapNode = new MacMahonNode(x, y, ObjectType.none);
					
					map.setNode(x, y, mapNode);
				}
			}
			
			for(int i = 0; i < edges.getLength(); i++){
				Node node = edges.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element elem = (Element)node;
					int sourceX = Integer.parseInt(elem.getAttribute("node1").split(",")[0]);
					int sourceY = Integer.parseInt(elem.getAttribute("node1").split(",")[1]);
					int targetX = Integer.parseInt(elem.getAttribute("node2").split(",")[0]);
					int targetY = Integer.parseInt(elem.getAttribute("node2").split(",")[1]);
					FloorType floorType = FloorType.valueOf(elem.getAttribute("floor"));
					WallType wallType = WallType.valueOf(elem.getAttribute("wall"));
					
					MacMahonNode source = map.getNode(sourceX, sourceY);
					MacMahonNode target = map.getNode(targetX, targetY); 
					MacMahonEdge edge = new MacMahonEdge(source, target, wallType, floorType);
					MacMahonEdge revEdge = new MacMahonEdge(target, source, wallType, floorType);
					
					map.setEdge(source, target, edge);
					map.setEdge(target, source, revEdge);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static void splitInstructionToRelatedMap(String fileName){
		File singleIns = new File(fileName);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document docGrid = dBuilder.newDocument();
			Document docJelly = dBuilder.newDocument();
			Document docL = dBuilder.newDocument();
			
			 Element rootGrid = docGrid.createElement("examples");
			 Element rootJelly = docJelly.createElement("examples");
			 Element rootL = docL.createElement("examples");
			
			Document doc = dBuilder.parse(singleIns);
			Node root = doc.getDocumentElement();
			NodeList examples = root.getChildNodes();
			
			
			
			for(int i = 0; i < examples.getLength(); i++){
				Node node = examples.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element elem = (Element)(node.cloneNode(true));
					String mapName = elem.getAttribute("map");
					
					if(mapName.equals("Grid"))
						rootGrid.appendChild(docGrid.importNode(elem, true));
					else if(mapName.equals("L"))
						rootL.appendChild(docL.importNode(elem, true));
					else
						rootJelly.appendChild(docJelly.importNode(elem, true));
				}
			}
			
			docGrid.appendChild(rootGrid);
			docJelly.appendChild(rootJelly);
			docL.appendChild(rootL);
			
			Transformer tr = TransformerFactory.newInstance().newTransformer();
			tr.transform(new DOMSource(docGrid), 
                     new StreamResult(new FileOutputStream(fileName + ".grid.xml")));
			tr.transform(new DOMSource(docJelly), 
                    new StreamResult(new FileOutputStream(fileName + ".jelly.xml")));
			tr.transform(new DOMSource(docL), 
                    new StreamResult(new FileOutputStream(fileName + ".l.xml")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void convertInstructionsToAIKUFormat(HashMap<String, MacMahonGraph> maps, String fileName){
		File singleIns = new File(fileName);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			BufferedWriter writerGrid = new BufferedWriter(new FileWriter(fileName + ".grid"));
			BufferedWriter writerJelly = new BufferedWriter(new FileWriter(fileName + ".jelly"));
			BufferedWriter writerL = new BufferedWriter(new FileWriter(fileName + ".l"));
			
			BufferedWriter writer = null;
			BufferedWriter prevWriter = null;
			
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(singleIns);
			Node root = doc.getDocumentElement();
			NodeList examples = root.getChildNodes();
			String prevFileName = "";
			
			for(int i = 0; i < examples.getLength(); i++){
				Node node = examples.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element elem = (Element)node;
					
					String mapName = elem.getAttribute("map");
					MacMahonGraph map = maps.get(mapName);
					Element instructionElement = (Element)(elem.getElementsByTagName("instruction").item(0));
					Element pathElement = (Element)(elem.getElementsByTagName("path").item(0));
					
					String currFileName = instructionElement.getAttribute("filename");
					String instruction = instructionElement.getTextContent().trim();
					instruction = Tokenizer.clean(instruction);
					
					String[] pathStr = pathElement.getTextContent()
							.replace("(", "").replace(")", "")
							.replace("[", "").replace("]", "")
							.replace(",", "").trim().split("\\s+");
					
					prevWriter = writer;
					
					if(mapName.equals("Grid"))
						writer = writerGrid;
					else if(mapName.equals("L"))
						writer = writerL;
					else
						writer = writerJelly;
					
					if(prevFileName.equals(""))
					{
						writer.append("<maze>\n");
						prevFileName = currFileName;
					}else if(!currFileName.equals(prevFileName)){
						prevWriter.append("</maze>\n").flush();
						writer.append("<maze>\n");
						prevFileName = currFileName;
					}
					
					int movementType = -1;
					for(int j = 3; j < pathStr.length; j = j + 3){
						MacMahonNode currNode = map
								.getNode(Integer.parseInt(pathStr[j])
										, Integer.parseInt(pathStr[j + 1]));
						int currentDirection = Integer.parseInt(pathStr[j + 2]);
						
						MacMahonNode prevNode = maps.get(mapName)
								.getNode(Integer.parseInt(pathStr[j - 3])
										, Integer.parseInt(pathStr[j - 2]));
						int prevDirection = Integer.parseInt(pathStr[j - 1]);
						
						if(prevDirection == -1){
							int rand = new Random().nextInt(2);
							if(rand == 0){
								prevDirection = currentDirection == 0 ? 270 : currentDirection - 90;
							}else{
								prevDirection = currentDirection == 270 ? 0 : currentDirection + 90;
							}
						}
						
						movementType = getMovementType(prevNode, currNode, prevDirection, currentDirection);
						
						if(movementType > 1 && pathStr[j - 1].equals("-1")){
							while(prevDirection != currentDirection){
								writer.append(instruction + "\n");
								writer.append(map.stateOfNode(prevNode, prevDirection / 90) + "\n");
								writer.append(Integer.toString(movementType) + "\n\n").flush();
								if(movementType == 2){
									if(prevDirection == 0)
										prevDirection = 270;
									else
										prevDirection -= 90;
								}else{
									if(prevDirection == 270)
										prevDirection = 0;
									else
										prevDirection += 90;
								}
							}
						}else{
							writer.append(instruction + "\n");
							writer.append(map.stateOfNode(prevNode, prevDirection / 90) + "\n");
							writer.append(Integer.toString(movementType) + "\n\n").flush();
						}
					}
					
					if(movementType != 0){
					
						MacMahonNode currNode = map
								.getNode(Integer.parseInt(pathStr[pathStr.length - 3])
										, Integer.parseInt(pathStr[pathStr.length - 2]));
						int currentDirection = Integer.parseInt(pathStr[pathStr.length - 1]);
						currentDirection = currentDirection == -1 ? new Random().nextInt(4) * 90 : currentDirection;
						writer.append(instruction + "\n");
						writer.append(map.stateOfNode(currNode, currentDirection / 90) + "\n");
						writer.append("0\n\n").flush();
					}
				}
			}
			writer.append("</maze>\n");
			writerGrid.close();
			writerL.close();
			writerJelly.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int getMovementType(MacMahonNode prev, MacMahonNode curr, int prevDirection, int currentDirection){
		int movementType = -1;
		
		if(prev.equals(curr) && prevDirection == currentDirection)
			movementType = 0;
		else if(prev.getX() != curr.getX() || prev.getY() != curr.getY())
			movementType = 1;
		else
			movementType = turnDirection(prevDirection, currentDirection);
		
		return movementType;
	}
	
	private static int turnDirection(int prevDirection, int currentDirection){
		if(prevDirection == 270 && currentDirection == 0)
			return 3;//right
		else if(prevDirection == 0 && currentDirection == 270)
			return 2;//left
		else if(prevDirection < currentDirection){
			return 3;//right
		}else if(prevDirection == currentDirection)
			return 0;//stop
		else
			return 2;//left
	}
	
	public static void rawInstructionText(String fileName){
		File singleIns = new File(fileName);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			BufferedWriter writerGrid = new BufferedWriter(new FileWriter(fileName + ".gridraw"));
			BufferedWriter writerJelly = new BufferedWriter(new FileWriter(fileName + ".jellyraw"));
			BufferedWriter writerL = new BufferedWriter(new FileWriter(fileName + ".lraw"));
			
			BufferedWriter writer = null;
			
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(singleIns);
			Node root = doc.getDocumentElement();
			NodeList examples = root.getChildNodes();
			
			for(int i = 0; i < examples.getLength(); i++){
				Node node = examples.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element elem = (Element)node;
					
					String mapName = elem.getAttribute("map");;
					Element instructionElement = (Element)(elem.getElementsByTagName("instruction").item(0));
					
					String instruction = instructionElement.getTextContent().trim();
					instruction = Tokenizer.clean(instruction);
					
					if(mapName.equals("Grid"))
						writer = writerGrid;
					else if(mapName.equals("L"))
						writer = writerL;
					else
						writer = writerJelly;
					
					writer.append("<s> ").append(instruction.toLowerCase()).append(" </s>").append("\n");
					writer.flush();
				}
			}
			writerGrid.close();
			writerL.close();
			writerJelly.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
