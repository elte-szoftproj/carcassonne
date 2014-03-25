package hu.elte.szoftproj.carcassonne.model;

public interface Board {

    // current size of the board (changes whith the game)
    int width();
    int height();

    // iterating tiles
    Square getStartTile();
    Square getTileAt(int x, int y);

    // iterating areas
    Area[] getAreaList(Object type);
}
