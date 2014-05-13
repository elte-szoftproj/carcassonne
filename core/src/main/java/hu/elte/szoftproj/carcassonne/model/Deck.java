package hu.elte.szoftproj.carcassonne.model;

import hu.elte.szoftproj.carcassonne.model.impl.BasicTile;

public interface Deck {

    // draws a tile from the deck, returns null when no tile left
    Tile drawTile();

	BasicTile getStarterTile();

    // TODO: statistics

}
