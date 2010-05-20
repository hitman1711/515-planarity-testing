import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * filename: PlanarityTest.java
 */

/**
 * This class contains the main method of our Project. Implements the algorithm
 * outlined in "Graph Drawing: Algorithms for the Visualization of Graphs" book
 * written by Ioannis G. Tollis, Giuseppe Di Battista, Peter Eades, Roberto Tamassia.
 * Algorithm can be found in Chapter 3: Divide and Conquer page 80.
 * 
 * @author Trevor Mack
 * @author David Sweeney
 *
 */
public class PlanarityTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if( args.length != 1 ) {
			System.err.println("usage: java PlanarityTest filename");
		}else {
			File aFile = new File(args[0]); 
			Graph graph = null;
			try {
				graph = new Graph(aFile);
				
				System.out.println("#unique-nodes? " + graph.size());
				System.out.println("#undirected-edges? " + graph.getEdgeCount());
				
				System.out.print("simple cycle? "); graph.simpleCycle();
				System.out.println("is bipartite? " + graph.isBipartite());
				System.out.println("is path? " + graph.isPath());
				
				//TODO: planar doesn't work yet...
				System.out.println("is planar? " + graph.isPlanar(graph.simpleCycle()));
			} catch (PlanarityException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Graph InterlacementGraph(List<Graph> pieces, Graph cycle) { 
		
		if (cycle==null) {
			if (pieces!=null) {
				if (pieces.size() == 1) {
					return pieces.get(0);
				}
			} else
				return null;
		}
			
		
		Graph Interlacement = new Graph();
		
		
		for (int i=0; i < pieces.size(); i++) {
			
			//TODO: devise method to determine order of vertices in cycle so that we can determine interval list
			List<Object> attach = Attach(pieces.get(i).getNodes(), cycle);
			
			
			Object first = attach.get(0);
			Object last = attach.get(attach.size()-1);
			
			
			Stack<Object> interval = Intervalize(cycle, first, last);
			
			for (int j =0 ; j < pieces.size(); j++) {
				if (i!=j) {
					for (int k=0; k < pieces.get(j).size(); k++) {
						
						if (interval.contains(pieces.get(j).getNodes().get(k))) {
							Interlacement.addEdge(i, j);
						}
							
					}
				}
			}
			
			
		}
		
		return Interlacement;
		
	}
	
	public static List<Object> Attach(List<Object> piece, Graph cycle) {
		//attach(n) returns attachment vertices of piece n (in other words, all nodes that exist in both the piece and the cycle)
		if (cycle==null)
			return null;
		
		List<Object> cycleNodes = cycle.getNodes();
		
		List<Object> attachments=new ArrayList<Object>();
		
		for (int i=0; i < cycleNodes.size(); i++) {
			List<Object> neighbors = cycle.getNeighbors(cycleNodes.get(i));
			
			for (int j=0; j < neighbors.size(); j++) {
				if (piece.contains(neighbors.get(j))) {
					//add the current cycle node to attachment list and move
					//to next cycle node
					attachments.add(neighbors.get(j));
					continue;
				}
						
			}
		}
		
		/*for (int i=0; i < cycleNodes.size(); i++) {
			if (piece.contains(cycleNodes.get(i)))
				attachments.add(cycleNodes.get(i));
		}*/
		
		
		return attachments;
	
	}
	
	public static Stack<Object> Intervalize(Graph cycle, Object first, Object last) {
		List<Object> neighbors = cycle.getNeighbors(first);
		Stack<Object> pathStack = new Stack<Object>();
		List<Object> visited = new ArrayList<Object>();
		pathStack.add(first);
		
		boolean found=false;
		for (int i=0; i < neighbors.size(); i++) {
			if (neighbors.get(i)!=last && !found) {
				visited.add(first);
				if (DepthSearch(cycle, neighbors.get(i), last, pathStack, visited) != (Object)(-1))
					found=true;
			}
		}
		
		return pathStack;
	
	}
	
	private static Object DepthSearch(Graph cycle, Object first, Object goal, Stack<Object> currentPath, List<Object> visited) {
		
		List<Object> neighbors = cycle.getNeighbors(first);
		visited.add(first);
		
		for (int i=0; i < neighbors.size(); i++ ) {
			if (neighbors.get(i) != goal && !visited.contains(neighbors.get(i))) {
				Object result = DepthSearch(cycle, neighbors.get(i), goal, currentPath, visited);
				
				if (result != (Object)(-1)) {
					currentPath.add(result);
					return neighbors.get(i);
				}
			}
			else 
				if (neighbors.get(i)==goal)
				return neighbors.get(i);
				
		}
		
		//visited.remove(first);
		return -1;
		
			
	}
	
	public static List<Graph> FindPieces(Graph graph, Graph cycle) { 
	    
		//List<Object> graphNodes = cycle.getNodes();
		List<Object> cycleNodes = cycle.getNodes();
		
	
		List<Object> state = new ArrayList<Object>();
		
		List<Graph> pieces = new ArrayList<Graph>();  
		
		for (int i=0; i < cycleNodes.size(); i++) {
			List<Object> neighbors = graph.getNeighbors(cycleNodes.get(i));
			
			//visit node
			state.add(cycleNodes.get(i));
			
			for (int j=0; j< neighbors.size(); j++) {
				
				
				//search the node if it is not on the cycle
				if (!cycleNodes.contains(neighbors.get(j)) && !state.contains(neighbors.get(j))) {
					Graph newPiece = TraversePiece(graph, cycleNodes, neighbors.get(j), state); //this should add all nodes to the piece
					//newPiece.addEdge(neighbors.get(j), cycleNodes.get(i));
					pieces.add(newPiece);
				}
						
			//and i think that's it	
			}
			
		}
		
		return pieces;
		
	}
	
	private static Graph TraversePiece(Graph graph, List<Object> cycleNodes, Object startNode, List<Object> state) {
		//TODO: add code that passes in some list containing all the nodes in the piece that we can add to
		List<Object> neighbors = graph.getNeighbors(startNode);
		
		Graph piece = new Graph();
		
		//visit node
		state.add(startNode);
		
		for (int i=0; i < neighbors.size(); i++) {
			if (!state.contains(neighbors.get(i)) && !cycleNodes.contains(neighbors.get(i))) { 
				//TODO: change piece to a Graph, and add Edges as well as vertices
				Graph newPiece=TraversePiece(graph, cycleNodes, neighbors.get(i), state);
				piece.addGraph(newPiece);
				piece.addEdge(startNode, neighbors.get(i));
				
				//also write code to add this node to the piece list or whatever
			}
			if (cycleNodes.contains(neighbors.get(i))) {
				piece.addEdge(startNode, neighbors.get(i));
			}
				
			
		}
		
		return piece;
	
	}
	
	static List<Graph> FindVertexPieces(Graph graph, Graph cycle, List<Graph> otherPieces) {
			
		List<Graph> pieces = new ArrayList<Graph>();
		
		List<Object[]> graphEdges = graph.getEdges();
		List<Object[]> cycleEdges = cycle.getEdges();
	
		//for (Object[] edge : graphEdges) {
		//	if (graphEdges.contains(edge))
		//			continue;//graphEdges.remove(edge);
		//}
		
		for (Object[] edge : cycleEdges) {
			for (int i =0; i < graphEdges.size(); i++) {
				if ( (graphEdges.get(i)[0]==edge[0] && graphEdges.get(i)[1]==edge[1]) ||
					 (graphEdges.get(i)[1]==edge[0] && graphEdges.get(i)[0]==edge[1]))
					graphEdges.remove(i);
			}
		}
		for (Graph piece : otherPieces) {
			for (Object[] edge : piece.getEdges()) {
				for (int i =0; i < graphEdges.size(); i++) {
					if ( (graphEdges.get(i)[0]==edge[0] && graphEdges.get(i)[1]==edge[1]) ||
							(graphEdges.get(i)[1]==edge[0] && graphEdges.get(i)[0]==edge[1]))
						graphEdges.remove(i);
				}
			}
		}
		
		for (Object[] edge : graphEdges) {
			Graph newGraph = new Graph();
			newGraph.addEdge(edge[0], edge[1]);
			pieces.add(newGraph);
		}
	
		return pieces;
	}
	

}
