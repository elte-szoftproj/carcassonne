package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

public class Square {

    Tile tile;

    Rotation tileRotation;

    ImmutableTable<Integer, Integer, Follower> placedFollowers;

    public Square(Tile tile, Rotation tileRotation) {
        this.tile = tile;
        this.tileRotation = tileRotation;
        this.placedFollowers = ImmutableTable.of();
    }

    Square(Tile tile, Rotation tileRotation, ImmutableTable<Integer, Integer, Follower> placedFollowers) {
        this.tile = tile;
        this.tileRotation = tileRotation;
        this.placedFollowers = placedFollowers;
    }

    public Tile getTile() {
        return tile;
    }

    public Rotation getTileRotation() {
        return tileRotation;
    }

    public boolean canPlaceFollower(Follower f, Coordinate at) {
        if (at.getX() < 0 || at.getX() > 4) {
            return false;
        }
        if (at.getY() < 0 || at.getY() > 4) {
            return false;
        }
        if (placedFollowers.contains(at.getY(), at.getX())) {
            return false;
        }

        return true;
    }

    public Square addFollower(Follower f, Coordinate at) {

        if (at.getX() < 0 || at.getX() > 4) {
            throw new IllegalArgumentException("Illegal X coordinate");
        }
        if (at.getY() < 0 || at.getY() > 4) {
            throw new IllegalArgumentException("Illegal Y coordinate");
        }
        if (placedFollowers.contains(at.getY(), at.getX())) {
            throw new IllegalArgumentException("Coordinate already in use");
        }

        ImmutableTable.Builder<Integer, Integer, Follower> builder = new ImmutableTable.Builder<>();
        builder.putAll(placedFollowers);
        builder.put(at.getY(), at.getX(), f);

        return new Square(tile, tileRotation, builder.build());
    }

    public Square removeFollower(Follower f) {
        if (!placedFollowers.containsValue(f)) {
            throw new IllegalArgumentException("Follower not found");
        }
        ImmutableTable.Builder<Integer, Integer, Follower> builder = new ImmutableTable.Builder<>();
        for(Table.Cell<Integer, Integer, Follower> ff: placedFollowers.cellSet()) {
            if (!f.equals(ff.getValue())) {
                builder.put(ff.getRowKey(), ff.getColumnKey(), ff.getValue());
            }
        }

        return new Square(tile, tileRotation, builder.build());
    }

    public ImmutableTable<Integer, Integer, Follower> getPlacedFollowers() {
        return placedFollowers;
    }
}
