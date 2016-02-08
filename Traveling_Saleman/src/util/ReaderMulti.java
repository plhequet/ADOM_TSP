package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import data.Ville;

import data.VilleFactoryMulti;

public class ReaderMulti {
	
	public static VilleFactoryMulti traitementFichier(String file, VilleFactoryMulti vfm) throws IOException{
		BufferedReader br = new BufferedReader( new FileReader(file) );
		ArrayList<String> listeLine = new ArrayList<String>();
		HashMap<Integer,Ville> listeVilles = vfm.getListeVilles();
		//int[] tableauPermutationDeDepart = new int[100];

		//Lecture pour séparer le fichier en ligne
		String line = br.readLine();
		while(!"EOF".equals(line)){
			listeLine.add(line);
			line = br.readLine();
		}

		//Traitement des lignes
		for(String correctLine : listeLine){
			int idTmp = Integer.parseInt(correctLine.split(" ")[0]);
			double xTmp = Double.parseDouble(correctLine.split(" ")[1]);	
			double yTmp = Double.parseDouble(correctLine.split(" ")[2]);
			Ville villeTmp = new Ville(idTmp-1,xTmp,yTmp);
			listeVilles.put(idTmp-1,villeTmp);
			//tableauPermutationDeDepart[idTmp-1] = idTmp-1;
		}
		vfm.setListeVilles(listeVilles);
		br.close();
		return vfm;
	}
	
}
