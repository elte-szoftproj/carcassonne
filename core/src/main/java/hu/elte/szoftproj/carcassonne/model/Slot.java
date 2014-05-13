package hu.elte.szoftproj.carcassonne.model;

import java.util.Set;

public interface Slot {

    public static final String FARMER = "F";
    public static final String KNIGHT = "K";
    public static final String THIEF  = "T";
    public static final String MONK   = "M";

    Square getSquare();

    Area   getArea();
    
    /**
     * Returns the ROTATED place list
     * @return
     */
    Set<Place> getPlaces();
    
    Set<Place> getUnrotatedPlaces();
    
    void  setPiece(Piece piece);
    Piece getPiece();
    
    Side getSide();
}
