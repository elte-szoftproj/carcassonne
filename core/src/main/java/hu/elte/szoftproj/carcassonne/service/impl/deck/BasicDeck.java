package hu.elte.szoftproj.carcassonne.service.impl.deck;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import hu.elte.szoftproj.carcassonne.domain.Board;
import hu.elte.szoftproj.carcassonne.domain.Rotation;
import hu.elte.szoftproj.carcassonne.domain.StandardTiles;
import hu.elte.szoftproj.carcassonne.domain.Tile;
import hu.elte.szoftproj.carcassonne.service.Deck;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class BasicDeck implements Deck {

    private ImmutableList<Tile> tiles;

    public BasicDeck() {
        LinkedList<Tile> ll = new LinkedList<>();

        addTile(ll, 1, StandardTiles.stdCity4);
        addTile(ll, 4, StandardTiles.stdCity3);
        addTile(ll, 3, StandardTiles.stdCity2we);
        addTile(ll, 5, StandardTiles.stdCity2nw);
        addTile(ll, 2, StandardTiles.stdCity11ne);
        addTile(ll, 3, StandardTiles.stdCity11we);
        addTile(ll, 3, StandardTiles.stdCity1);

        addTile(ll, 3, StandardTiles.stdCity3r);
        addTile(ll, 5, StandardTiles.stdCity2nwr);
        addTile(ll, 3, StandardTiles.stdCity1rse);
        addTile(ll, 3, StandardTiles.stdCity1rsw);
        addTile(ll, 3, StandardTiles.stdCity1rswe);
        addTile(ll, 3, StandardTiles.stdCity1rwe); // + starter

        addTile(ll, 1, StandardTiles.stdRoad4);
        addTile(ll, 4, StandardTiles.stdRoad3);
        addTile(ll, 8, StandardTiles.stdRoad2ns);
        addTile(ll, 9, StandardTiles.stdRoad2sw);

        addTile(ll, 4, StandardTiles.stdCloister1);
        addTile(ll, 2, StandardTiles.stdCloister1r);

        Collections.shuffle(ll);

        tiles = ImmutableList.copyOf(ll);
    }

    public void addTile(LinkedList<Tile> ll, int num, Tile t) {
        for (int i=0;i<num;i++) {
            ll.add(t);
        }
    }

    public BasicDeck(ImmutableList<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public String getName() {
        return "basic";
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
        LinkedList<Tile> ll = new LinkedList<>(tiles);

        Collections.shuffle(ll);

        return new BasicDeck(ImmutableList.copyOf(ll));
    }
}
