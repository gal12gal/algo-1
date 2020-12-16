public class Heap {
	
	// The weights to get to the vertex mapped by verticals id
	// will contain: [[vertexid, weight], [vertexid, weight] ...]
	private int[][] weights;
	
	// The positions to keep track of which vertex is on which position in weight
	private int[] positions;
	
	// The heap size
	private int size;
	
	/**
	 * Constructor - building a default heap, all weights infinity
	 * O(n) 
	 */
	public Heap(int numVertex) {
		size = numVertex;
		weights = new int[size][2];
		positions = new int[size];
		for (int i = 0; i < size; i++) {
			weights[i][0] = i;
			weights[i][1] = Integer.MAX_VALUE;
			positions[i] = i;
		}
	}
	
	/**
	 * Get the [vertexid, weight] of the minimum weight in the heap
	 * O(1)
	 * @return [vertexid, weight] of the minimum weight
	 */
	public int[] minimum(){
		return weights[0];
	}
	
	/**
	 * Removing the minimum from the heap
	 * O(log(n))
	 * @return the minimum we just removed
	 */
	public int[] extractMin(){
		int[] minWeight = minimum();
		int[] lastWeight = weights[size - 1];
		
		// replace root weight with last weight
		weights[0] = lastWeight;
		weights[size - 1] = minWeight;
		
		// replace positions accordingly
		positions[minWeight[0]] = size-1;
		positions[lastWeight[0]] =  0;
		
		// update size
		size--;
		
		// reorder heap
		heapify(0);
		
		return minWeight;
	}
	
	/**
	 * This function is updating weight for vertex
	 * O(log(n))
	 * @param vertexId the vertex to update it's weight
	 * @param weight the new weight
	 */
	public void updateWeight(int vertexId, int weight) {
		
		// Getting the position of the weight to update
		int index = positions[vertexId];
		
		// Setting the vertex to the new weight
		weights[index][1] = weight;
		
		// fixing the heap
		// from down to up, if the new weight smaller than his parent, switching them
		// this is O(log(n))
		int parent = (index-1)/2;
		while (index > 0 && weights[index][1] < weights[parent][1]) {
			int[] currentWeight = weights[index];
			int[] parentWeight = weights[parent];
			
			// Swapping the weights
			weights[index] = parentWeight;
			weights[parent] = currentWeight;
						
			// Swapping the positions
			positions[currentWeight[0]] = parent;
			positions[parentWeight[0]] = index;
			
			// moving to the father index
			index = parent;
			parent = (parent - 1) / 2;
		}
	}
	
	/**
	 * Heapify
	 * O(log(n))
	 * @param index 
	 */
	private void heapify(int index) {
		// Getting the sons indexes
		int indexLeft = index*2 + 1;
		int indexRight = index*2 + 2;
		
		int minIndex = index;
		
		// Getting the minimum of the left/right/parent
		
		if (indexLeft < size && weights[minIndex][1] > weights[indexLeft][1]) {
			minIndex = indexLeft;
		}
		
		if (indexRight < size && weights[minIndex][1] > weights[indexRight][1]) {
			minIndex = indexRight;
		}

		// If the minimum is not the parent the heap is not good and we need to order it
		if (minIndex != index) {
			int[] minWeight = weights[minIndex];
			int[] otherWeight = weights[index];
			
			// Swapping the weights
			weights[minIndex] = otherWeight;
			weights[index] = minWeight;
			
			// Swapping the positions 
			positions[minWeight[0]] = index;
			positions[otherWeight[0]] = minIndex;
			
			// Ordering if needed the weight we just replaced
			heapify(minIndex);
		}
	}
	
	/**
	 * Checking if heap is empty
	 * @return true if empty, false if not
	 */
	public boolean isEmpty() {
		return size == 0;
	}
}