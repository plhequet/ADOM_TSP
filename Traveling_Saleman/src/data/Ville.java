package data;

public class Ville {

	private int id;
	private double xCoordonnee;
	private double yCoordonnee;
	/**
	 * @param id
	 * @param xCoordonnee
	 * @param yCoordonnee
	 */
	public Ville(int id, double xCoordonnee, double yCoordonnee) {
		super();
		this.id = id;
		this.xCoordonnee = xCoordonnee;
		this.yCoordonnee = yCoordonnee;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the xCoordonnee
	 */
	public double getxCoordonnee() {
		return xCoordonnee;
	}
	/**
	 * @param xCoordonnee the xCoordonnee to set
	 */
	public void setxCoordonnee(double xCoordonnee) {
		this.xCoordonnee = xCoordonnee;
	}
	/**
	 * @return the yCoordonnee
	 */
	public double getyCoordonnee() {
		return yCoordonnee;
	}
	/**
	 * @param yCoordonnee the yCoordonnee to set
	 */
	public void setyCoordonnee(double yCoordonnee) {
		this.yCoordonnee = yCoordonnee;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(xCoordonnee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yCoordonnee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ville other = (Ville) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(xCoordonnee) != Double.doubleToLongBits(other.xCoordonnee))
			return false;
		if (Double.doubleToLongBits(yCoordonnee) != Double.doubleToLongBits(other.yCoordonnee))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ville [id = " + id + ", xCoordonnee = " + xCoordonnee + ", yCoordonnee = " + yCoordonnee + "]";
	}
	
	
	
	
}
