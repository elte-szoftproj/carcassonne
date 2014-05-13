package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

public interface Square {

    int getX();
    int getY();

    Tile getTile();
    
    Set<Slot> getSlotList();

    /**
     * Gets the slot for the given direction.
     * 
     * @param direction
     * @return
     */
    Slot getSlotAt(Place direction);
	Rotation getTileRotation();
}
