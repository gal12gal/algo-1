
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
