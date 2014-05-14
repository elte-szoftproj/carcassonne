package hu.elte.szoftproj.carcassonne.model;

/**
 * A játék pályája. A kezdõelem a 0,0 pontban található, onnan bármilyen irányba tetszõleges hosszan terjeszkedhet.
 * 
 *
 */
public interface Board {

    /**
     * A pálya aktuális szélessége
     * @return
     */
    int width();
    
    /**
     * A pálya aktuális magassága
     * @return
     */
    int height();

    /**
     * Visszaadja a kezdõelemet
     * @return
     */
    Square getStartSquare();
    /**
     * Visszaadja az adott koordinátájú elemet
     * @param x
     * @param y
     * @return
     */
    Square getTileAt(int x, int y);
    
    /**
     * Visszaadja a pálya bal felsõ elemének koordinátáit
     * @return
     */
    PositionInterface getTopLeftPosition();
    
    /**
     * Visszaadja a pálya jobb alsó elemének a koordinátáit
     * @return
     */
    PositionInterface getBottomRightPosition();

    /**
     * Igaz, ha a megadott paraméterekkel le lehet tenni egy új elemet
     * @param x az új elem x koordinátája
     * @param y az új elem y koordinátája
     * @param t az új elem típusa
     * @param r az új elem elforgatása
     * @return
     */
    boolean canPlaceTileAt(int x, int y, Tile t, Rotation r);
    
    /**
     * Letesz egy új elemet, ha lehetséges. Ha nem, nem történik semmi. A paraméterek azonosak a canPlaceTileAt eljárással.
     * 
     * @param x
     * @param y
     * @param t
     * @param r
     * @return
     */
    Square placeTileAt(int x, int y, Tile t, Rotation r);
    
    /**
     * Visszaadja a pálya összes aktív (nem másikkal egyesített) területét
     * @param type
     * @return
     */
    Area[] getAreaList(Object type);
    
	
}
