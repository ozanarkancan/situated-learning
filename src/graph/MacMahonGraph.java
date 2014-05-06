package graph;

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

}
