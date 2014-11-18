package hu.elte.szoftproj.carcassonne.model;

/**
 * Egy ter!0lett!pust le!r! oszt!ly
 * @author Zsolt
 *
 */
public interface AreaType {

	/**
	 * A ter!lett!pus neve
	 * @return
	 */
	public String getName();
	
	/**
	 * Meghat!rozza a param!terk!nt !tadott, ilyen t!pus! ter!let pontsz!m!t
	 * @param a
	 * @return
	 */
	public int getScore(Area a);
	
}
