package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableSet;

public class Area {

    private Character type;

    int openEdgeCount;

    private ImmutableSet<Coordinate> coordinates;

    private ImmutableSet<Follower> followers;

    public Area(Character type) {
        this.type = type;
        followers = ImmutableSet.of();
    }

    public Character getType() {
        return type;
    }

    void setCoordinates(ImmutableSet<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    void setOpenEdgeCount(int openEdgeCount) {
        this.openEdgeCount = openEdgeCount;
    }

    void setFollowers(ImmutableSet<Follower> followers) {
        this.followers = followers;
    }

    public ImmutableSet<Coordinate> getCoordinates() {
        return coordinates;
    }

    public boolean isClosed() {
        return openEdgeCount == 0;
    }

    public int getOpenEdgeCount() {
        return openEdgeCount;
    }

    public ImmutableSet<Follower> getFollowers() {
        return followers;
    }
}
