package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableTable;

public class Tile {

    String name;

    // Standard symbols:
    // C - city
    // X - city corner
    // G - ground
    // T - cloister (temple)
    // R - road
    // O - road junction

    final ImmutableTable<Integer, Integer, Character> representation0;
    final ImmutableTable<Integer, Integer, Character> representation90;
    final ImmutableTable<Integer, Integer, Character> representation180;
    final ImmutableTable<Integer, Integer, Character> representation270;

    public Tile(String repStr, String name) {
        this.name = name;

        ImmutableTable.Builder<Integer, Integer, Character> repBuilder = new ImmutableTable.Builder<>();

        if (repStr.length() != 5*5+4) {
            throw new IllegalArgumentException("Bad repStr, expected XXXXXX-XXXXXX-XXXXXX-XXXXXX-XXXXXX");
        }

        repStr = repStr.replaceAll("-", "");

        for(int iy=0;iy<5;iy++) {
            for(int ix=0;ix<5;ix++) {
                int pos = iy*5+ix;
                Character c = repStr.charAt(pos);
                repBuilder.put(iy, ix, c);
            }
        }
        representation0 = repBuilder.build();
        representation90 = rotate90R(representation0);
        representation180 = rotate90R(representation90);
        representation270 = rotate90R(representation180);
    }

    protected ImmutableTable<Integer, Integer, Character> rotate90R(ImmutableTable<Integer, Integer, Character> orig) {
        ImmutableTable.Builder<Integer, Integer, Character> repBuilder = new ImmutableTable.Builder<>();

        for(int iy=0;iy<5;iy++) {
            for(int ix=0;ix<5;ix++) {
                int pos = iy*5+ix;
                Character c = orig.get(4-ix, iy);
                repBuilder.put(iy, ix, c);
            }
        }

        return repBuilder.build();
    }

    public ImmutableTable<Integer, Integer, Character> getRepresentation0() {
        return representation0;
    }

    public ImmutableTable<Integer, Integer, Character> getRepresentation180() {
        return representation180;
    }

    public ImmutableTable<Integer, Integer, Character> getRepresentation270() {
        return representation270;
    }

    public ImmutableTable<Integer, Integer, Character> getRepresentation90() {
        return representation90;
    }

    public ImmutableTable<Integer, Integer, Character> getRepresentation(Rotation r) {
        switch (r) {
            case R0: return getRepresentation0();
            case R90: return getRepresentation90();
            case R180: return getRepresentation180();
            case R270: return getRepresentation270();
        }

        throw new RuntimeException("Illegal rotation parameter!");
    }

    public boolean canBePlacedAt(Rotation selfRotation, Tile otherTile, Rotation otherRotation, Placement where) {
        ImmutableTable<Integer, Integer, Character> selfMatrix = getRepresentation(selfRotation);
        ImmutableTable<Integer, Integer, Character> otherMatrix = otherTile.getRepresentation(otherRotation);

        int minX = (!where.requiresVerticalCheck() ? 0 : (where == Placement.LEFT  ? 0 : 4));
        int maxX = (!where.requiresVerticalCheck() ? 4 : (where == Placement.LEFT  ? 0 : 4));
        int minY = ( where.requiresVerticalCheck() ? 0 : (where == Placement.TOP ? 0 : 4));
        int maxY = ( where.requiresVerticalCheck() ? 4 : (where == Placement.TOP ? 0 : 4));

        for (int iy=minY;iy<=maxY;iy++) {
            for (int ix=minX;ix<=maxX;ix++) {
                Character selfV  = selfMatrix.get(iy, ix);
                Character otherV = otherMatrix.get(minY == maxY ? 4 - minY : iy, minX == maxX ? 4 - minX : ix);

                if (!selfV.equals(otherV)) {
                    if (selfV.equals('X') || otherV.equals('X')) continue;
                    return false;
                }
            }
        }

        return true;
    }

    public String getName() {
        return name;
    }
}

