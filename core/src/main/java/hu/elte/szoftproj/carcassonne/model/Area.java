package hu.elte.szoftproj.carcassonne.model;

/**
 * Represents a closed area on the map
 */
public interface Area {

    // type of area (quasi enum)
    public static final String FIELD     = "Field";
    public static final String ROAD      = "Road";
    public static final String CITY      = "City";
    public static final String CHATEDRAL = "Chatedral";

    // area type
    String  getType();

    // returns true if the area is completed
    boolean isComplete();

    // player pieces on this area
    Piece[] getPieceList();

    // neighbouring areas
    Area[] getNeighbourList();
    Square getSquareList();
}
