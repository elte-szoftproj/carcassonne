package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableSet;

public class Area {

    private Character type;

    private ImmutableSet<Coordinate> coordinates;

    public Area(Character type) {
        this.type = type;
    }

    public Character getType() {
        return type;
    }

    void setCoordinates(ImmutableSet<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public ImmutableSet<Coordinate> getCoordinates() {
        return coordinates;
    }
}
