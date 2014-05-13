package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

// type of the tiles that
public interface Tile {

	/**
	 * Get the list of the DIFFERENT regions on the Tile.
	 * 
	 * The size of this list will be at least 1, at most 9.
	 * @return
	 */
    Set<Side> getSideList();
    /**
     * Get the Side object for the given direction.
     * @param direction
     * @return
     */
    Side   getSide(Place direction);

    /**
     * 
     * @param pieceType
     * @return true if the given type can be placed on this tile
     */
    boolean canPlacePieceType(AreaType pieceType);
}
