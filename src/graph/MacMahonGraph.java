package graph;

import graph.MacMahonEdge.FloorType;
import graph.MacMahonEdge.WallType;
import graph.MacMahonNode.ObjectType;

import java.util.HashMap;

public class MacMahonGraph {
	HashMap<Integer, HashMap<Integer, MacMahonNode>> nodes;
	HashMap<MacMahonNode, HashMap<MacMahonNode, MacMahonEdge>> edges;
	String name;
	
	public MacMahonGraph(){
		nodes = new HashMap<Integer, HashMap<Integer, MacMahonNode>>();
		edges = new HashMap<MacMahonNode, HashMap<MacMahonNode, MacMahonEdge>>();
	}
	
	public void setNode(int x, int y, MacMahonNode node){
		HashMap<Integer, MacMahonNode> columns;
		if(nodes.containsKey(y))
			columns = nodes.get(y);
		else
			columns = new HashMap<Integer, MacMahonNode>();
		
		columns.put(x, node);
		nodes.put(y, columns);
	}
	
	public MacMahonNode getNode(int x, int y){
		if(nodes.containsKey(y)){
			if(nodes.get(y).containsKey(x))
				return nodes.get(y).get(x);
			else
				return null;
		}else
			return null;
	}
	
	public void setEdge(MacMahonNode source, MacMahonNode target, MacMahonEdge edge){
		HashMap<MacMahonNode, MacMahonEdge> columns;
		if(edges.containsKey(source))
			columns = edges.get(source);
		else
			columns = new HashMap<MacMahonNode, MacMahonEdge>();
		
		columns.put(target, edge);
		edges.put(source, columns);
	}
	
	public MacMahonEdge getEdge(MacMahonNode source, MacMahonNode target){
		if(edges.containsKey(source)){
			if(edges.get(source).containsKey(target))
				return edges.get(source).get(target);
			else
				return null;
		}else
			return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String stateOfNode(MacMahonNode node, int direction){
		MacMahonNode northNode = getNode(node.getX(), node.getY() - 1);
		MacMahonEdge north = northNode == null ? null : getEdge(node, northNode);
		MacMahonNode eastNode = getNode(node.getX() + 1, node.getY());
		MacMahonEdge east = eastNode == null ? null : getEdge(node, eastNode);
		MacMahonNode southNode = getNode(node.getX(), node.getY() + 1);
		MacMahonEdge south = southNode == null ? null : getEdge(node, southNode);
		MacMahonNode westNode = getNode(node.getX() - 1, node.getY());
		MacMahonEdge west = westNode == null ? null : getEdge(node, westNode);
		
		String state = "";
		
		state += north == null ? "1 " : "0 ";
		state += east == null ? "1 " : "0 ";
		state += south == null ? "1 " : "0 ";
		state += west == null ? "1 " : "0 ";
		state += ObjectType.objectTypeFeatureString(node.getObjectType()) + " ";
		state += northNode == null ? ObjectType.objectTypeFeatureString(ObjectType.none)  + " "
				: ObjectType.objectTypeFeatureString(northNode.getObjectType()) + " ";
		state += eastNode == null ? ObjectType.objectTypeFeatureString(ObjectType.none) + " " 
				: ObjectType.objectTypeFeatureString(eastNode.getObjectType()) + " ";
		state += southNode == null ?ObjectType.objectTypeFeatureString(ObjectType.none) + " " 
				: ObjectType.objectTypeFeatureString(southNode.getObjectType()) + " ";
		state += westNode == null ? ObjectType.objectTypeFeatureString(ObjectType.none) + " " 
				: ObjectType.objectTypeFeatureString(westNode.getObjectType()) + " ";
		state += north == null ? FloorType.floorTypeFeatureString(FloorType.none) + " " 
				+ WallType.wallTypeFeatureString(WallType.none) + " " : 
			FloorType.floorTypeFeatureString(north.getFloorType()) + " " + 
				WallType.wallTypeFeatureString(north.getWallType()) + " ";
		state += east == null ? FloorType.floorTypeFeatureString(FloorType.none)  + " "
				+ WallType.wallTypeFeatureString(WallType.none) + " ": 
			FloorType.floorTypeFeatureString(east.getFloorType()) + " " + 
			WallType.wallTypeFeatureString(east.getWallType()) + " ";
		state += south == null ? FloorType.floorTypeFeatureString(FloorType.none) + " " 
				+ WallType.wallTypeFeatureString(WallType.none) + " " : 
			FloorType.floorTypeFeatureString(south.getFloorType()) + " " + 
			WallType.wallTypeFeatureString(south.getWallType()) + " ";
		state += west == null ? FloorType.floorTypeFeatureString(FloorType.none) + " " 
				+ WallType.wallTypeFeatureString(WallType.none) + " " : 
			FloorType.floorTypeFeatureString(west.getFloorType()) + " " + 
			WallType.wallTypeFeatureString(west.getWallType()) + " ";
		for(int i = 0; i < 4; i++){
			state += (i == direction) ? "1 " : "0 ";
				
		}
		return state.trim();
	}

}
