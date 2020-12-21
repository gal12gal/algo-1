import java.util.LinkedList;
public class Algos {

	public static Graph MST(Graph myGraph) {
			
		int[] parent = new int[myGraph.getVerticalsCount()];
		int[] key = new int[myGraph.getVerticalsCount()];
		boolean[] isInMST= new boolean[myGraph.getVerticalsCount()];
		Heap myHeap = new Heap(myGraph.getVerticalsCount());

		for (Vertex v : myGraph.getVerticals()) {
			key[v.getID()] = Integer.MAX_VALUE;
			parent[v.getID()] = -1;
			isInMST[v.getID()] = false;
		}
		myHeap.updateWeight(0, 0);
		key[0] = 0;

		while (!myHeap.isEmpty()) {
			int[] u = myHeap.extractMin();
			isInMST[u[0]] = true;
			for (Neighbor v : myGraph.getVertex(u[0]).getAllNeighbors()) {
				if (!isInMST[v.getDestination().getID()] && v.getWeight() < key[v.getDestination().getID()]) {
					parent[v.getDestination().getID()] = u[0]; 
					key[v.getDestination().getID()] = v.getWeight(); 
					myHeap.updateWeight(v.getDestination().getID(), v.getWeight()); 
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
	
	public static Graph changeMST(Graph mstGraph, Vertex vertex1, Vertex vertex2, int weight) {
    	int[][] bfsResult = BFSRoute(mstGraph, vertex1);
		int[] parent = bfsResult[1];
		int[] id = bfsResult[0];
        int currVertex = vertex2.getID();
        int maxVertex1=-1,maxVertex2=-1,maxWeight =-1;		
		while (id[currVertex] != 0) {
			int parentVertex = parent[currVertex];
			for (Neighbor currentNeighbor : mstGraph.getVertex(parentVertex).getAllNeighbors()) {
				if (currentNeighbor.getDestination().getID() == currVertex) {
					if (currentNeighbor.getWeight() > maxWeight) {
                        maxWeight = currentNeighbor.getWeight();
                        maxVertex2 = parentVertex;
						maxVertex1 = currVertex;
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
	public static int[][] BFSRoute(Graph g, Vertex start) {
        LinkedList<Integer> BFSqueue  = new LinkedList<Integer>();
		int[] key = new int[g.getVerticalsCount()];
		int[] parent = new int[g.getVerticalsCount()];
		boolean[] visited = new boolean[g.getVerticalsCount()];
		int[][] BFSresult = new int[2][];
		for (Vertex u : g.getVerticals()) {
			visited[u.getID()] = false;
			key[u.getID()] = Integer.MAX_VALUE;
			parent[u.getID()] = -1;
        }
		key[start.getID()] = 0;
		visited[start.getID()] = true;
		BFSqueue.add(start.getID());
		while (!BFSqueue .isEmpty()) {
			Vertex u = g.getVertex(BFSqueue.poll());
			for (Neighbor v : u.getAllNeighbors()) {
				if (!visited[v.getDestination().getID()]) {
                    BFSqueue.add(v.getDestination().getID());
					visited[v.getDestination().getID()] = true;
                    parent[v.getDestination().getID()] = u.getID();
                    key[v.getDestination().getID()] = key[u.getID()] + 1;
				}
			}
		}
		BFSresult[0] = key;
		BFSresult[1] = parent;
		return BFSresult;
	}
}