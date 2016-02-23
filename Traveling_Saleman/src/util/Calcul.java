package util;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import data.Ville;
import data.VilleFactory;

/**
*
* @author hequet
*
*/
public class Calcul {


	/**
	* Méthode qui calcule la distanceEuclidienne selon les coordonnées fournies
	*
	* Rappel de formule de la distance Euclidienne :  racine((xb-xa)^2+(yb-ya)^2)
	*
	* @param xb
	* @param xa
	* @param yb
	* @param ya
	* @return distance
	*/
	private double distanceEuclidienne(double xb, double xa, double yb, double ya){
		double resultat_parenthese_gauche = Math.pow((xb-xa), 2);
		double resultat_parenthese_droite = Math.pow((yb-ya), 2);
		double resultat_Final = Math.sqrt(resultat_parenthese_gauche + resultat_parenthese_droite);
		return resultat_Final;
	}

	/**
	* Méthode qui génère la matrice de distance entre les villes.
	*
	* @param listeVilles
	* @return matrice
	*/
	public int[][] matriceCout(HashMap<Integer,Ville> listeVilles){
		int[][] matrice_Final = new int[100][100];
		double xa, ya, xb, yb;
		for(int i=0 ; i<100 ; i++){
			xa = listeVilles.get(i).getxCoordonnee();
			ya = listeVilles.get(i).getyCoordonnee();
			for(int j=0; j<100; j++){
				xb = listeVilles.get(j).getxCoordonnee();
				yb = listeVilles.get(j).getyCoordonnee();
				matrice_Final[i][j] = (int) Math.round(distanceEuclidienne(xb,xa,yb,ya)) ;
			}
		}
		return matrice_Final;
	}

	/**
	* Méthode qui évalue la distance total du parcours passé en utilisant les valeurs de la matrice des distances
	*
	* @param tableauPermutation
	* @param matrice
	* @return distance totale
	*/
	public int evaluation(int[]tableauPermutation, int[][] matrice){
		VilleFactory vf = VilleFactory.getINSTANCE();
		HashMap<Integer,Ville> listeVille = vf.getListeVilles();
		int resultat = 0;
		for(int i=0; i<tableauPermutation.length; i++){
			int j = tableauPermutation[i];
			int g ;
			//si on visite la dernier ville
			if(tableauPermutation.length-1 == i){
				int v = listeVille.get(j).getId();
				resultat += matrice[v][0];
			}
			else {
				int v = listeVille.get(j).getId();
				g = tableauPermutation[i+1];
				int w = listeVille.get(g).getId();
				resultat += matrice[v][w];

			}
		}
		return  resultat;
	}

	/**
	* Méthode qui mélange un tableau de valeur passé en paramètre
	*
	* @param ar
	*/
	public void shuffleArray(int[] ar)
	{
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--)
		{

			int index = rnd.nextInt(i+1);
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	/**
	* Fonction qui regarde si une ville est deja présente dans le tableau
	*
	* @param path
	* @param city
	* @return true or false
	*/
	public static boolean isCityInPath(int[] path, int city) {
		for(int i = 0; i < path.length; i++) {
			if(path[i] == city) {
				return true;
			}
		}
		return false;
	}

	/**
	* Fonction qui compare deux tableaux et retourne vrai s'ils sont les mêmes
	*
	* @param path1
	* @param path2
	* @return true or false
	*/
	public static boolean isSamePath(int[] path1, int[] path2) {
		boolean b = true;
		for (int i = 0; i < path2.length; i++) {
			if (path2[i] != path1[i]) {
				b = false;
			}
		}
		return b;
	}

	/**
	* Fonction qui retourne la prochaine ville
	*
	* @param path
	* @param srcIndex
	* @return ville
	*/
	public int getDestination(int[] path, int srcIndex) {
		if(srcIndex + 1 == path.length) {
			return path[0];
		}
		return path[srcIndex + 1];
	}

	/**
	* Fonction qui retourne le numéro de la prochaine ville
	*
	* @param path
	* @param srcIndex
	* @return numéro de la ville
	*/
	public int getIndexOfDestination(int[] path, int srcIndex) {
		if(srcIndex + 1 == path.length) {
			return 0;
		}
		return srcIndex + 1;
	}

}
