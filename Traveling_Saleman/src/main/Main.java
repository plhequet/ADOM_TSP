package main;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import data.Ville;
import data.VilleFactory;
import util.Calcul;
import util.Heuristique;
import util.Reader;
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
		double[][] resultat = calcul.matriceCout(listeVilles);


		//		//afficher la matrice
		//		System.out.println("Martice des couts : ");
		//		for(int i=0 ; i<100 ; i++){
		//			System.out.println();
		//			for(int j=0; j<100; j++){
		//				System.out.print(resultat[i][j]+" | ");
		//			}
		//			System.out.print("\n");
		//		}
		//		System.out.print("\n");
		//
		//		//On mélange la liste et on fait une evaluation
		//
		//		for(int i=0; i<10; i++){
		//			Set<Integer> keys = listeVilles.keySet();
		//			int[] array = new int[keys.size()];
		//			int index = 0;
		//			for(Integer element : keys) array[index++] = element.intValue();
		//			int [] listeVillesShuffled = array.clone();
		//			calcul.shuffleArray(listeVillesShuffled);
		//			double testEvaluation = calcul.evaluation(listeVillesShuffled,resultat);
		//			System.out.println("Solution candidate d'une méthode gloutonne sur une liste de permutation aléatoire: " + testEvaluation +"\n");
		//		}
		//		
		//		int max = 30;
		//		
		//		for(int i=0; i<max; i++){
		//			
		//			System.out.println("---------------------------- VALEUR DE I : " + i+ " ----------------------------\n");
		//			 
		//			//Test méthode plus proche voisin
		//			
		//			int[]solutionCandidate = Heuristique.heursitiquePlusProcheVoisin(resultat,i);
		//			double testVoisin = Calcul.evaluation(solutionCandidate,resultat);
		//			System.out.println("Poids d'une solution candidate de la méthode du plus proche voisin:  " + testVoisin +"\n");
		//			
		//			//Test du swap
		//			
		//			int[] testSwap = Swap.swap(solutionCandidate, i, max+i);
		//			double valeurSwap = Calcul.evaluation(testSwap,resultat);
		//			System.out.println("Poids d'une solution candidate de la méthode du swap:  " + valeurSwap +"\n");
		//			
		//			//Test du two_opt
		//			
		//			int[] testtwoOpt = TwoOpt.twoOpt(solutionCandidate, i);
		//			double valeurtwoOpt = Calcul.evaluation(testtwoOpt,resultat);
		//			System.out.println("Poids d'une solution candidate de la méthode du two-opt:  " + valeurtwoOpt +"\n");
		//		}		
		//		
		//		int[][] pop = Population.genererPopulation(10,tableauPermutationDeBase);
		//		for(int i=0; i<pop.length;i++){
		//			for(int j=0; j<pop[i].length;j++){
		//				System.out.print(pop[i][j]+ " | ");
		//			}
		//			System.out.println("\n");
		//		}
		//	
		//		int[][]par = Population.selectionnerParents(pop);
		//		for(int i=0; i<par.length;i++){
		//			for(int j=0; j<par[i].length;j++){
		//				System.out.print(par[i][j]+ " | ");
		//			}
		//			System.out.println("\n");
		//		}
		//		
		//		int[]enfant = Population.croissementParents(par);
		//		for(int i=0; i<enfant.length;i++){
		//			System.out.print(enfant[i]+ " | ");
		//		}
		//		System.out.println();
		//		int[]enfantMute = Population.mutationEnfant(enfant);
		//		for(int i=0; i<enfantMute.length;i++){
		//			System.out.print(enfantMute[i]+ " | ");
		//		}
		int[][]matrix = new int[resultat.length][resultat[1].length];
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[0].length; j++)
				matrix[i][j] = (int) resultat[i][j];
		}
		TwoOpt to = new TwoOpt(matrix);
		double total = 0.0;
		for(int j=0;j<resultat[1].length;j++){
//			int rng = ThreadLocalRandom.current().nextInt(resultat[1].length);
			int[]solutionCandidate = Heuristique.heursitiquePlusProcheVoisin(resultat,j);
			to.twoOpt(solutionCandidate, false);
			int[] resultatTO = to.getPath();
//			
//			for(int i=0; i<resultatTO.length;i++){
//				System.out.print(resultatTO[i]+ " | ");
//			}
			total = total + calcul.evaluation(resultatTO,resultat);
			System.out.println(calcul.evaluation(resultatTO,resultat));
		}
		System.out.println("---- " + total/resultat[1].length);
		System.out.println("---- ---- " + (total/resultat[1].length)/21282);
		
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
	public double choix(int intialisation, int mouvement, int voisinage, int[] tableauPermutationDeBase, double[][]matice){
		double result=0.0;
		Calcul calcul = new Calcul();
		int[]tableauPermutationUtilise;
		if(intialisation==0){
			tableauPermutationUtilise = tableauPermutationDeBase.clone();
			calcul.shuffleArray(tableauPermutationUtilise);
		}
		else if(intialisation==1){
			tableauPermutationUtilise = Heuristique.heursitiquePlusProcheVoisin(matice,1);
		}

		return result;
	}

}
