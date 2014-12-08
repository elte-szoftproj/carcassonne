package hu.elte.szoftproj.carcassonne.service.impl.deck;

import com.google.common.collect.ImmutableSet;
import hu.elte.szoftproj.carcassonne.domain.Board;
import hu.elte.szoftproj.carcassonne.domain.Tile;
import hu.elte.szoftproj.carcassonne.service.Deck;

/**
 * Used in network games
 */
public class DummyDeck implements Deck {

    String deckType;
    int remainingCount;
    Tile currentTile;

    public DummyDeck(String deckType, int remainingCount, Tile currentTile) {
        this.deckType = deckType;
        this.remainingCount = remainingCount;
        this.currentTile = currentTile;
    }

    @Override
    public String getName() {
        return deckType;
    }

    @Override
    public Board getStarterBoard() {
        throw new IllegalArgumentException("Don't call this!");
    }

    @Override
    public Tile peekNext() {
        return currentTile;
    }

    @Override
    public ImmutableSet<Tile> uniqueTiles() { // TODO: implement this with a fallback!
        throw new IllegalArgumentException("Don't call this!");
    }

    @Override
    public Deck removeNext() {
        throw new IllegalArgumentException("Don't call this!");
    }

    @Override
    public int getRemainingPieceCount() {
        return remainingCount;
    }

    @Override
    public Deck reshuffle() {
        throw new IllegalArgumentException("Don't call this!");
    }
}
