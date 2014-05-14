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
     * Visszaadja a terulet elemeit tartalmaz� p�lyaelemek list�j�t
     * @return
     */
    Set<Square> getSquares();
    
    /**
     * Visszaadja a ter�let r�szelemeit
     * @return
     */
    Set<Slot> getSlots();
    
    /**
     * Hozz�ad egy �j szomsz�dot a ter�lethez
     * @param a
     */
    void addNeighbour(Area a);

    /**
     * Egyes�ti a k�t ter�letet.
     */
	void mergeTo(Area area);

	/**
	 * Hozz�ad egy �j elemet a ter�lethez.
	 * @param s
	 * @param direction
	 */
	void addPart(Square s, Place direction);
}
