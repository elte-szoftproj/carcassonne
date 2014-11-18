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
     * Visszaadja a terulet elemeit tartalmazo palyaelemek listajat
     * @return
     */
    Set<Square> getSquares();
    
    /**
     * Visszaadja a terulet reszelemeit
     * @return
     */
    Set<Slot> getSlots();
    
    /**
     * Hozzaad egy uj szomszedot a terulethez
     * @param a
     */
    void addNeighbour(Area a);

    /**
     * Egyeseti a ket teruletet.
     */
	void mergeTo(Area area);

	/**
	 * Hozzaad egy uj elemet a terulethez.
	 * @param s
	 * @param direction
	 */
	void addPart(Square s, Place direction);
}
