package main;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import data.Ville;
import data.VilleFactory;
import util.Calcul;
import util.Heuristique;
import util.Reader;
import util.Swap;
import util.TwoOpt;

public class Main {

	public static void main(String[] args) {
		try {
			new Main("/home/m2miage/hequet/Documents/ADOM/kroA100.tsp");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	public Main(String file) throws IOException{
		VilleFactory vf = VilleFactory.getINSTANCE();
		int[] tableauPermutationDeBase = Reader.traitementFichier(file);
		HashMap<Integer, Ville> listeVilles = vf.getListeVilles();
		Calcul calcul = new Calcul();
		int[][] resultat = calcul.matriceCout(listeVilles);


		int[][]matrix = new int[resultat.length][resultat[1].length];
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[0].length; j++)
				matrix[i][j] = (int) resultat[i][j];
		}
//		TwoOpt to = new TwoOpt(matrix);
//		int total = 0;
//		for(int j=0;j<resultat[1].length;j++){
//			int[]solutionCandidate = Heuristique.heursitiquePlusProcheVoisin(resultat,j);
//			to.twoOpt(solutionCandidate, false);
//			int[] resultatTO = to.getPath();
//
//			total = total + calcul.evaluation(resultatTO,resultat);
//			System.out.println(calcul.evaluation(resultatTO,resultat));
//		}
//		double calculeeeeee = total/resultat[1].length;
//		System.out.println("---- " + calculeeeeee);
//		System.out.println("---- ---- " + (calculeeeeee)/21282);
		
		
//		Swap sw = new Swap(matrix);
//		int total = 0;
//		for(int j=0;j<resultat[1].length;j++){
//			int[]solutionCandidate = Heuristique.heursitiquePlusProcheVoisin(resultat,j);
//			sw.swap(solutionCandidate, true);
//			int[] resultatTO = sw.getPath();
//
//			total = total + calcul.evaluation(resultatTO,resultat);
//			System.out.println(calcul.evaluation(resultatTO,resultat));
//		}
//		double calculeeeeee = total/resultat[1].length;
//		System.out.println("---- " + calculeeeeee);
//		System.out.println("---- ---- " + (calculeeeeee)/21282);
	}
	/**
	 * Valeur qui appele la fonction du type d'initialisation voulue, mouvement voulu et le type de voisinage choisi sur un tableau de permutation donné.
	 * 
	 * @param intialisation (0 -> aleatoire, 1 -> heuristique constructive)
	 * @param mouvement (0 -> meilleur voisin améliorant, 1 -> premier voisin améliorant)
	 * @param voisinage (0 -> swap, 1 -> twoopt)
	 * @param tableauPermutationDeBase
	 * @return
	 */
	public double choix(int intialisation, int mouvement, int voisinage, int[] tableauPermutationDeBase, int[][]matrice){
		double result=0.0;
		int[] path = new int[tableauPermutationDeBase.length];
		Calcul calcul = new Calcul();
		int[]tableauPermutationUtilise = new int[tableauPermutationDeBase.length];
		if(intialisation==0){
			tableauPermutationUtilise = tableauPermutationDeBase.clone();
			calcul.shuffleArray(tableauPermutationUtilise);
		}
		else if(intialisation==1){
			tableauPermutationUtilise = Heuristique.heursitiquePlusProcheVoisin(matrice,1);
		}
		if(voisinage==0){
			Swap swap = new Swap(matrice);
			if(mouvement==0){
				swap.swap(tableauPermutationUtilise, false);
			}
			else if(mouvement==1){
				swap.swap(tableauPermutationUtilise, true);
			}
			path = swap.getPath();
		}
		else if(voisinage==1){
			TwoOpt twoOpt = new TwoOpt(matrice);
			if(mouvement==0){
				twoOpt.twoOpt(tableauPermutationUtilise, false);
			}
			else if(mouvement==1){
				twoOpt.twoOpt(tableauPermutationUtilise, true);
			}
			path = twoOpt.getPath();
		}
		result = calcul.evaluation(path,matrice);
		return result;
	}

}
