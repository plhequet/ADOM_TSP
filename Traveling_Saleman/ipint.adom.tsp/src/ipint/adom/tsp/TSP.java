package ipint.adom.tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author buchart
 *
 */
public class TSP {

	private BufferedReader reader;
	private List<Town> towns = null;
	//private int nbTown;

	public TSP(){
	}

	public TSP(String file){
		try {
			towns = parse(file);
		} catch (IOException e) {
			System.out.println("fail parse " + e.getMessage());
		}
	}

	public List<Town> parse(String file) throws IOException{
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Erreur lecture fichier : " + e.getMessage());
		}
		String line;
		List<Town> towns = new ArrayList<>();
		//nbTown=0;

		while ((line = reader.readLine()) != null) {
			String[] parsedLine = line.split(" ");
			if(parsedLine.length>1){
				//nbTown++;
				towns.add(new Town
						(parsedLine[0],
								Double.parseDouble(parsedLine[1]), 
								Double.parseDouble(parsedLine[2])));
			}
		}

		return towns;
	}

	private double getDistance(Town t1, Town t2){
		//		return Math.sqrt(
		//				((t1.getX()-t1.getY()) * (t1.getX()-t1.getY()))
		//				+ ((t2.getX()-t2.getY()) * (t2.getX()-t2.getY()))
		//				);
		return Math.sqrt(
				((t2.getX()-t1.getX()) * (t2.getX()-t1.getX()))
				+ ((t2.getY()-t1.getY()) * (t2.getY()-t1.getY()))
				);
	}

	public double[][] generateMatrix(List<Town> towns){
		double[][] matrix = new double[towns.size()][towns.size()];

		for(int i=0; i<towns.size(); i++){
			for(int j=i; j<towns.size(); j++){
				if(i==j){
					matrix[i][j]=0;
				}else{
					double dist=Math.round(getDistance(towns.get(i), towns.get(j))); 
					matrix[i][j]=dist;
					matrix[j][i]=dist;
				}
			}
		}

		return matrix;
	}

	public int[] generateRandomPermutation(int length){
		List<Integer> randomList = new ArrayList<>();
		int[] perm = new int[length];
		for(int i=0;i<length;i++){
			randomList.add(i);
		}
		Random r = new Random();
		for(int i=0; i<length;i++){
			if(randomList.size()>1){
				int nextRand = r.nextInt(randomList.size()-1);
				perm[i]=randomList.get(nextRand);
				randomList.remove(nextRand);
			}else{
				perm[i]=randomList.get(0);
			}
		}
		return perm;
	}

	public double eval(double[][] matrix, int[] permutation){
		double dist = 0.0;

		for(int i=1;i<permutation.length;i++){
			dist+=matrix[permutation[i-1]][permutation[i]];
		}
		dist+=matrix[0][matrix.length-1];

		return dist;
	}

	public int[] generateHeuristicPermutation(double [][] matrix, int firstTown){
		int[] permutation = new int[matrix.length];
		int currTown=firstTown;
		int minIndex=0;
		boolean[] visitedTowns = new boolean[matrix.length];
		visitedTowns[currTown]=true;

		permutation[0]=firstTown;
		for(int i=1; i<matrix.length; i++){
			if(matrix[currTown][minIndex]==0)minIndex++;
			for(int j=0; j<matrix.length; j++){
				if(!visitedTowns[j]){
					if((matrix[currTown][j]!=0 && matrix[currTown][minIndex]>matrix[currTown][j])){
						minIndex=j;
					}
				}
			}
			permutation[i]=minIndex;
			currTown=minIndex;
			visitedTowns[minIndex]=true;

			for(int k=0;k<visitedTowns.length;k++){
				if(!visitedTowns[k]){
					minIndex=k;break;
				}
			}
		}
		return permutation;
	}

	private int[] swap(int[] permutation, int ind1, int ind2){
		int[] newPermu = permutation.clone();
		int tmp = newPermu[ind1];
		newPermu[ind1]=newPermu[ind2];
		newPermu[ind2]=tmp;
		return newPermu;
	}

	private int[] randomSwap(int[] permutation){
		Random r = new Random();
		int ind1=r.nextInt(permutation.length);
		int ind2=r.nextInt(permutation.length);
		while(ind1==ind2){
			ind2=r.nextInt(permutation.length);
		}
		int[] newPermu = permutation.clone();
		int tmp = newPermu[ind1];
		newPermu[ind1]=newPermu[ind2];
		newPermu[ind2]=tmp;
		return newPermu;
	}

	private int[] _2_opt(int[] permutation, int ind1, int ind2){
		int[] newPermu=permutation.clone();
		int deb=ind1;
		int fin=ind2;
		while(deb<fin){
			newPermu=swap(newPermu, deb, fin);
			deb++;
			fin--;
		}
		return newPermu;
	}

	/**
	 * Retourne l'indice de la ville à swap pour le meilleur voisin
	 * @param permu
	 * @param matrix
	 * @param firstTown
	 * @return
	 */
	private int[] bestNeighbor(double matrix[][], int[] permu, int voisinnage){
		int[] neighbor;
		double bestDist=eval(matrix, permu);
		double dist;
		int town1=0;
		int town2=0;

		if(voisinnage==0){
			for(int i=0;i<permu.length;i++){
				for(int j=i+1;j<permu.length;j++){
					neighbor=swap(permu, i, j);
					dist=eval(matrix, neighbor);
					if(dist<bestDist){
						bestDist=dist;
						town1=i;
						town2=j;
					}
				}
			}
		}else{
			for(int i=0;i<permu.length;i++){
				for(int j=i+1;j<permu.length;j++){
					neighbor=_2_opt(permu, i, j);
					dist=eval(matrix, neighbor);
					if(dist<bestDist){
						bestDist=dist;
						town1=i;
						town2=j;
					}

				}
			}
		}

		return new int[]{town1,town2};
	}

	private int[] nextNeighbor(double matrix[][], int[] permu, int voisinnage){
		int[] neighbor;
		double bestDist=eval(matrix, permu);
		double dist;
		int town1=0;
		int town2=0;

		if(voisinnage==0){
			for(int i=0;i<permu.length;i++){
				for(int j=i+1;j<permu.length;j++){

					neighbor=swap(permu, i, j);
					dist=eval(matrix, neighbor);
					if(dist<bestDist){
						town1=i;
						town2=j;
						return new int[]{town1,town2};
					}

				}
			}
		}else{
			for(int i=0;i<permu.length;i++){
				for(int j=i+1;j<permu.length;j++){

					neighbor=_2_opt(permu, i, j);
					dist=eval(matrix, neighbor);
					if(dist<bestDist){
						town1=i;
						town2=j;
						return new int[]{town1,town2};
					}

				}
			}
		}



		return new int[]{town1,town2};
	}

	public int[][] generateRandomPopulation(int population, int nbTown){
		int[][] randPop = new int[population][nbTown];

		for(int i=0;i<population;i++){
			randPop[i]=generateRandomPermutation(nbTown);
		}

		return randPop;
	}

	public int[] crossover(int[] permu1, int[] permu2, int ind1, int ind2){
		int[] permu = new int[permu1.length];
		boolean aAjouter=false;
		int indexPermu2=0;

		for(int i=0;i<permu1.length;i++){
			if(i>=ind1 && i<=ind2){
				permu[i]=permu1[i];
			}else{
				aAjouter=false;
				while(!aAjouter){
					aAjouter=true;
					for(int j=ind1;j<=ind2;j++){
						if(permu1[j]==permu2[indexPermu2]){
							indexPermu2++;
							aAjouter=false;
							break;
						}
					}

				}
				permu[i]=permu2[indexPermu2];
				indexPermu2++;
			}
		}

		return permu;
	}

	public double hillClimbing(String file, int init, int voisinage, int mvt){
		double[][] matrix = null;
		int[] permutation = null;
		int[] nextNeighbor = new int[2];
		List<Town> towns = null;

		try {
			towns = parse(file);
		} catch (IOException e) {
			System.out.println("fail parse " + e.getMessage());
		}

		matrix=generateMatrix(towns);

		permutation=(init==0)?
				generateRandomPermutation(towns.size())
				:generateHeuristicPermutation(matrix, 0);

				while(true){
					nextNeighbor=(mvt==0)?
							nextNeighbor(matrix, permutation, voisinage)
							:bestNeighbor(matrix, permutation, voisinage);
							if(nextNeighbor[0]==nextNeighbor[1])break;

							permutation=(voisinage==0)?
									swap(permutation, nextNeighbor[0], nextNeighbor[1])
									:_2_opt(permutation, nextNeighbor[0], nextNeighbor[1]);
				}

				return eval(matrix, permutation);
	}

	/**
	 * random/heuristic   ,  swap/2_opt   ,   next/best
	 * @param matrix
	 * @param permutation
	 * @param init
	 * @param voisinage
	 * @param mvt
	 * @return
	 */
	public int[] hillClimbing(double[][] matrix, int[] permutation, int init, int voisinage, int mvt){
		int[] nextNeighbor = new int[2];

		if(mvt==0 && voisinage==0){
			while(true){
				nextNeighbor=nextNeighbor(matrix, permutation, voisinage);
				if(nextNeighbor[0]==nextNeighbor[1])break;

				permutation=swap(permutation, nextNeighbor[0], nextNeighbor[1]);
			}
		}else if(mvt==0 && voisinage!=0){
			while(true){
				nextNeighbor=nextNeighbor(matrix, permutation, voisinage);
				if(nextNeighbor[0]==nextNeighbor[1])break;

				permutation=_2_opt(permutation, nextNeighbor[0], nextNeighbor[1]);
			}
		}else if(mvt!=0 && voisinage==0){
			while(true){
				nextNeighbor=bestNeighbor(matrix, permutation, voisinage);
				if(nextNeighbor[0]==nextNeighbor[1])break;

				permutation=swap(permutation, nextNeighbor[0], nextNeighbor[1]);
			}
		}else{
			while(true){
				nextNeighbor=bestNeighbor(matrix, permutation, voisinage);
				if(nextNeighbor[0]==nextNeighbor[1])break;

				permutation=_2_opt(permutation, nextNeighbor[0], nextNeighbor[1]);
			}
		}
		return permutation;
	}

	public double evolution(String file){
		Random r = new Random();
		double[][] matrix = null;
		int[][] population = null;
		int[] parent1 = null;
		int[] parent2 = null;
		int[] crossover = null;
		List<Town> towns = null;
		int indexParent1;
		int indexParent2;
		int indexCrossover1;
		int indexCrossover2;
		double eval1;
		double eval2;
		double evalCross;
		double min;

		try {
			towns = parse(file);
		} catch (IOException e) {
			System.out.println("fail parse " + e.getMessage());
		}

		matrix=generateMatrix(towns);

		population=generateRandomPopulation(150, towns.size());

		min = eval(matrix,population[0]);

		for(int i=0;i<10000000;i++){
			System.out.println(i);
			//while(min>24900){
			indexParent1 = r.nextInt(towns.size());
			indexParent2 = r.nextInt(towns.size());
			while(indexParent1==indexParent2){
				indexParent2 = r.nextInt(towns.size());
			}
			parent1 = population[indexParent1];
			parent2 = population[indexParent2];

			indexCrossover1 = r.nextInt(towns.size()-1);
			indexCrossover2 = r.nextInt(towns.size()-indexCrossover1) + indexCrossover1;

			crossover = crossover(parent1, parent2, indexCrossover1, indexCrossover2);

			crossover = randomSwap(crossover);
			//crossover = hillClimbing(matrix, crossover, 1, 0, 0);
			//TODO +hillClimbing?


			eval1=eval(matrix, population[indexParent1]);
			eval2=eval(matrix, population[indexParent2]);
			evalCross=eval(matrix,crossover);
			if(eval1 < eval2 && eval1 > evalCross){
				population[indexParent1]=crossover;
			}else if(eval2 > evalCross){
				population[indexParent2]=crossover;
			}
			if(evalCross<min){
				min=evalCross;
			}
		}
		return min;
	}

	public static void main(String[] args) {
		//		TSP tsp = new TSP();
		//		List<Town> towns = null;
		//		try {
		//			towns = tsp.parse("data/kroA100.tsp");
		//		} catch (IOException e) {
		//			System.out.println("fail main " + e.getMessage());
		//		}
		//		/*for(Town t : towns){
		//			System.out.println(t.getName() +" "+t.getX() + " " + t.getY());
		//		}*/
		//
		//		//System.out.println(myParser.getDistance(towns.get(0), towns.get(2)));
		//
		//		double[][] matrix = tsp.generateMatrix(towns);
		//		for(int i=0; i<matrix.length; i++){
		//			for(int j=0; j<matrix[0].length; j++){
		//				System.out.print(matrix[i][j]+ "  |  ");
		//			}
		//			System.out.println();
		//		}
		//
		//		System.out.println(tsp.eval(matrix, tsp.generateRandomPermutation(100)));
		//		System.out.println("short :" + tsp.eval(matrix, tsp.generateHeuristicPermutation(matrix, 0)));
		//
		//		int[] perm = {0,1,2,3,4,98,99};
		//		int[] newPermu=tsp._2_opt(perm, 6);
		//		for(int i=0;i<newPermu.length;i++){
		//			System.out.println(newPermu[i]);
		//		}

		//		int town=0;
		//		int nextTown=1;
		//		int[] permu = tsp.generateRandomPermutation(100);
		//		int[] nextPermu;
		//		while(true){
		//			nextTown=tsp.bestNextTown(permu, matrix, town);
		//			if(town==nextTown){
		//				break;
		//			}
		//		}
		//		
		TSP tsp = new TSP();
		// random/heuristic   ,  swap/2_opt   ,   next/best
				System.out.println("Heuristic swap next " + tsp.hillClimbing("data/kroA100.tsp", 1, 0, 0));
				System.out.println("Heuristic swap best " + tsp.hillClimbing("data/kroA100.tsp", 1, 0, 1));
				System.out.println("Heuristic 2_opt next " + tsp.hillClimbing("data/kroA100.tsp", 1, 1, 0));
				System.out.println("Heuristic 2_opt best " + tsp.hillClimbing("data/kroA100.tsp", 1, 1, 1));
				
				double dist1=0.0;
				double dist2=0.0;
				double dist3=0.0;
				double dist4=0.0;
				for(int i=0;i<30;i++){
					dist1+=tsp.hillClimbing("data/kroA100.tsp", 0, 0, 0);
					dist2+=tsp.hillClimbing("data/kroA100.tsp", 0, 0, 1);
					dist3+=tsp.hillClimbing("data/kroA100.tsp", 0, 1, 0);
					dist4+=tsp.hillClimbing("data/kroA100.tsp", 0, 1, 1);
				}
				System.out.println("Random swap next " + dist1/30);
				System.out.println("Random swap best " + dist2/30);
				System.out.println("Random 2_opt next " + dist3/30);
				System.out.println("Random 2_opt best " + dist4/30);

//				Heuristic swap next 26750.0
//				Heuristic swap best 26847.0
//				Heuristic 2_opt next 25512.0
//				Heuristic 2_opt best 24404.0
//				Random swap next 40565.86666666667
//				Random swap best 44597.566666666666
//				Random 2_opt next 24153.566666666666
//				Random 2_opt best 24233.0

		//		int[][] randPop=tsp.generateRandomPopulation(150, 100);
		//		int[] p = tsp.crossover(new int[]{1,2,3,4,5}, new int[]{2,3,4,5,1},1,3);
		//		for(int i=0;i<p.length;i++){
		//			System.out.print(p[i]);
		//		}

//		System.out.println(tsp.evolution("data/kroA100.tsp"));
	}

}
