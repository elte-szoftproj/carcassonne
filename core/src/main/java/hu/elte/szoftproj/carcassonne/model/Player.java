package hu.elte.szoftproj.carcassonne.model;

public interface Player {

    // name of the player
    String getName();

    // score of the player
    float  getScore();

    // iternate pieces of the player
    Piece[] getPieceList();

    // get piece for the player, that isn't placed yet
    boolean  hasFreePiece();
    Piece    getFreePiece();
}
