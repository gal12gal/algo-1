// Yuval Azoulai 206570186
// Gal Mashiah 318192127

public class Graph {

	private Vertex[] verticals;
	private int verticalsCount;
	

	public Graph(int numVerticals) {
		verticalsCount = numVerticals;
		verticals = new Vertex[numVerticals];
		for (int i = 0; i<verticalsCount; i++) {
			verticals[i] = new Vertex(i);
		}
	}
	public Vertex[] getVerticals() {
		return verticals;
	}

	public int getVerticalsCount() {
		return verticalsCount;
	}
	
	public Vertex getVertex(int id) {
		if (id >= verticalsCount) {
			return null;
		}
		return verticals[id];
	}
	
 
	public void addEdge(int vId, int uId, int weight) {
		Vertex v = getVertex(vId);
		Vertex u = getVertex(uId);
		if (v != null && u != null) {
			v.addNeighbor(u, weight);
			u.addNeighbor(v, weight);
		}
	}
	

	public void removeEdge(int vId, int uId) {
		Vertex v = getVertex(vId);
		Vertex u = getVertex(uId);
		if (v != null && u != null) {
			v.removeNeighbor(u);
			u.removeNeighbor(v);
		}
	}
	
	public void printGraph() {
		for (Vertex v : getVerticals()) {
			System.out.println(v);
		}
	}

	
	
	
}