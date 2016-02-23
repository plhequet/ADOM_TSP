package util;

/**
 *
 * @author hequet
 *
 */
public class TwoOpt {

	private int[] path;
	private int[][] distanceMatrix;

	private static Calcul calcul = new Calcul();


	public TwoOpt(int[][] distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}

	/**
	 *
	 * Méthode qui construit le meilleur (premier) chemin possible avec swap
	 *
	 * @param path
	 * @param distanceMatrix
	 */
	public void twoOpt(int[] path, final boolean firstImprovement) {
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
	 *
	 * Méthode qui calcul le gain de l'échange entre la ville1 et la ville2
	 *
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
	*
	* Fonction qui echange la ville1 avec la ville2
	*
	* @param cityIndex1
	* @param cityIndex2
	*/
	public void exchange(final int cityIndex1, final int cityIndex2) {

		int indexDest1 = calcul.getIndexOfDestination(path, cityIndex1);
		int indexDest2 = calcul.getIndexOfDestination(path, cityIndex2);

		int[] pathNew = new int[path.length];
		int indexOfPathNew = 0;

		int i = 0;
		while(i <= cityIndex1) {
			if(Calcul.isCityInPath(pathNew, path[i]) == false) {
				pathNew[indexOfPathNew] = path[i];
				indexOfPathNew++;
			}
			i++;
		}

		i = cityIndex2;
		while(i >= indexDest1) {
			if(Calcul.isCityInPath(pathNew, path[i]) == false) {
				pathNew[indexOfPathNew] = path[i];
				indexOfPathNew++;
			}
			i--;
		}

		i = indexDest2;
		while(i < path.length) {
			if(Calcul.isCityInPath(pathNew, path[i]) == false) {
				pathNew[indexOfPathNew] = path[i];
				indexOfPathNew++;
			}
			i++;
		}

		for(int k = 0; k < pathNew.length; k++) {
			path[k] = pathNew[k];
		}

	}

	/**
	*
	* Méthode pour récupérer le chemin
	*
	* @return path
	*/
	public int[] getPath() {
		return path;
	}

	/**
	*
	* Méthode pour setter le chemin
	*
	* @param path
	*/
	public void setPath(final int[] path) {
		this.path = path;
	}

}
