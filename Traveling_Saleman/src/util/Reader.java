package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import data.Ville;
import data.VilleFactory;

public class Reader {

	public static int[] traitementFichier(String file) throws IOException{
		VilleFactory vf = VilleFactory.getINSTANCE();
		BufferedReader br = new BufferedReader( new FileReader(file) );
		ArrayList<String> listeLine = new ArrayList<String>();
		HashMap<Integer,Ville> listeVilles = vf.getListeVilles();;
		int[] tableauPermutationDeDepart = new int[100];

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
			tableauPermutationDeDepart[idTmp-1] = idTmp-1;
		}
		vf.setListeVilles(listeVilles);
		br.close();
		return tableauPermutationDeDepart;
	}

}
