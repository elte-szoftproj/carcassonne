package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

/**
 * Egy palyaelem azonos tipusu osszefuggo reszegyseget leiro interfesz. Pl. egy darab ut, vagy mezo.
 * 
 * @todo Az elnevezes mar reg nem relevans, at kene nevezni
 * @author Zsolt
 *
 */
public interface Side {

	/**
	 * Visszaadja a reszegyseg tipusat
	 * @return
	 */
	AreaType getType();
	
	/**
	 * Igaz, ha a ket reszegyseg kompatibilis (Azaz: segit eldonteni, hogy ket elem egymas melle teheto e)
	 * @param s
	 * @return
	 */
	public boolean compatibleWith(Side s);
	
	/**
	 * Megadja a reszegyseg altal magaba foglalt poziciok listajat
	 * @return
	 */
	Set<Place> getPlaces();
}
