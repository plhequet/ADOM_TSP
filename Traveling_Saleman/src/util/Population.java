package util;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import data.Ville;
import data.VilleFactory;

public class Population {

	private static Calcul calcul = new Calcul();

	/**
	 * 
	 * Fonction qui génère une population de taille donnée,
	 * sans doublon dont les solutions sont générés de façon heuristique
	 * 
	 * @param nbMembres
	 * @param permutationBase
	 * @return population
	 */
	public static int[][] genererPopulation(int nbMembres,int[] permutationBase){
		VilleFactory vf = VilleFactory.getINSTANCE();
		HashMap<Integer, Ville> listeVilles = vf.getListeVilles();
		int[][] population = new int[nbMembres][permutationBase.length];
		for(int i=0;i<nbMembres;i++){
			double[][] matrix = calcul.matriceCout(listeVilles);
			population[i] = Heuristique.heursitiquePlusProcheVoisin(matrix,i);
		}
		if(!isPopulationSansDoublon(population)){
			int[] permutation;
			permutation = permutationBase.clone();
			calcul.shuffleArray(permutation);
			population = genererPopulation(nbMembres, permutation);
		}
		return population;
	}

	/**
	 * 
	 * Fonction qui génère une population de taille donnée,
	 * sans doublon dont les solutions sont générés de façon aléatoire
	 * 
	 * @param nbMembres
	 * @param permutationBase
	 * @return population
	 */
	public static int[][] genererAleatoirementPopulation(int nbMembres,int[] permutationBase){
		int[][] population = new int[nbMembres][permutationBase.length];
		int[] permutation;
		for(int i=0;i<nbMembres;i++){
			permutation = permutationBase.clone();
			calcul.shuffleArray(permutation);
			population[i] = permutation;
		}
		if(!isPopulationSansDoublon(population)){
			population = genererAleatoirementPopulation(nbMembres, permutationBase);
		}
		return population;
	}

	/**
	 * 
	 * Fonction qui vérifie si il existe ou non des doublons dans la population fournie
	 * 
	 * @param population
	 * @return true or false
	 */
	public static boolean isPopulationSansDoublon(int[][] population){
		boolean b = true;
		for(int i=0;i<population.length;i++){
			for(int j=0;j<population.length;j++){
				if(!(i==j)){
					if(Calcul.isSamePath(population[i],population[j])){
						b = false;
					}
				}
			}
		}
		return b;
	}

	/**
	 * Fonction qui selectionne deux parents aléatoirement dans la population.
	 * 
	 * @param population
	 * @return liste des deux parents
	 */
	public static int[][] selectionnerParents(int[][] population){
		int[][] parent = new int[2][population[1].length];
		int rng1, rng2;
		rng1 = ThreadLocalRandom.current().nextInt(population.length);
		rng2 = ThreadLocalRandom.current().nextInt(population.length);
		while(rng1==rng2){
			rng2 = ThreadLocalRandom.current().nextInt(population.length);
		}
		parent[0]= population[rng1];
		parent[1]= population[rng2];
		return parent;
	}

	/**
	 * 
	 * Fonction qui croisent les deux parents passés en paramètre,
	 *  séparer et recombiner selon un indince aléatoire pour donner un enfant
	 * 
	 * @param listParents
	 * @return l'enfant
	 */
	public static int[] croissementParents(int[][] listParents){
		int[] enfant = new int[listParents[1].length];

		//On met toute les valeurs a -1 afin de pas fausser l'évaluation
		// 	de !isCityInPath(enfant,villeLue) quand la ville est la ville 0
		for(int a=0;a<enfant.length;a++){
			enfant[a]=-1;
		}

		int rng1 = ThreadLocalRandom.current().nextInt(listParents[0].length);
		int rng2 = ThreadLocalRandom.current().nextInt(listParents[1].length);
		if(rng1>rng2){
			int tmp = rng2;
			rng2 = rng1;
			rng1 = tmp;
		}
		for(int i=rng1; i<rng2; i++ ){
			enfant[i]=listParents[0][i];
		}
		int k=0;
		for(int j=0; j<listParents[1].length;j++){

			int villeLue = listParents[1][j];
			if(!(j<=rng1 && rng2<j)){
				if(!Calcul.isCityInPath(enfant,villeLue)){
					enfant[k]=listParents[1][j];
					k=k+1;
				}
			}
			if(k>=rng1 && rng2>=k){
				k=rng2;
			}
		}
		return enfant;
	}

	/**
	 * 
	 * Fonction qui mutent le tableau de permutation via un swap aléatoire
	 * 
	 * @param enfant
	 * @return l'enfant muté
	 */
	public static int[] mutationEnfant(int[] enfant){
		int indice1 = ThreadLocalRandom.current().nextInt(enfant.length);
		int indice2 = ThreadLocalRandom.current().nextInt(enfant.length);
		return Swap.swap(enfant, indice1, indice2);
	}

	public void memetique(){
		
	}
}
