import java.util.LinkedList;

public class Vertex {
	private int id;
	private LinkedList<Neighbor> neighbors;

	public Vertex(int idInput) {
		id = idInput;
		neighbors = new LinkedList<Neighbor>();
	}
		public int getID() {
		return id;
	}
	public LinkedList<Neighbor> getAllNeighbors(){
		return neighbors;
	}
	public void addNeighbor(Vertex neighbor, Integer weight) {
		neighbors.add(new Neighbor(neighbor, weight));
	}
	public void removeNeighbor(Vertex neighbor) {
		neighbors.removeIf(n -> n.getDestination().getID() == neighbor.getID());
	}
	public String toString() {
		String printMe = String.format("id %s, adjacent: [", id);
		for (Neighbor n : neighbors) {
				printMe = String.format("%s id: %s weight: %s,", printMe, n.getDestination().getID(), n.getWeight());
		}
		printMe = printMe.substring(0, printMe.length()-1);
		printMe = String.format("%s]", printMe);
		return printMe;
	}
}
