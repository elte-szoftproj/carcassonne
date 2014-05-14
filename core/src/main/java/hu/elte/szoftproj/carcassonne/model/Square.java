package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

/**
 * Egy lerakott palyaelemet reprezentalo osztaly.
 * 
 * @author Zsolt
 *
 */
public interface Square {

	/**
	 * A lerakott elem x koordinataja
	 * @return
	 */
	
    int getX();
    /**
     * A lerakott elem y koordinataja
     * @return
     */
    int getY();

    /**
     * Az elem tipusa
     * @return
     */
    Tile getTile();
    
    /**
     * A reszelemek listaja
     * @return
     */
    Set<Slot> getSlotList();

    /**
     * Visszaadja az adott pozicion talalhato reszelemet.
     * 
     * @param direction Az irany, mar az elforgatasnak megfeleloen kezelve
     * @return
     */
    Slot getSlotAt(Place direction);
    
    /**
     * Visszaadja az elem elforgatasanak merteket
     * @return
     */
	Rotation getTileRotation();
}
