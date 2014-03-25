package hu.elte.szoftproj.carcassonne.model;

public interface Slot {

    public static final String FARMER = "F";
    public static final String KNIGHT = "K";
    public static final String THIEF  = "T";
    public static final String MONK   = "M";

    Tile getTile();

    Area getArea();

}
