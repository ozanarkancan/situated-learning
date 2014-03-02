package maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class MazeSolver {
	private class Node implements Comparable<Node>{
		public Cell cell;
		public double pathCost;
		public double heuristicCost;
		public Node parent;
		
		public Node(Cell data, Node parent, Cell destination){
			cell = data;
			this.parent = parent;
			pathCost = (parent == null) ? 0 : parent.pathCost + 1;
			heuristicCost = cell.euclideanDistance(destination);
		}
		
		public double getTotalCost(){
			return pathCost + heuristicCost;
		}
		
		public ArrayList<Node> getNeighbours(Maze maze){
			ArrayList<Node> neighbours = new ArrayList<Node>();
			Wall[] walls = cell.getWalls();
			
			for(int i = 0; i < 4; i++){
				if(walls[i].isCarved()){
					if(i == 0 || i == 2){
						Node n = new Node(walls[i].getSource(), this, maze.getDestination());
						neighbours.add(n);
					}
					else{
						Node n = new Node(walls[i].getTarget(), this, maze.getDestination());
						neighbours.add(n);
					}
				}
			}
			
			return neighbours;
		}

		@Override
		public int compareTo(Node o) {
			return (int) (getTotalCost() - o.getTotalCost());
		}
		
		@Override
		public boolean equals(Object rhs){
			return cell.equals(((Node)rhs).cell);
		}
		
	}
	
	private static MazeSolver instance = null;
	private PriorityQueue<Node> openSet;
	private Set<Node> closedSet;
	
	private MazeSolver(){
		
	}
	
	public static MazeSolver getInstance(){
		if(instance == null)
			instance = new MazeSolver();
		return instance;
	}
	
	public ArrayList<Cell> solveAStar(Maze maze){
		openSet = new PriorityQueue<Node>();
		closedSet = new HashSet<Node>();
		
		Node goalNode = new Node(maze.getDestination(), null, maze.getDestination());
		
		Node startNode = new Node(maze.getStart(), null, maze.getDestination());
		openSet.add(startNode);
		
		Node currentNode = null;
		
		while(!openSet.isEmpty()){
			currentNode = openSet.poll();
			if(currentNode.equals(goalNode))
				break;
			
			closedSet.add(currentNode);
			
			ArrayList<Node> neighbours = currentNode.getNeighbours(maze);
			
			for(int i = 0; i < neighbours.size(); i++){
				Node neighbour = neighbours.get(i);
				if(closedSet.contains(neighbour))
					continue;
				if(openSet.contains(neighbour)){
					Node prevNeighbourNode = null;
					for(Node n : openSet){
						if(n.equals(neighbour)){
							if(neighbour.pathCost < n.pathCost)
								prevNeighbourNode = n;
							break;
						}
					}
					if(prevNeighbourNode != null){
						openSet.remove(prevNeighbourNode);
						openSet.add(neighbour);
					}
				}
				else
					openSet.add(neighbour);
			}
			
		}
		
		return getPath(currentNode);
	}
	
	private ArrayList<Cell> getPath(Node n){
		ArrayList<Cell> path = new ArrayList<Cell>();
		
		while(n != null){
			path.add(0, n.cell);
			n = n.parent;
		}
		
		return path;
	}

}
