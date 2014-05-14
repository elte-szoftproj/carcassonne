package hu.elte.szoftproj.carcassonne.model;

import hu.elte.szoftproj.carcassonne.model.impl.BasicTile;

/**
 * A játék paklijának megvalósítása. A pakli rendelkezik egy elemmel, majd utána meghatározott mennyiségû lap egyenkénti kiosztására alkalmas.
 * 
 * A kezdõelem nem szerves része a paklinak, az elemek számába nem számít bele.
 * 
 * @author Zsolt
 *
 */
public interface Deck {

    /**
     * Kivesz egy lapot a pakliból
     * @return
     */
    Tile drawTile();

    /**
     * Lekérdezi a kezdõelemet
     * @return
     */
	BasicTile getStarterTile();

	/**
	 * Megmondja, hány elem van még hátra.
	 * @return
	 */
	int getRemainingPieceCount();

    // TODO: statistics

}
