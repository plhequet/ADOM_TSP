package data;

import java.util.HashMap;

public class VilleFactoryMulti {
	
	private HashMap<Integer,Ville> listeVilles;

	/**
	 * 
	 */
	public VilleFactoryMulti() {
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


}
