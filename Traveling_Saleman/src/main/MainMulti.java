package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import data.Ville;
import data.VilleFactoryMulti;
import multi.CalculMulti;
import multi.Filtre;
import util.Calcul;
import util.ReaderMulti;

public class MainMulti {
	
	public static void main(String[] args) {
		try {
			new MainMulti("/home/m2miage/hequet/Documents/ADOM/kroA100.tsp","/home/m2miage/hequet/Documents/ADOM/kroB100.tsp");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public MainMulti(String file1, String file2) throws IOException {
		VilleFactoryMulti vfm1 = new VilleFactoryMulti();
		VilleFactoryMulti vfm2 = new VilleFactoryMulti();
		vfm1 = ReaderMulti.traitementFichier(file1,vfm1);
		vfm2 = ReaderMulti.traitementFichier(file2,vfm2);
		HashMap<Integer, Ville> listeVilles1 = vfm1.getListeVilles();
		HashMap<Integer, Ville> listeVilles2 = vfm2.getListeVilles();
		Calcul calcul = new Calcul();
		CalculMulti calculMulti = new CalculMulti();
		int[][] matrix1 = calculMulti.matriceCout(listeVilles1);
		int[][] matrix2 = calculMulti.matriceCout(listeVilles2);
		int[][] ensemble = new int[500][2];
		for(int k = 0; k<500-1 ;k++){
			Set<Integer> keys = listeVilles1.keySet();
			int[] array = new int[keys.size()];
			int index = 0;
			for(Integer element : keys) array[index++] = element.intValue();
			int[] listeVillesShuffled = array.clone();
			calcul.shuffleArray(listeVillesShuffled);
			ensemble[k] = calculMulti.evaluation(listeVillesShuffled,matrix1,matrix2,vfm1);
			//System.out.println(testEvaluation[0] +" " + testEvaluation[1]);	
		}
		afficher(ensemble);
		System.out.println("--------------------------");
		int[][] ensembleFiltre = Filtre.offLine(ensemble);
		afficher(ensembleFiltre);
	}
	
	public void afficher(int[][] ensemble){
		for(int i=0;i<ensemble.length-1;i++){
			boolean test = (ensemble[i][0] == 0) && (ensemble[i][1] == 0);
			if(!(test))
			System.out.println(ensemble[i][0] +" " + ensemble[i][1]);
		}
	}
}