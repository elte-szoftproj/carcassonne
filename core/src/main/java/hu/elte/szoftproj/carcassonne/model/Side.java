package hu.elte.szoftproj.carcassonne.model;

public interface Side {

	/**
	 * Returns the slot type of the side
	 * @return
	 */
	String getSlotType();
	
	/**
	 * True if the two sides can be neighboars
	 * @param s
	 * @return
	 */
	public boolean compatibleWith(Side s);
}
