package hu.elte.szoftproj.carcassonne.domain;

public interface Follower {
    Player getOwner();

    int getValue();

    String getType();

    boolean canBePlacedAt(Area area);
}
