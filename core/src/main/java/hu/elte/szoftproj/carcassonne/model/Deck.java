package hu.elte.szoftproj.carcassonne.model;

import hu.elte.szoftproj.carcassonne.model.impl.BasicTile;

/**
 * A j!t!k paklij!nak megval!s!t!sa. A pakli rendelkezik egy elemmel, majd ut!na meghat!rozott mennyis!g! lap egyenk!nti kioszt!s!ra alkalmas.
 * 
 * A kezd!elem nem szerves r!sze a paklinak, az elemek sz!m!ba nem sz!m!t bele.
 * 
 * @author Zsolt
 *
 */
public interface Deck {

    /**
     * Kivesz egy lapot a paklib!l
     * @return
     */
    Tile drawTile();

    /**
     * Lek!rdezi a kezd!elemet
     * @return
     */
	BasicTile getStarterTile();

	/**
	 * Megmondja, h!ny elem van m!g h!tra.
	 * @return
	 */
	int getRemainingPieceCount();

    // TODO: statistics

}
