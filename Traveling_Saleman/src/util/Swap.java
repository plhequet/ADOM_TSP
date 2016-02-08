package util;

public class Swap {	
	
	private int[] path;
	private int[][] distanceMatrix;
	
	private static Calcul calcul = new Calcul();
	
	
	public Swap(int[][] distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}
	
	/**
	 * Constructor of a path that it's better (in cost-sense) than the given one
	 * @param path
	 * @param distanceMatrix
	 */
	public void swap(int[] path, final boolean firstImprovement) {
		this.path = path;
		
		int bestGain = Integer.MAX_VALUE;
		int bestI = Integer.MAX_VALUE;
		int bestJ = Integer.MAX_VALUE;

		while(bestGain > 0) {
			bestGain = 0;

			for(int i = 0; i < path.length - 1; i++) {
				for(int j = i+1; j < path.length; j++) { // i+1
					if(i!=j) {
						int gain = computeGain(i, j);

						if(gain < bestGain) {
							bestGain = gain;
							bestI = i;
							bestJ = j;
							if(firstImprovement == true) {
								break;
							} 
							exchange(bestI, bestJ);
						}
					}
				}
				if(firstImprovement == true) {
					break;
				}
			}
			if(firstImprovement == true && bestI != Integer.MAX_VALUE && bestJ != Integer.MAX_VALUE) {
				exchange(bestI, bestJ);
			}
		}
	}
	
	/**
	 * Compute the gain if we exchange edge (path[cityIndex1],path[cityIndex1]+1) and 
	 * (path[cityIndex2],path[cityIndex2]+1) with
	 * (path[cityIndex1]+1,path[cityIndex2]+1) and (path[cityIndex1],path[cityIndex2])
	 * @param cityIndex1
	 * @param cityIndex2
	 * @return the gain of the change
	 */
	public int computeGain(final int cityIndex1, final int cityIndex2) {
		
		int src1 = path[cityIndex1];
		int src2 = path[cityIndex2];
		
		int dest1 = calcul.getDestination(path, cityIndex1);
		int dest2 = calcul.getDestination(path, cityIndex2);
		
		return ((distanceMatrix[src1][src2] + distanceMatrix[dest1][dest2]) - (distanceMatrix[src1][dest1] + distanceMatrix[src2][dest2]));
	}
	
	/**
	 * Make the change (path[cityIndex1],path[cityIndex1]+1) and 
	 * (path[cityIndex2],path[cityIndex2]+1) with
	 * (path[cityIndex1]+1,path[cityIndex2]+1) and (path[cityIndex1],path[cityIndex2])
	 * @param cityIndex1
	 * @param cityIndex2
	 */
	public void exchange(final int cityIndex1, final int cityIndex2) {

		int tmp = path[cityIndex1];
		path[cityIndex1] = path[cityIndex2];
		path[cityIndex2] = tmp;
		
	}
	
	/**
	 * @return path 
	 */
	public int[] getPath() {
		return path;
	}
	
	/**
	 * @param the path to set
	 */
	public void setPath(final int[] path) {
		this.path = path;
	}
	
}
