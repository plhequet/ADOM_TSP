package multi;

public class Filtre {


	public Filtre() {
		super();
	}

	public static int[][] offLine(int[][] ensemble){
		int[][] resultat = new int[ensemble.length][2];
		boolean[] domine = new boolean[ensemble.length];
		for(int k = 0; k<(ensemble.length)-1;k++){
			domine[k] = true;
		}		
		for(int i=0; i<(ensemble.length)-1; i++){
			if(domine[i]){
				for(int j=0; j<(ensemble.length)-1; j++){
					if(domine[j]){
						int a = ensemble[i][0];
						int b = ensemble[i][1];
						int aPrime = ensemble[j][0];
						int bPrime = ensemble[j][1];
						if((aPrime < a) && (bPrime < b)){
							domine[i]=false;
						}
					}
				}
			}
		}
		int cmp = 0;
		for(int l = 0; l<(ensemble.length)-1;l++){
			if(domine[l] == true){
				resultat[cmp]= ensemble[l];
				cmp++;
			}		
		}			
		return resultat;
	}


}
