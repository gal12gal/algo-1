import java.util.LinkedList;
import java.util.Random; 


public class main {

	public static Graph MST(Graph myGraph) {
			
		int[] parent = new int[myGraph.getVerticalsCount()];
		int[] key = new int[myGraph.getVerticalsCount()];
		boolean[] isInMST= new boolean[myGraph.getVerticalsCount()];
		

		// Creating heap with all weights infinity
		Heap myHeap = new Heap(myGraph.getVerticalsCount());
	
		// initiating arrays
		for (Vertex v : myGraph.getVerticals()) {
			key[v.getID()] = Integer.MAX_VALUE;
			parent[v.getID()] = -1;
			isInMST[v.getID()] = false;
		}
		
		// The vertex of the start
		myHeap.updateWeight(0, 0);
		key[0] = 0;
		
		// iterating verticals
		while (!myHeap.isEmpty()) {
			int[] u = myHeap.extractMin();
			isInMST[u[0]] = true;
			// going over all it's neighbors 
			for (Neighbor v : myGraph.getVertex(u[0]).getNeighbors()) {
				if (!isInMST[v.getDest().getID()] && v.getWeight() < key[v.getDest().getID()]) {
					parent[v.getDest().getID()] = u[0]; 
					key[v.getDest().getID()] = v.getWeight(); 
					myHeap.updateWeight(v.getDest().getID(), v.getWeight()); 
				}
			}
		}
		
		Graph mstGraph = new Graph(myGraph.getVerticalsCount());
		
		for (int i = 0; i < myGraph.getVerticalsCount(); i++) {
			if (parent[i] != -1) {
				mstGraph.addEdge(parent[i], i, key[i]);
			}
		}
		return mstGraph;
	}
	
	public static Graph addEdgeToMST(Graph mstGraph, Vertex vertex1, Vertex vertex2, int weight) {
		
		// finding the path between v and u using BFS
		int[][] bfsResult = BFS(mstGraph, vertex1);
		int[] parent = bfsResult[1];
		int[] key = bfsResult[0];

		
		// Finding the edge with the max weight in the path from v to u.
		int currVertex = vertex2.getID();		
		int maxVertex1 = -1;
		int maxVertex2 = -1;
		int maxWeight = -1; 
		while (key[currVertex] != 0) {
			int parentVertex = parent[currVertex];
			for (Neighbor n : mstGraph.getVertex(parentVertex).getNeighbors()) {
				if (n.getDest().getID() == currVertex) {
					if (n.getWeight() > maxWeight) {
						maxWeight = n.getWeight();
						maxVertex1 = currVertex;
						maxVertex2 = parentVertex;
					}
				}
			}
			currVertex = parentVertex;
		}
		
		if (weight < maxWeight) {
			mstGraph.removeEdge(maxVertex1, maxVertex2);
			mstGraph.addEdge(vertex1.getID(), vertex2.getID(), weight);
		}

		return mstGraph;
	}
	
	public static int[][] BFS(Graph g, Vertex start) {

		// The distance to get to this vertex
		int[] key = new int[g.getVerticalsCount()];
	    // Vertex parent
		int[] parent = new int[g.getVerticalsCount()];
		// marking if a vertex was visited
		boolean[] visited = new boolean[g.getVerticalsCount()];
		
		// initiating the arrays
		for (Vertex u : g.getVerticals()) {
			visited[u.getID()] = false;
			key[u.getID()] = Integer.MAX_VALUE;
			parent[u.getID()] = -1;
		}
		LinkedList<Integer> q = new LinkedList<Integer>();
		
		// starting from start vertex
		key[start.getID()] = 0;
		visited[start.getID()] = true;
		q.add(start.getID());
		
		while (!q.isEmpty()) {
			Vertex u = g.getVertex(q.poll());
			for (Neighbor v : u.getNeighbors()) {
				if (!visited[v.getDest().getID()]) {
					visited[v.getDest().getID()] = true;
					key[v.getDest().getID()] = key[u.getID()] + 1;
					parent[v.getDest().getID()] = u.getID();
					q.add(v.getDest().getID());
				}
			}
		}
		int[][] result = new int[2][];
		result[0] = key;
		result[1] = parent;
		
		return result;
	}
	
