package data;

import java.util.HashMap;

public class VilleFactory {

	private static VilleFactory INSTANCE = new VilleFactory();
	private HashMap<Integer,Ville> listeVilles;
	
	/**
	 * 
	 */
	private VilleFactory() {
		super();
		this.listeVilles = new HashMap<Integer,Ville>();
	}

	/**
	 * @return the listeVilles
	 */
	public HashMap<Integer,Ville> getListeVilles() {
		return listeVilles;
	}

	/**
	 * @param listeVilles the listeVilles to set
	 */
	public void setListeVilles(HashMap<Integer,Ville> listeVilles) {
		this.listeVilles = listeVilles;
	}

	/**
	 * @return the iNSTANCE
	 */
	public static VilleFactory getINSTANCE() {
		return INSTANCE;
	}
	
	
}
