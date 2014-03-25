package hu.elte.szoftproj.carcassonne.model;

public interface Square {

    Square[] getNeighbourList();
    Square   getNeighbour(Object direction);

    int x();
    int y();

    Tile getTile();
    void placeTile(Tile tile);

    Slot[] getSlotList();
    Slot   getSlotForSide(Facing direction);
    Slot   getSlotForMiddle();

}
