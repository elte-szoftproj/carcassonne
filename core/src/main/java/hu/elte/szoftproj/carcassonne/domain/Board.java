package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.TreeBasedTable;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private ImmutableTable<Integer, Integer, Square> grid;

    /**
     * Has different keys! gridKey = areaGridKey / 5
     */
    private ImmutableTable<Integer, Integer, Area> areaGrid;

    private ImmutableSet<Area> areas;

    public Board(Tile tile, Rotation tileRotation) {
        grid = (new ImmutableTable.Builder<Integer, Integer, Square>()).put(0, 0, new Square(tile, tileRotation)).build();

        buildAreaGrid(grid);
    }

    Board(ImmutableTable<Integer, Integer, Square> grid) {
        this.grid = grid;
        buildAreaGrid(grid);
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

    private void buildAreaGrid(ImmutableTable<Integer, Integer, Square> grid) {
        TreeBasedTable<Integer, Integer, Area> tempTable = TreeBasedTable.create();

        HashMap<Area, ImmutableSet.Builder<Coordinate>> areaHelper = new HashMap<>();
        HashMap<Area, Integer> edgeModifierMap = new HashMap<>();

        for (Integer iy: ImmutableSortedSet.copyOf(grid.rowKeySet())) {
            for (Integer ix: ImmutableSortedSet.copyOf(grid.columnKeySet())) {

                if (!grid.contains(iy, ix)) {
                    continue;
                }

                Tile actTile = grid.get(iy, ix).getTile();
                Rotation actRot = grid.get(iy, ix).getTileRotation();

                for (int dy = 0; dy < 5; dy ++) {
                    for (int dx = 0; dx < 5 ; dx++) {
                        int ay = iy * 5 + dy;
                        int ax = ix * 5 + dx;


                        Character type = actTile.getRepresentation(actRot).get(dy, dx);

                        if (type.equals('X') || type.equals('O')) { // can't place followers there
                            continue;
                        }

                        boolean sameAsAbove = tempTable.contains(ay-1, ax) && tempTable.get(ay-1, ax).getType().equals(type);
                        boolean sameAsLeft  = tempTable.contains(ay, ax-1) && tempTable.get(ay, ax-1).getType().equals(type);

                        int edgeModifier = 0;
                        if (dy==0) edgeModifier++;
                        if (dx==0) edgeModifier++;
                        if (dy==4) edgeModifier++;
                        if (dx==4) edgeModifier++;
                        if (dy==0 && sameAsAbove) edgeModifier-=2;
                        if (dx==0 && sameAsLeft)  edgeModifier-=2;

                        if (sameAsLeft && sameAsAbove) {
                            if (tempTable.get(ay-1, ax).equals(tempTable.get(ay, ax-1))) {
                                Area curr = tempTable.get(ay-1, ax);
                                areaHelper.get(curr).add(new Coordinate(ay, ax));
                                tempTable.put(ay, ax, curr);
                                edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifier);
                            } else {
                                Area curr = tempTable.get(ay-1, ax);
                                Area other = tempTable.get(ay, ax-1);
                                ImmutableSet<Coordinate> tmp = areaHelper.get(other).build();
                                areaHelper.get(curr).add(new Coordinate(ay, ax)).addAll(tmp);
                                areaHelper.remove(other);
                                tempTable.put(ay, ax, curr);
                                edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifierMap.get(other)+edgeModifier);
                                edgeModifierMap.remove(other);
                                for (Coordinate c: tmp) {
                                    tempTable.put(c.getY(), c.getX(), curr);
                                }
                            }
                        } else if (sameAsAbove) {
                            Area curr = tempTable.get(ay-1, ax);
                            areaHelper.get(curr).add(new Coordinate(ay, ax));
                            tempTable.put(ay, ax, curr);
                            edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifier);
                        } else if (sameAsLeft) {
                            Area curr = tempTable.get(ay, ax-1);
                            areaHelper.get(curr).add(new Coordinate(ay, ax));
                            tempTable.put(ay, ax, curr);
                            edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifier);
                        } else {
                            Area curr = new Area(type);
                            areaHelper.put(curr, new ImmutableSet.Builder<>());
                            areaHelper.get(curr).add(new Coordinate(ay, ax));
                            tempTable.put(ay, ax, curr);
                            edgeModifierMap.put(curr, 0);
                            edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifier);
                        }
                    }
                }

            }
        }

        ImmutableTable.Builder<Integer, Integer, Area> builder = new ImmutableTable.Builder<>();

        ImmutableSet.Builder<Area> setBuilder = new ImmutableSet.Builder<>();

        for(Map.Entry<Area, ImmutableSet.Builder<Coordinate>> entry: areaHelper.entrySet()) {
            entry.getKey().setCoordinates(entry.getValue().build());
            entry.getKey().setOpenEdgeCount(edgeModifierMap.get(entry.getKey()));
            setBuilder.add(entry.getKey());
        }

        this.areaGrid =  builder.build();
        this.areas = setBuilder.build();
    }

    public ImmutableSet<Area> getAreas() {
        return areas;
    }
}
