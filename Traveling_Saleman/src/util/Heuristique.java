package util;

public class Heuristique {

	
	/**
	 * 
	 * @param matrix
	 * @param firstTown
	 * @return
	 */
	public static int[] heursitiquePlusProcheVoisin(int[][] matrix, int firstTown){
		int[] permutation = new int[matrix.length];
		int currTown=firstTown;
		int minIndex=0;
		boolean[] visitedTowns = new boolean[matrix.length];
		visitedTowns[currTown]=true;

		permutation[0]=firstTown;
		for(int i=1; i<matrix.length; i++){
			
			if(matrix[currTown][minIndex]==0)minIndex++;
			for(int j=0; j<matrix.length; j++){
				if(!visitedTowns[j]){
					if((matrix[currTown][j]!=0 && matrix[currTown][minIndex]>matrix[currTown][j])){
						
						minIndex=j;
					}
				}
			}
			
			
			permutation[i]=minIndex;
			currTown=minIndex;
			visitedTowns[minIndex]=true;
			for(int k=0;k<visitedTowns.length;k++){
				if(!visitedTowns[k]){
					minIndex=k;break;
				}
			}
			
		}
		
		return permutation;
	}
}
