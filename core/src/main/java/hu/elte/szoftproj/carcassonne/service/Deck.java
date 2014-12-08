package hu.elte.szoftproj.carcassonne.service;

import com.google.common.collect.ImmutableSet;
import hu.elte.szoftproj.carcassonne.domain.Board;
import hu.elte.szoftproj.carcassonne.domain.Tile;

public interface Deck {

    String getName();

    Board getStarterBoard();

    Tile peekNext();

    ImmutableSet<Tile> uniqueTiles();

    Deck removeNext();

    int getRemainingPieceCount();

    /**
     * Called when the actual tile can't be placed anywhere
     * @return
     */
    Deck reshuffle();
}
