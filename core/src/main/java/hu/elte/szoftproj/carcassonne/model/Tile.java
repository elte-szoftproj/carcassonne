package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

// type of the tiles that
public interface Tile {

	/**
	 *Visszaadja a palyaelem kulonbozo reszegysegeinek listajat
	 * 
	 * @return
	 */
    Set<Side> getSideList();
    /**
     * Visszaadja a pozicionak megfelelo reszegyseget
     * @param direction
     * @return
     */
    Side   getSide(Place direction);

    /**
     * Igaz, ha az elemre le lehet tenni adott tipusu babut.
     * @param pieceType
     * @return true if the given type can be placed on this tile
     */
    boolean canPlacePieceType(AreaType pieceType);
    
    /**
     * Visszaadja a palyaelemtipus nevet.
     * @return
     */
    String getName();
}
