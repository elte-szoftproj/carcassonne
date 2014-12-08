package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;

public class Board {
    private ImmutableTable<Integer, Integer, Square> grid;

    public Board(Tile tile, Rotation tileRotation) {
        grid = (new ImmutableTable.Builder<Integer, Integer, Square>()).put(0, 0, new Square(tile, tileRotation)).build();
    }

    Board(ImmutableTable<Integer, Integer, Square> grid) {
        this.grid = grid;
    }

    public boolean canPlaceAt(Tile t, Rotation r, int y, int x) {
        if (grid.contains(y, x)) {
            return false;
        }
        int neighborCount = 0;

        if (grid.contains(y-1, x)) {
            neighborCount++;
            Square sq = grid.get(y-1, x);
            if (!t.canBePlacedAt(r, sq.getTile(), sq.getTileRotation(), Placement.TOP)) {
                return false;
            }
        }
        if (grid.contains(y+1, x)) {
            neighborCount++;
            Square sq = grid.get(y+1, x);
            if (!t.canBePlacedAt(r, sq.getTile(), sq.getTileRotation(), Placement.BOTTOM)) {
                return false;
            }
        }
        if (grid.contains(y, x-1)) {
            neighborCount++;
            Square sq = grid.get(y, x-1);
            if (!t.canBePlacedAt(r, sq.getTile(), sq.getTileRotation(), Placement.LEFT)) {
                return false;
            }
        }
        if (grid.contains(y, x+1)) {
            neighborCount++;
            Square sq = grid.get(y, x+1);
            if (!t.canBePlacedAt(r, sq.getTile(), sq.getTileRotation(), Placement.RIGHT)) {
                return false;
            }
        }


        return neighborCount > 0;
    }

    public ImmutableSet<Coordinate> whereCanBePlaced(Tile t, Rotation r) {
        ImmutableSet.Builder<Coordinate> setBuilder = new ImmutableSet.Builder<>();

        for (ImmutableTable.Cell<Integer, Integer, Square> cell: grid.cellSet()) {
            if (canPlaceAt(t, r, cell.getRowKey()-1, cell.getColumnKey())) {
                setBuilder.add(new Coordinate(cell.getRowKey()-1, cell.getColumnKey()));
            }
            if (canPlaceAt(t, r, cell.getRowKey()+1, cell.getColumnKey())) {
                setBuilder.add(new Coordinate(cell.getRowKey()+1, cell.getColumnKey()));
            }
            if (canPlaceAt(t, r, cell.getRowKey(), cell.getColumnKey()-1)) {
                setBuilder.add(new Coordinate(cell.getRowKey(), cell.getColumnKey()-1));
            }
            if (canPlaceAt(t, r, cell.getRowKey(), cell.getColumnKey()+1)) {
                setBuilder.add(new Coordinate(cell.getRowKey(), cell.getColumnKey()+1));
            }
        }
        return setBuilder.build();
    }

    public boolean canBePlaced(Tile t) {
        return false
                || !whereCanBePlaced(t, Rotation.R0).isEmpty()
                || !whereCanBePlaced(t, Rotation.R90).isEmpty()
                || !whereCanBePlaced(t, Rotation.R180).isEmpty()
                || !whereCanBePlaced(t, Rotation.R270).isEmpty()
                ;
    }

    public int placedTileCount() {
        return grid.values().size();
    }

    public Board placeTile(Tile t, Rotation r, int y, int x) {
        if (!canPlaceAt(t, r, y, x)) {
            throw new IllegalArgumentException("Can't be placed");
        }

        ImmutableTable.Builder<Integer, Integer, Square> builder = new ImmutableTable.Builder<>();
        builder.putAll(grid);
        builder.put(y, x, new Square(t, r));

        return new Board(builder.build());
    }
}
