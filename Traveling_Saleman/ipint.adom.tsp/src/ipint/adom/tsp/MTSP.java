package ipint.adom.tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MTSP {

	private BufferedReader reader;
	private List<Town> towns1 = null;
	private List<Town> towns2 = null;
	private double[][] matrix1 = null;
	private double[][] matrix2 = null;
	
	public MTSP(String file1, String file2){
		try {
			towns1 = parse(file1);
			towns2 = parse(file2);
		} catch (IOException e) {
			System.out.println("fail parse " + e.getMessage());
		}
		matrix1 = generateMatrix(towns1);
		matrix2 = generateMatrix(towns2);
	}
	
	public List<Town> parse(String file) throws IOException{
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Erreur lecture fichier : " + e.getMessage());
		}
		String line;
		List<Town> towns = new ArrayList<>();

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
	
	private double getDistance(Town t1, Town t2){
		return Math.sqrt(
				((t2.getX()-t1.getX()) * (t2.getX()-t1.getX()))
				+ ((t2.getY()-t1.getY()) * (t2.getY()-t1.getY()))
				);
	}	
	
	public double[] eval(int[] permutation){
		double dist = 0.0;
		double[] solution = new double[2];
		
		for(int i=1;i<permutation.length;i++){
			dist+=matrix1[permutation[i-1]][permutation[i]];
		}
		dist+=matrix1[0][matrix1.length-1];
		solution[0]=dist;
		
		dist = 0.0;
		for(int i=1;i<permutation.length;i++){
			dist+=matrix2[permutation[i-1]][permutation[i]];
		}
		dist+=matrix2[0][matrix2.length-1];
		solution[1]=dist;
		
		return solution;
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
	
	public List<int[]> off_line(int[][] permutations){
		int nbComp=0;
		boolean ajout;
		double[][] solutions = new double[permutations.length][2];
		List<int[]> sousEnsemble = new ArrayList<>();
		
		for(int i=0;i<permutations.length;i++){
			solutions[i]=eval(permutations[i]);
		}
		
		for(int i=0;i<solutions.length;i++){
			ajout=true;
			for(int j=0;j<solutions.length;j++){
				nbComp+=2;
				if(solutions[j][0]<solutions[i][0] && solutions[j][1]<solutions[i][1]){
					ajout=false;break;
				}
			}
			if(ajout){
				sousEnsemble.add(permutations[i]);
			}
		}
		System.out.println("Comparaisons off-line :"+nbComp);
		return sousEnsemble;
	}
	
	public List<int[]> on_line(int[][] permutations){
		int nbComp=0;
		boolean ajout;
		double[][] solutions = new double[permutations.length][2];
		List<int[]> sousEnsemble = new ArrayList<>();
		
		for(int i=0;i<permutations.length;i++){
			solutions[i]=eval(permutations[i]);
		}
		
		for(int i=0;i<solutions.length;i++){
			double[] solutionToAdd = solutions[i];
			ajout=true;
			for(int j=0;j<sousEnsemble.size();j++){
				double[] solutionToCheck = eval(sousEnsemble.get(j));
				nbComp+=4;
				if(solutionToCheck[0]>solutionToAdd[0] && solutionToCheck[1]>solutionToAdd[1]){
					sousEnsemble.remove(j);
					j--;
				}
				if(solutionToCheck[0]<solutionToAdd[0] && solutionToCheck[1]<solutionToAdd[1]){
					ajout=false;
				}
			}
			if(ajout){
				sousEnsemble.add(permutations[i]);
			}
		}
		System.out.println("Comparaisons on-line :"+nbComp);
		return sousEnsemble;
	}
	
	
//	public double[][] generateWeights(int qty){
//		double[][] weights = new double[qty][2];
//		
//		for(int i=0;i<qty;i++){
//			weights[i][0]=(double)1/(i+1);
//			weights[qty-i-1][1]=(double)1/(i+1);
//		}
//		
//		return weights;
//	}
	
	public double[][] generateWeights(){
		double[][] weights = new double[101][2];
		
		for(int i=0;i<101;i++){
			weights[i][0]=(double)0+(i*0.01);
			weights[i][1]=1-weights[i][0];
		}
		
		return weights;
	}
	
	public int[][] scalarApproach(){
		
	}
	
	
	
	public static void main(String[] args) {
		MTSP mTSP = new MTSP("data/kroA100.tsp", "data/kroB100.tsp");
//		int[][] randomPermutations = new int[500][100];
//		for(int i=0;i<randomPermutations.length;i++){
//			randomPermutations[i] = mTSP.generateRandomPermutation(randomPermutations[0].length);
//		}
//		
//		for(int[] permutation : randomPermutations){
//			System.out.println(mTSP.eval(permutation)[0]+" "+mTSP.eval(permutation)[1]);
//		}
//		
//		System.out.println();
//		
//		List<int[]> nonDominatesOffLine = mTSP.off_line(randomPermutations);
//		for(int[] permutation : nonDominatesOffLine){
//			System.out.println(mTSP.eval(permutation)[0]+ " "+mTSP.eval(permutation)[1]);
//		}
//		
//		System.out.println();
//		
//		List<int[]> nonDominatesOnLine = mTSP.on_line(randomPermutations);
//		for(int[] permutation : nonDominatesOnLine){
//			System.out.println(mTSP.eval(permutation)[0]+ " "+mTSP.eval(permutation)[1]);
//		}
		
		double[][] weights = mTSP.generateWeights();
		for(int i=0;i<weights.length;i++){
			System.out.println(weights[i][0]+" "+weights[i][1]);
		}
	}

}
