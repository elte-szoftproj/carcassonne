package hu.elte.szoftproj.carcassonne.model;

/**
 * Egy b!b!t le!r! oszt!ly.
 * @author Zsolt
 *
 */
public interface Piece {

    // returns the player for the piece
    Player getPlayer();

    // if the piece is placed, this returns the slot
    boolean isPlaced();
    Slot    getSlot();

}
