package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

/**
 * Egy jol hatarozott, azonos tipusu teruletet reprezental a terkepen, mint pl. egy mezo, vagy egy varos.
 */
public interface Area {
	
    /**
     * Visszaadja a terulet tipusat
     * @return
     */
    AreaType getType();

    /**
     * Igaz, ha a terulet mar teljesen korulhatarolt, annak kesobbi bovitesere nincs lehetoseg.
     * @return
     */
    boolean isComplete();

    /**
     * Visszaadja a teruleten talalhato babuk listajat
     * @return
     */
    Piece[] getPieceList();

    /**
     * Visszaadja a szomszedos (kozos hatarvonallal rendelkezo) teruletek listajat
     * @return
     */
    Set<Area> getNeighbours();
    
    /**
     * Visszaadja a terulet elemeit tartalmazó pályaelemek listáját
     * @return
     */
    Set<Square> getSquares();
    
    /**
     * Visszaadja a terület részelemeit
     * @return
     */
    Set<Slot> getSlots();
    
    /**
     * Hozzáad egy új szomszédot a területhez
     * @param a
     */
    void addNeighbour(Area a);

    /**
     * Egyesíti a két területet.
     */
	void mergeTo(Area area);

	/**
	 * Hozzáad egy új elemet a területhez.
	 * @param s
	 * @param direction
	 */
	void addPart(Square s, Place direction);
}
