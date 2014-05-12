package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

// type of the tiles that
public interface Tile {

    Set<Side> getSideList();
    Side   getSide(Place direction);

    boolean canPlacePieceType(String pieceType);
}
