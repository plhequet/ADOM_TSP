package util;

public class Swap {

	/**
	 * 
	 */
	public Swap() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param tableauPermutationDeBase
	 * @param indice1
	 * @param indice2
	 * @return
	 */
	public static int[] swap(int[] tableauPermutationDeBase, int indice1, int indice2) {
		int tmp;
		int[] tableauPermutationVoisin;
		tableauPermutationVoisin = tableauPermutationDeBase.clone();
		tmp = tableauPermutationVoisin[indice1];
		tableauPermutationVoisin[indice1] = tableauPermutationVoisin[indice2];
		tableauPermutationVoisin[indice2] = tmp;
		return tableauPermutationVoisin;
	}

	
	public int[] premierVoisinSwap(int[] tableauPermutationDeBase){
		return null;
	}
	
	public int[] meilleurVoisinSwap(int[] tableauPermutationDeBase){
		return null;
	}
}
