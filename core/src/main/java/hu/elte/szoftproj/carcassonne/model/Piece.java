package hu.elte.szoftproj.carcassonne.model;

public interface Piece {

    // returns the player for the piece
    Player getPlayer();

    // if the piece is placed, this returns the slot
    boolean isPlaced();
    Slot    getSlot();

}
