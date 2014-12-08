package hu.elte.szoftproj.carcassonne.domain;

public class Square {

    Tile tile;

    Rotation tileRotation;


    // area

    // immutablelist<pieceplacement>


    public Square(Tile tile, Rotation tileRotation) {
        this.tile = tile;
        this.tileRotation = tileRotation;
    }

    public Tile getTile() {
        return tile;
    }

    public Rotation getTileRotation() {
        return tileRotation;
    }
}