	public static void main(String[] args) { 
		Graph myGraph = new Graph(20);
		
		boolean[][] edgeMarker = new boolean[myGraph.getVerticalsCount()][myGraph.getVerticalsCount()];
		for (int i = 0; i < myGraph.getVerticalsCount(); i++) {
			for (int j = 0; j < myGraph.getVerticalsCount(); j++) {				
				edgeMarker[i][j] = false;
			}
		}
		
		Random rand = new Random();

		// creating the edges
		int minNumOfEdges = 50;
		while (minNumOfEdges > 0) {
			int vertex1 = rand.nextInt(myGraph.getVerticalsCount());
			int vertex2 = rand.nextInt(myGraph.getVerticalsCount());
			if (!edgeMarker[vertex1][vertex2] && vertex1 != vertex2) {
				myGraph.addEdge(vertex1, vertex2, rand.nextInt(1000) + 1);
				edgeMarker[vertex1][vertex2] = true;
				edgeMarker[vertex2][vertex1] = true;
				minNumOfEdges--;
			}
		}
		
		// graph linking 
		for (Vertex v : myGraph.getVerticals()) {
			if (v.getNeighbors().isEmpty()) {
				int u = rand.nextInt(myGraph.getVerticalsCount());
				while (u == v.getID()) {
					u = rand.nextInt(myGraph.getVerticalsCount());
				}
				myGraph.addEdge(u, v.getID(), rand.nextInt(1000) + 1);
				edgeMarker[u][v.getID()] = true;
				edgeMarker[v.getID()][u] = true;
			}
		}
		
		System.out.println("the graph generated");
		myGraph.printGraph();
		
		System.out.println("----------------------------------------");
		
		// MST graph !!!
		Graph mst = MST(myGraph);
		
		System.out.println("MST graph:");
		mst.printGraph();
		
		
		int vertex1 = rand.nextInt(myGraph.getVerticalsCount());
		int vertex2 = rand.nextInt(myGraph.getVerticalsCount());
		while (edgeMarker[vertex1][vertex2] || vertex1 == vertex2) {
			vertex1 = rand.nextInt(myGraph.getVerticalsCount());
			vertex2 = rand.nextInt(myGraph.getVerticalsCount());
		}

		int weight = 10000;
		myGraph.addEdge(vertex1, vertex2, weight);
		edgeMarker[vertex1][vertex2] = true;
		edgeMarker[vertex2][vertex1] = true;
		
		System.out.printf("the edge between %s, %s with weight %s%n", vertex1, vertex2, weight);

		Graph notChagedMST = addEdgeToMST(mst, myGraph.getVertex(vertex1), myGraph.getVertex(vertex2), weight);
		
		System.out.println("the new MST graph after adding new edge");
		notChagedMST.printGraph();
		
		System.out.println("==========================================================");
		
		// Creating one more edge that will change the mst
		vertex1 = rand.nextInt(myGraph.getVerticalsCount());
		vertex2 = rand.nextInt(myGraph.getVerticalsCount());
		while (edgeMarker[vertex1][vertex2] || vertex1 == vertex2) {
			vertex1 = rand.nextInt(myGraph.getVerticalsCount());
			vertex2 = rand.nextInt(myGraph.getVerticalsCount());
		}
		weight = 0;
		myGraph.addEdge(vertex1, vertex2, weight);
		edgeMarker[vertex1][vertex2] = true;
		edgeMarker[vertex2][vertex1] = true;
		
		System.out.printf("adding edge between %s, %s with weight %s%n", vertex1, vertex2, weight);
		
		Graph newMST = addEdgeToMST(mst, myGraph.getVertex(vertex1), myGraph.getVertex(vertex2), weight);
		
		System.out.println("new MST graph:");
		newMST.printGraph();
	}
}