// Yuval Azoulai 206570186
// Gal Mashiah 318192127

import java.util.Random; 
import java.util.LinkedList;

public class main {
	
	public static void main(String[] args) { 
		Graph myGraph = new Graph(20);
		
		boolean[][] edgeMarker = new boolean[myGraph.getVerticalsCount()][myGraph.getVerticalsCount()];
		for (int i = 0; i < myGraph.getVerticalsCount(); i++) {
			for (int j = 0; j < myGraph.getVerticalsCount(); j++) {				
				edgeMarker[i][j] = false;
			}
		}
		
		Random rand = new Random();
		int minNumOfEdges = 50;
		while (minNumOfEdges > 0) {
			int ver1 = rand.nextInt(myGraph.getVerticalsCount());
			int ver2 = rand.nextInt(myGraph.getVerticalsCount());
			if (!edgeMarker[ver1][ver2] && ver1 != ver2) {
				myGraph.addEdge(ver1, ver2, rand.nextInt(1000) + 1);
				edgeMarker[ver1][ver2] = true;
				edgeMarker[ver2][ver1] = true;
				minNumOfEdges--;
			}
		}

		for (Vertex currnetVertex : myGraph.getVerticals()) {
			if (currnetVertex.getAllNeighbors().isEmpty()) {
				int u = rand.nextInt(myGraph.getVerticalsCount());
				while (u == currnetVertex.getID()) {
					u = rand.nextInt(myGraph.getVerticalsCount());
				}
				myGraph.addEdge(u, currnetVertex.getID(), rand.nextInt(1000) + 1);
				edgeMarker[u][currnetVertex.getID()] = true;
				edgeMarker[currnetVertex.getID()][u] = true;
			}
		}
		
		System.out.println("the created graph");
		myGraph.printGraph();
		
		System.out.println("_____________________________________________________________");
		Graph mst = Algos.MST(myGraph);
		System.out.println("MST graph:");
		mst.printGraph();
		int ver1 = rand.nextInt(myGraph.getVerticalsCount());
		int ver2 = rand.nextInt(myGraph.getVerticalsCount());
		while (edgeMarker[ver1][ver2] || ver1 == ver2) {
			ver1 = rand.nextInt(myGraph.getVerticalsCount());
			ver2 = rand.nextInt(myGraph.getVerticalsCount());
		}

		int maxWeight = Integer.MAX_VALUE;
		myGraph.addEdge(ver1, ver2, maxWeight);
		edgeMarker[ver1][ver2] = true;
		edgeMarker[ver2][ver1] = true;
		
		System.out.printf("Added edge between %s, %s with weight %s%n", ver1, ver2, maxWeight);

		Graph notChagedMST = Algos.changeMST(mst, myGraph.getVertex(ver1), myGraph.getVertex(ver2), maxWeight);
		System.out.println("the new MST graph after adding new edge");
		notChagedMST.printGraph();
		System.out.println("_____________________________________________________________");
		ver1 = rand.nextInt(myGraph.getVerticalsCount());
		ver2 = rand.nextInt(myGraph.getVerticalsCount());
		while (edgeMarker[ver1][ver2] || ver1 == ver2) {
			ver1 = rand.nextInt(myGraph.getVerticalsCount());
			ver2 = rand.nextInt(myGraph.getVerticalsCount());
		}
		int minWeight = 0;
		myGraph.addEdge(ver1, ver2, minWeight);
		edgeMarker[ver1][ver2] = true;
		edgeMarker[ver2][ver1] = true;
		
		System.out.printf("edge between %s, %s with weight %s%n as beed added", ver1, ver2, minWeight);
		
		Graph newMST = Algos.changeMST(mst, myGraph.getVertex(ver1), myGraph.getVertex(ver2), minWeight);
		System.out.println("");
		System.out.println("Changed MST graph:");
		newMST.printGraph();
	}
}