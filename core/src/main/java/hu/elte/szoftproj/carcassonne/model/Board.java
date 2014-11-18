package hu.elte.szoftproj.carcassonne.model;

/**
 * A jat!k p!ly!ja. A kezd!elem a 0,0 pontban tal!lhat!, onnan b!rmilyen ir!nyba tetsz!leges hosszan terjeszkedhet.
 * 
 *
 */
public interface Board {

    /**
     * A p!lya aktu!lis sz!less!ge
     * @return
     */
    int width();
    
    /**
     * A p!lya aktu!lis magass!ga
     * @return
     */
    int height();

    /**
     * Visszaadja a kezd!elemet
     * @return
     */
    Square getStartSquare();
    /**
     * Visszaadja az adott koordin!t!j! elemet
     * @param x
     * @param y
     * @return
     */
    Square getTileAt(int x, int y);
    
    /**
     * Visszaadja a p!lya bal fels! elem!nek koordin!t!it
     * @return
     */
    PositionInterface getTopLeftPosition();
    
    /**
     * Visszaadja a p!lya jobb als! elem!nek a koordin!t!it
     * @return
     */
    PositionInterface getBottomRightPosition();

    /**
     * Igaz, ha a megadott param!terekkel le lehet tenni egy !j elemet
     * @param x az !j elem x koordin!t!ja
     * @param y az !j elem y koordin!t!ja
     * @param t az !j elem t!pusa
     * @param r az !j elem elforgat!sa
     * @return
     */
    boolean canPlaceTileAt(int x, int y, Tile t, Rotation r);
    
    /**
     * Letesz egy !j elemet, ha lehets!ges. Ha nem, nem t!rt!nik semmi. A param!terek azonosak a canPlaceTileAt elj!r!ssal.
     * 
     * @param x
     * @param y
     * @param t
     * @param r
     * @return
     */
    Square placeTileAt(int x, int y, Tile t, Rotation r);
    
    /**
     * Visszaadja a p!lya !sszes akt!v (nem m!sikkal egyes!tett) ter!let!t
     * @param type
     * @return
     */
    Area[] getAreaList(Object type);
    
	
}
