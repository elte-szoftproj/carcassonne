package hu.elte.szoftproj.carcassonne.model;

public interface RuleBook {

    // calculates rules for placing tiles
    boolean canPlaceTile(Tile tile, Square square);
    boolean canPlacePiece(Piece piece, Square square, Slot slot);

    // turn with special semantics
    boolean isSpecialTurn();

    // areas that return pieces after completed (anything except farms)
    boolean returnPiecesWhenAreaComplete(Object type);

    // calculates score
    float   getScoreForPiece(Piece piece);
    int     getScoreForPlayer(Player player);
}
