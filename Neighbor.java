// Yuval Azoulai 206570186
// Gal Mashiah 318192127

public class Neighbor {
	private int weight;

	private Vertex destination;

	public Neighbor(Vertex dest, int weightInput) {
		weight = weightInput;
		destination = dest;
	}
	public Vertex getDestination() {
		return destination;
	}
	public int getWeight() {
		return weight;
	}
}
