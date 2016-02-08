package multi;

import java.util.HashMap;

import data.Ville;
import data.VilleFactory;
import data.VilleFactoryMulti;

public class CalculMulti {

	
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
	private int distanceEuclidienne(double xb, double xa, double yb, double ya){
		double resultat_parenthese_gauche = Math.pow((xb-xa), 2);
		double resultat_parenthese_droite = Math.pow((yb-ya), 2);
		double resultat_Final = Math.sqrt(resultat_parenthese_gauche + resultat_parenthese_droite);
		return (int) resultat_Final;
	}

	/**
	 * 
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
				matrice_Final[i][j] = Math.round(distanceEuclidienne(xb,xa,yb,ya)) ;
			}
		}
		return matrice_Final;
	}
	
	/**
	 * 
	 * Fonction qui evalue la qualité d'une solution pour chacun des objectifs
	 * 
	 * @param tableauPermutation
	 * @param matrice1
	 * @param matrice2
	 * @return resultatFinal 
	 */
	public int[] evaluation(int[]tableauPermutation, int[][] matrice1, int[][] matrice2, VilleFactoryMulti vf){
		HashMap<Integer,Ville> listeVille = vf.getListeVilles();
		int[] resultatFinal = new int[2];
		int resultat1 = 0;
		int resultat2 = 0;
		for(int i=0; i<tableauPermutation.length; i++){
			int j = tableauPermutation[i];
			int g ;

			if(tableauPermutation.length-1 == i){
				int v = listeVille.get(j).getId();
				resultat1 += matrice1[v][0];
				resultat2 += matrice2[v][0];
			}
			else {
				int v = listeVille.get(j).getId();
				g = tableauPermutation[i+1];
				int w = listeVille.get(g).getId();
				resultat1 += matrice1[v][w];
				resultat2 += matrice2[v][w];

			}
		}
		resultatFinal[0] = resultat1;
		resultatFinal[1] = resultat2;
		return resultatFinal;
	}
	
}
