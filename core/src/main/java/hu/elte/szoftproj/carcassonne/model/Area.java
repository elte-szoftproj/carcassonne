package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

/**
 * Represents a closed area on the map
 */
public interface Area {
	
    // area type
    AreaType getType();

    // returns true if the area is completed
    boolean isComplete();

    // player pieces on this area
    Piece[] getPieceList();

    // neighbouring areas
    Set<Area> getNeighbours();
    Set<Square> getSquares();
    Set<Slot> getSlots();
    
    void addNeighbour(Area a);

    /**
     * Merges this area into the other one
     * @param area
     */
	void mergeTo(Area area);

	void addPart(Square s, Place direction);
}
