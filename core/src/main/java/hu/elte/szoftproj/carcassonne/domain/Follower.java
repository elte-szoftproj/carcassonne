package hu.elte.szoftproj.carcassonne.domain;

public interface Follower {
    Player getOwner();

    int getValue();

    boolean canBePlacedAt(Area area);
}
