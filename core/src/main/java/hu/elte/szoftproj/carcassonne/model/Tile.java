package hu.elte.szoftproj.carcassonne.model;

// type of the tiles that
public interface Tile {

    Side[] getSideList();
    Side   getSide(Facing direction);

}
