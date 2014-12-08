package hu.elte.szoftproj.carcassonne.service.impl.deck;

import com.google.common.collect.ImmutableSet;
import hu.elte.szoftproj.carcassonne.domain.Board;
import hu.elte.szoftproj.carcassonne.domain.Tile;
import hu.elte.szoftproj.carcassonne.service.Deck;

public class BasicDeck implements Deck {

    @Override
    public String getName() {
        return "basic";
    }

    @Override
    public Board getStarterBoard() {
        return null;
    }

    @Override
    public Tile peekNext() {
        return null;
    }

    @Override
    public ImmutableSet<Tile> uniqueTiles() {
        return null;
    }

    @Override
    public Deck removeNext() {
        return null;
    }

    @Override
    public int removeRemainingPieceCount() {
        return 0;
    }

    @Override
    public Deck reshuffle() {
        return null;
    }
}
