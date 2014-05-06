
package io;

import graph.MacMahonEdge;
import graph.MacMahonEdge.FloorType;
import graph.MacMahonEdge.WallType;
import graph.MacMahonGraph;
import graph.MacMahonNode;
import graph.MacMahonNode.ObjectType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MacMahonSingleInstructionReader {
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static void convertInstructionsToAIKUFormat(HashMap<String, MacMahonGraph> maps, String fileName){
		File singleIns = new File(fileName);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".aikudata"));
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(singleIns);
			//doc.getDocumentElement().normalize();
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
					String[] pathStr = pathElement.getTextContent()
							.replace("(", "").replace(")", "")
							.replace("[", "").replace("]", "")
							.replace(",", "").trim().split("\\s+");
					
					if(prevFileName.equals(""))
					{
						writer.append("<maze>\n");
						prevFileName = currFileName;
					}else if(!currFileName.equals(prevFileName)){
						writer.append("</maze>\n<maze>\n");
						prevFileName = currFileName;
					}
					
					int movementType = -1;
					for(int j = 3; j < pathStr.length; j = j + 3){
						MacMahonNode prevNode = maps.get(mapName)
								.getNode(Integer.parseInt(pathStr[j - 3])
										, Integer.parseInt(pathStr[j - 2]));
						int prevDirection = Integer.parseInt(pathStr[j - 1]);
						prevDirection = prevDirection == -1 ? new Random().nextInt(4) * 90 : prevDirection;
						
						MacMahonNode currNode = map
								.getNode(Integer.parseInt(pathStr[j])
										, Integer.parseInt(pathStr[j + 1]));
						int currentDirection = Integer.parseInt(pathStr[j + 2]);
						
						movementType = getMovementType(prevNode, currNode, prevDirection, currentDirection);
						
						if(movementType > 1 && pathStr[j - 1].equals("-1")){
							while(prevDirection != currentDirection){
								writer.append(instruction + "\n");
								writer.append(stateOfNode(currNode, map, prevDirection));
								writer.append(Integer.toString(prevDirection / 90) + "\n");
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
							writer.append(stateOfNode(currNode, map, prevDirection));
							writer.append(Integer.toString(prevDirection / 90) + "\n");
							writer.append(Integer.toString(movementType) + "\n\n").flush();
						}
					}
					
					if(movementType != 0){
					
						MacMahonNode currNode = map
								.getNode(Integer.parseInt(pathStr[pathStr.length - 3])
										, Integer.parseInt(pathStr[pathStr.length - 2]));
						int currentDirection = Integer.parseInt(pathStr[pathStr.length - 1]);
						writer.append(instruction + "\n");
						writer.append(stateOfNode(currNode, map, currentDirection));
						writer.append(Integer.toString(currentDirection / 90) + "\n");
						writer.append("0\n\n").flush();
					}
				}
			}
			writer.append("</maze>");
			writer.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		else if(prevDirection < currentDirection){
			return 3;//right
		}else if(prevDirection == currentDirection)
			return 0;//stop
		else
			return 2;//left
	}
	
	private static String stateOfNode(MacMahonNode node, MacMahonGraph map, int direction){
		MacMahonNode northNode = map.getNode(node.getX(), node.getY() - 1);
		MacMahonEdge north = northNode == null ? null : map.getEdge(node, northNode);
		MacMahonNode eastNode = map.getNode(node.getX() + 1, node.getY());
		MacMahonEdge east = eastNode == null ? null : map.getEdge(node, eastNode);
		MacMahonNode southNode = map.getNode(node.getX(), node.getY() + 1);
		MacMahonEdge south = southNode == null ? null : map.getEdge(node, southNode);
		MacMahonNode westNode = map.getNode(node.getX() - 1, node.getY());
		MacMahonEdge west = westNode == null ? null : map.getEdge(node, westNode);
		
		String state = "";
		
		state += north == null ? "1 " : "0 ";
		state += east == null ? "1 " : "0 ";
		state += south == null ? "1 " : "0 ";
		state += west == null ? "1 " : "0 ";
		state += ObjectType.objectTypeFeatureString(node.getObjectType()) + " ";
		state += northNode == null ? nonNodeString() : ObjectType.objectTypeFeatureString(northNode.getObjectType()) + " ";
		state += eastNode == null ? nonNodeString() : ObjectType.objectTypeFeatureString(eastNode.getObjectType()) + " ";
		state += southNode == null ? nonNodeString() : ObjectType.objectTypeFeatureString(southNode.getObjectType()) + " ";
		state += westNode == null ? nonNodeString() : ObjectType.objectTypeFeatureString(westNode.getObjectType()) + " ";
		state += north == null ? nonEdgeString() : 
			FloorType.floorTypeFeatureString(north.getFloorType()) + " " + 
				WallType.wallTypeFeatureString(north.getWallType()) + " ";
		state += east == null ? nonEdgeString() : 
			FloorType.floorTypeFeatureString(east.getFloorType()) + " " + 
			WallType.wallTypeFeatureString(east.getWallType()) + " ";
		state += south == null ? nonEdgeString() : 
			FloorType.floorTypeFeatureString(south.getFloorType()) + " " + 
			WallType.wallTypeFeatureString(south.getWallType()) + " ";
		state += west == null ? nonEdgeString() : 
			FloorType.floorTypeFeatureString(west.getFloorType()) + " " + 
			WallType.wallTypeFeatureString(west.getWallType()) + " ";
		
		return state;
	}
	
	private static String nonNodeString(){
		String nonNodeState = "";
		
		for(int i = 0; i < ObjectType.values().length; i++)
			nonNodeState += "0 ";
		
		return nonNodeState;
	}
	
	private static String nonEdgeString(){
		String nonEdgeState = "";
		
		for(int i = 0; i < FloorType.values().length; i++)
			nonEdgeState += "0 ";
		for(int i = 0; i < WallType.values().length; i++)
			nonEdgeState += "0 ";
		
		return nonEdgeState;
	}
}
