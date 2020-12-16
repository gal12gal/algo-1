import java.util.LinkedList;

public class Vertex {
	
	private int id;

	private LinkedList<Neighbor> neighbors;
	
	
	public Vertex(int id) {
		id = id;
		neighbors = new LinkedList<Neighbor>();
	}
	
	
	public int getID() {
		return id;
	}
	
	public LinkedList<Neighbor> getNeighbors(){
		return neighbors;
	}	
	
	public void addNeighbor(Vertex neighbor, Integer weight) {
		neighbors.add(new Neighbor(neighbor, weight));
	}
	
	
	public void removeNeighbor(Vertex neighbor) {
		neighbors.removeIf(n -> n.getDest().getID() == neighbor.getID());
	}
	
	
	public String toString() {
		int count = 0;
		String output = String.format("id %s, adjacent: [", id);
		for (Neighbor n : neighbors) {
			output = String.format("%s id: %s weight: %s,", output, n.getDest().getID(), n.getWeight());
				count++;
		}
		output = output.substring(0, output.length()-1);
		output = String.format("%s]", output);
		return output;
	}
}