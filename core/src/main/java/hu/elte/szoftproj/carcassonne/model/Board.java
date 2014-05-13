package hu.elte.szoftproj.carcassonne.model;

/**
 * The game board. Central (initial) position is (0,0). From there, coordinates can expand to all directions.
 * 
 *
 */
public interface Board {

    // current size of the board (changes whith the game)
    int width();
    int height();

    // iterating tiles
    Square getStartSquare();
    Square getTileAt(int x, int y);

    boolean canPlaceTileAt(int x, int y, Tile t, Rotation r);
    
    Square placetileAt(int x, int y, Tile t, Rotation r);
    
    // iterating areas
    Area[] getAreaList(Object type);
    
	
}
