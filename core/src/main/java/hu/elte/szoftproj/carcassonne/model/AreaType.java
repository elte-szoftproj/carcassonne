package hu.elte.szoftproj.carcassonne.model;

/**
 * Egy területtípust leíró osztály
 * @author Zsolt
 *
 */
public interface AreaType {

	/**
	 * A területtípus neve
	 * @return
	 */
	public String getName();
	
	/**
	 * Meghatározza a paraméterként átadott, ilyen típusú terület pontszámát
	 * @param a
	 * @return
	 */
	public int getScore(Area a);
	
}
