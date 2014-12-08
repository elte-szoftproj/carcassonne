package hu.elte.szoftproj.carcassonne.service.impl.deck;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import hu.elte.szoftproj.carcassonne.domain.Board;
import hu.elte.szoftproj.carcassonne.domain.Rotation;
import hu.elte.szoftproj.carcassonne.domain.StandardTiles;
import hu.elte.szoftproj.carcassonne.domain.Tile;
import hu.elte.szoftproj.carcassonne.service.Deck;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Simple deck, just enough to finish a simple test game
 */
public class TestDeck implements Deck {

    private ImmutableList<Tile> tiles;

    public TestDeck() {
        LinkedList<Tile> ll = new LinkedList<>();

        addTile(ll, 1, StandardTiles.stdCity3);
        addTile(ll, 2, StandardTiles.stdCity2nw);
        addTile(ll, 2, StandardTiles.stdCity1);

        addTile(ll, 2, StandardTiles.stdRoad2sw);
        addTile(ll, 2, StandardTiles.stdRoad3);
        addTile(ll, 1, StandardTiles.stdRoad2ns);

        tiles = ImmutableList.copyOf(ll);
    }

    public void addTile(LinkedList<Tile> ll, int num, Tile t) {
        for (int i = 0; i < num; i++) {
            ll.add(t);
        }
    }

    public TestDeck(ImmutableList<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public Board getStarterBoard() {
        return new Board(StandardTiles.stdCity1rwe, Rotation.R0);
    }

    @Override
    public Tile peekNext() {
        return tiles.get(0);
    }

    @Override
    public ImmutableSet<Tile> uniqueTiles() {
        HashSet<Tile> tileset = new HashSet<>(tiles);

        return ImmutableSet.copyOf(tileset);
    }

    @Override
    public Deck removeNext() {
        LinkedList<Tile> ll = new LinkedList<>(tiles);
        ll.removeFirst();
        return new BasicDeck(ImmutableList.copyOf(ll));
    }

    @Override
    public int getRemainingPieceCount() {
        return tiles.size();
    }

    @Override
    public Deck reshuffle() {
        return this;
    }
}
