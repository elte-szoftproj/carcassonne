package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

/**
 * Egy lerakott palyaelem reszegysegenek a megfeleltetese, amihez teruletet illetve babut lehet rendelni.
 * 
 * @author Zsolt
 *
 */
public interface Slot {

	/**
	 * Visszaadja a hozza tartozo lerakott palyaelemet
	 * @return
	 */
    Square getSquare();

    /**
     * A terulet, aminek a reszegyseg resze
     * @return
     */
    Area   getArea();
    
    /**
     * Visszaadja a poziciok listajat, mar az elforgatasnak megfeleloen
     * @return
     */
    Set<Place> getPlaces();
    
    /**
     * Visszaadja az eredeti, a forgatastol fuggetlen, a palyaelem definicioban szereplo poziciolistat
     * @return
     */
    Set<Place> getUnrotatedPlaces();
    
    /**
     * Lerak egy babut az elemre
     * @param piece
     */
    void  setPiece(Piece piece);
    
    /**
     * Lekerdezi a lerakott babut
     * @return
     */
    Piece getPiece();
    
    /**
     * Visszaadja a definialo palyaelem-reszegyseg osztalyt
     * @return
     */
    Side getSide();
}
