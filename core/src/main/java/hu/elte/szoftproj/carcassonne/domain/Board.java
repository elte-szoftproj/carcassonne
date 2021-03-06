package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Board {
    private ImmutableTable<Integer, Integer, Square> grid;

    private Optional<Tile> lastPlacedTile;
    private Optional<Coordinate> lastCoordinates;

    /**
     * Has different keys! gridKey = areaGridKey / 5
     */
    private ImmutableTable<Integer, Integer, Area> areaGrid;

    private ImmutableSet<Area> areas;

    private ImmutableMap<Follower, Area> usedFollowers;

    private ImmutableList<Table.Cell<Integer, Integer, Square>> placementOrder; // DO NOT USE, BUGGY! Contains the original items, without modification

    public Board(Tile tile, Rotation tileRotation) {
        grid = (new ImmutableTable.Builder<Integer, Integer, Square>()).put(0, 0, new Square(tile, tileRotation)).build();
        lastPlacedTile = Optional.empty();
        lastCoordinates = Optional.empty();
        placementOrder = ImmutableList.of(grid.cellSet().asList().get(0));
        buildAreaGrid(grid);
    }

    Board(ImmutableTable<Integer, Integer, Square> grid, ImmutableList<Table.Cell<Integer, Integer, Square>> placementOrder, Optional<Tile> lastPlacedTile, Optional<Coordinate> lastCoordinate) {
        this.grid = grid;
        this.lastPlacedTile = lastPlacedTile;
        this.lastCoordinates = lastCoordinate;
        this.placementOrder = placementOrder;
        buildAreaGrid(grid);
    }

    public Optional<Coordinate> getLastCoordinates() {
        return lastCoordinates;
    }

    public ImmutableList<Table.Cell<Integer, Integer, Square>> getPlacementOrder() {
        return placementOrder;
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
        ImmutableList.Builder<Table.Cell<Integer, Integer, Square>> lB = new ImmutableList.Builder<>();

        ImmutableTable<Integer, Integer, Square> newGrid = builder.build();

        lB.addAll(placementOrder);

        for(Table.Cell<Integer, Integer, Square> c: newGrid.cellSet()){
            if (c.getRowKey().equals(y) && c.getColumnKey().equals(x)) {
                lB.add(c);
                break;
            }
        }

        return new Board(newGrid, lB.build(), Optional.of(t), Optional.of(new Coordinate(y, x)));
    }

    public boolean canPlaceAFollower(int y, int x, ImmutableList<Follower> followers) {
        for (int dy=0;dy<5;dy++) {
            for (int dx=0;dx<5;dx++) {
                for (Follower f: followers) {
                    if (canPlaceFollower(y, x, f, dy, dx)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean canPlaceFollower(int y, int x, Follower f, int dy, int dx) {
        Square s = grid.get(y, x);
        if (usedFollowers.containsKey(f)) {
            return false;
        }
        if (!areaGrid.contains(y * 5 + dy, x * 5 + dx)) {
            return false;
        }
        if (s.canPlaceFollower(f, new Coordinate(dy, dx)) && f.canBePlacedAt(areaGrid.get(y * 5 + dy, x * 5 + dx))) {
            return true;
        }

        return false;
    }

    public int tileCountAround(int y, int x) {
        int t = 0;
        for (int iy=-1;iy<2;iy++) {
            for (int ix=-1;ix<2;ix++) {
                if (grid.contains(y+iy, x+ix)) {
                    t++;
                }
            }
        }

        return t;
    }

    public Board placeFollower(int y, int x, Follower f, int dy, int dx) {
        if (!canPlaceFollower(y, x, f, dy, dx)) {
            throw new IllegalArgumentException("Can't be placed!");
        }
        Square s = grid.get(y, x);
        Square newSq = s.addFollower(f, new Coordinate(dy, dx));

        ImmutableTable.Builder<Integer, Integer, Square> builder = new ImmutableTable.Builder<>();
        for (Table.Cell<Integer, Integer, Square> sq: grid.cellSet()) {
            if (sq.getRowKey().equals(y) && sq.getColumnKey().equals(x)) {
                builder.put(sq.getRowKey(), sq.getColumnKey(), newSq);
            } else {
                builder.put(sq.getRowKey(), sq.getColumnKey(), sq.getValue());
            }
        }
        return new Board(builder.build(), placementOrder, Optional.empty(), Optional.empty());
    }

    public Board removeFollowersFromArea(Area a) {
        ImmutableTable.Builder<Integer, Integer, Square> builder = new ImmutableTable.Builder<>();

        for (Table.Cell<Integer, Integer, Square> sq : grid.cellSet()) {
            Square s = sq.getValue();
            for(Follower f: a.getFollowers()) {
                if (s.getPlacedFollowers().containsValue(f)) {
                    s = s.removeFollower(f);
                }
            }
            builder.put(sq.getRowKey(), sq.getColumnKey(), s);
        }

        return new Board(builder.build(), placementOrder, Optional.<Tile>empty(), Optional.empty());
    }

    public ImmutableList<Follower> notUsedFollowers(ImmutableList<Follower> from) {
        ImmutableList.Builder<Follower> builder = new ImmutableList.Builder<>();
        for (Follower f: from) {
            if (!usedFollowers.containsKey(f)) {
                builder.add(f);
            }
        }

        return builder.build();
    }

    public ImmutableSet<Area> getClosedNeighborAreasOfTYpe(Area center, Character type) {
        ImmutableSet.Builder<Area> builder = new ImmutableSet.Builder<>();

        if (!areas.contains(center)) {
            throw new IllegalArgumentException("Area not found");
        }

        for (Coordinate c : center.getCoordinates()) {
            if (    true
                    && areaGrid.contains(c.getY()-1, c.getX())
                    && areaGrid.get(c.getY()-1, c.getX()) != center
                    && areaGrid.get(c.getY()-1, c.getX()).getType().equals(type)
                    && areaGrid.get(c.getY()-1, c.getX()).isClosed()
                    ) {
                builder.add(areaGrid.get(c.getY()-1, c.getX()));
            }
            if (    true
                    && areaGrid.contains(c.getY()+1, c.getX())
                    && areaGrid.get(c.getY()+1, c.getX()) != center
                    && areaGrid.get(c.getY()+1, c.getX()).getType().equals(type)
                    && areaGrid.get(c.getY() + 1, c.getX()).isClosed()
                    ) {
                builder.add(areaGrid.get(c.getY()+1, c.getX()));
            }
            if (    true
                    && areaGrid.contains(c.getY(), c.getX()-1)
                    && areaGrid.get(c.getY(), c.getX()-1) != center
                    && areaGrid.get(c.getY(), c.getX()-1).getType().equals(type)
                    && areaGrid.get(c.getY(), c.getX() - 1).isClosed()
                    ) {
                builder.add(areaGrid.get(c.getY(), c.getX()-1));
            }
            if (    true
                    && areaGrid.contains(c.getY(), c.getX()+1)
                    && areaGrid.get(c.getY(), c.getX()+1) != center
                    && areaGrid.get(c.getY(), c.getX()+1).getType().equals(type)
                    && areaGrid.get(c.getY(), c.getX()+1).isClosed()
                    ) {
                builder.add(areaGrid.get(c.getY(), c.getX()+1));
            }
        }

        return builder.build();
    }

    public ImmutableSet<Area> getNeighborAreasOfTYpe(Area center, Character type) {
        ImmutableSet.Builder<Area> builder = new ImmutableSet.Builder<>();

        if (!areas.contains(center)) {
            throw new IllegalArgumentException("Area not found");
        }

        for (Coordinate c : center.getCoordinates()) {
            if (    true
                    && areaGrid.contains(c.getY()-1, c.getX())
                    && areaGrid.get(c.getY()-1, c.getX()) != center
                    && areaGrid.get(c.getY()-1, c.getX()).getType().equals(type)
                    ) {
                builder.add(areaGrid.get(c.getY()-1, c.getX()));
            }
            if (    true
                    && areaGrid.contains(c.getY()+1, c.getX())
                    && areaGrid.get(c.getY()+1, c.getX()) != center
                    && areaGrid.get(c.getY()+1, c.getX()).getType().equals(type)
                    ) {
                builder.add(areaGrid.get(c.getY()+1, c.getX()));
            }
            if (    true
                    && areaGrid.contains(c.getY(), c.getX()-1)
                    && areaGrid.get(c.getY(), c.getX()-1) != center
                    && areaGrid.get(c.getY(), c.getX()-1).getType().equals(type)
                    ) {
                builder.add(areaGrid.get(c.getY(), c.getX()-1));
            }
            if (    true
                    && areaGrid.contains(c.getY(), c.getX()+1)
                    && areaGrid.get(c.getY(), c.getX()+1) != center
                    && areaGrid.get(c.getY(), c.getX()+1).getType().equals(type)
                    ) {
                builder.add(areaGrid.get(c.getY(), c.getX()+1));
            }
        }

        return builder.build();
    }


    private void buildAreaGrid(ImmutableTable<Integer, Integer, Square> grid) {
        TreeBasedTable<Integer, Integer, Area> tempTable = TreeBasedTable.create();

        HashMap<Area, ImmutableSet.Builder<Coordinate>> areaHelper = new HashMap<>();
        HashMap<Area, ImmutableSet.Builder<Follower>> areaFollowerHelper = new HashMap<>();
        HashMap<Area, Integer> edgeModifierMap = new HashMap<>();
        HashMap<Follower, Area> followerMap = new HashMap<>();

        for (Integer iy: ImmutableSortedSet.copyOf(grid.rowKeySet())) {
            for (Integer ix: ImmutableSortedSet.copyOf(grid.columnKeySet())) {

                if (!grid.contains(iy, ix)) {
                    continue;
                }

                Square actSq = grid.get(iy, ix);
                Tile actTile = actSq.getTile();
                Rotation actRot = actSq.getTileRotation();

                for (int dy = 0; dy < 5; dy ++) {
                    for (int dx = 0; dx < 5 ; dx++) {
                        int ay = iy * 5 + dy;
                        int ax = ix * 5 + dx;

                        Follower currentFollower = null;

                        if (actSq.getPlacedFollowers().contains(dy, dx)) {
                            currentFollower = actSq.getPlacedFollowers().get(dy, dx);
                        }

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

                        Area curr = null;

                        if (sameAsLeft && sameAsAbove) {
                            if (tempTable.get(ay-1, ax).equals(tempTable.get(ay, ax-1))) {
                                curr = tempTable.get(ay-1, ax);
                                areaHelper.get(curr).add(new Coordinate(ay, ax));
                                tempTable.put(ay, ax, curr);
                                edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifier);
                            } else {
                                curr = tempTable.get(ay-1, ax);
                                Area other = tempTable.get(ay, ax-1);
                                ImmutableSet<Coordinate> tmp = areaHelper.get(other).build();
                                areaHelper.get(curr).add(new Coordinate(ay, ax)).addAll(tmp);
                                areaHelper.remove(other);
                                tempTable.put(ay, ax, curr);
                                edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifierMap.get(other)+edgeModifier);
                                edgeModifierMap.remove(other);

                                ImmutableSet<Follower> tmpF = areaFollowerHelper.get(other).build();
                                areaFollowerHelper.put(curr, areaFollowerHelper.get(curr).addAll(tmpF));
                                for (Follower f: tmpF) {
                                    followerMap.put(f, curr);
                                }

                                areaFollowerHelper.remove(other);
                                for (Coordinate c: tmp) {
                                    tempTable.put(c.getY(), c.getX(), curr);
                                }
                            }
                        } else if (sameAsAbove) {
                            curr = tempTable.get(ay-1, ax);
                            areaHelper.get(curr).add(new Coordinate(ay, ax));
                            tempTable.put(ay, ax, curr);
                            edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifier);
                        } else if (sameAsLeft) {
                            curr = tempTable.get(ay, ax-1);
                            areaHelper.get(curr).add(new Coordinate(ay, ax));
                            tempTable.put(ay, ax, curr);
                            edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifier);
                        } else {
                            curr = new Area(type);
                            areaHelper.put(curr, new ImmutableSet.Builder<>());
                            areaHelper.get(curr).add(new Coordinate(ay, ax));
                            tempTable.put(ay, ax, curr);
                            edgeModifierMap.put(curr, 0);
                            edgeModifierMap.put(curr, edgeModifierMap.get(curr)+edgeModifier);
                            areaFollowerHelper.put(curr, new ImmutableSet.Builder<>());
                        }

                        if(currentFollower != null) {
                            followerMap.put(currentFollower, curr);
                            areaFollowerHelper.get(curr).add(currentFollower);
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
            entry.getKey().setFollowers(areaFollowerHelper.get(entry.getKey()).build());

            for (Coordinate c: entry.getKey().getCoordinates()) {
                builder.put(c.getY(), c.getX(), entry.getKey());
            }
        }

        this.areaGrid =  builder.build();
        this.areas = setBuilder.build();
        this.usedFollowers = ImmutableMap.copyOf(followerMap);
    }

    public ImmutableSet<Area> getAreas() {
        return areas;
    }

    public ImmutableTable<Integer, Integer, Square> getGrid() {
        return grid;
    }

    public ImmutableTable<Integer, Integer, Area> getAreaGrid() {
        return areaGrid;
    }

    public ImmutableMap<Follower, Area> getUsedFollowers() {
        return usedFollowers;
    }

    public Optional<Tile> getLastPlacedTile() {
        return lastPlacedTile;
    }

    // debug
    public void printAreaGrid() {
        for (Integer iy : ImmutableSortedSet.copyOf(areaGrid.rowKeySet())) {
            for (Integer ix : ImmutableSortedSet.copyOf(areaGrid.columnKeySet())) {

                if (!areaGrid.contains(iy, ix)) {
                    System.out.print(" ");
                    continue;
                }

                System.out.print(areaGrid.get(iy, ix).getType());
            }

            System.out.println();
        }
    }
}
