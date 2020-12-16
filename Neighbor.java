
public class Neighbor {
	
	private int weight;
	
	private Vertex dest;	

	public Neighbor(Vertex dest, int weightInput) {
		weight = weightInput;
		dest = dest;
	}

	
	public int getWeight() {
		return weight;
	}
	
	public Vertex getDest() {
		return dest;
	}
}