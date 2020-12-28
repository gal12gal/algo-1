// Yuval Azoulai 206570186
// Gal Mashiah 318192127

public class Heap {
	private int heapSize;
	private int[] locations;
	private int[][] weights;
	

	public Heap(int numVertex) {
		heapSize = numVertex;
		weights = new int[heapSize][2];
		locations = new int[heapSize];
		for (int i = 0; i < heapSize; i++) {
			weights[i][0] = i;
			weights[i][1] = Integer.MAX_VALUE;
			locations[i] = i;
		}
	}
	
	public int[] minimum(){
		return weights[0];
	}
	
	private void heapify(int index) {
		int leftIndex = index*2 + 1;
		int rightIndex = index*2 + 2;
		int minIndex = index;
		if (leftIndex < heapSize && weights[minIndex][1] > weights[leftIndex][1]) {
			minIndex = leftIndex;
		}
		if (rightIndex < heapSize && weights[minIndex][1] > weights[rightIndex][1]) {
			minIndex = rightIndex;
		}
		if (minIndex != index) {
			int[] minWeight = weights[minIndex];
			int[] otherWeight = weights[index];
			weights[minIndex] = otherWeight;
			weights[index] = minWeight;
			locations[minWeight[0]] = index;
			locations[otherWeight[0]] = minIndex;
			heapify(minIndex);
		}
	}
	public int[] extractMin(){
		int[] prevWeight = weights[heapSize - 1];
		int[] minWeight = minimum();
		weights[0] = prevWeight;
		weights[heapSize - 1] = minWeight;
		locations[minWeight[0]] = heapSize-1;
		locations[prevWeight[0]] =  0;
		heapSize--;
		heapify(0);
		return minWeight;
	}
	
	public void updateWeight(int vertexId, int weight) {
		int index = locations[vertexId];
		weights[index][1] = weight;
		int parent = (index-1)/2;
		while (index > 0 && weights[index][1] < weights[parent][1]) {
			int[] currentWeight = weights[index];
			int[] parentWeight = weights[parent];
			weights[index] = parentWeight;
			weights[parent] = currentWeight;
			locations[currentWeight[0]] = parent;
			locations[parentWeight[0]] = index;
			index = parent;
			parent = (parent - 1) / 2;
		}
	}

	public boolean isEmpty() {
		return heapSize == 0;
	}
}
