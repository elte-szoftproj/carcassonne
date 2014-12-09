package hu.elte.szoftproj.carcassonne.domain;

public enum Rotation {
    R0(0),
    R90(90),
    R180(180),
    R270(270);

    private int degrees;

    Rotation(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }

    public Rotation prev() {
        switch(this) {
            case R0: return R270;
            case R90: return R0;
            case R180: return R90;
            case R270: return R180;
        }
        throw new IllegalArgumentException("Won't happen!");
    }

    public Rotation next() {
        switch(this) {
            case R0: return R90;
            case R90: return R180;
            case R180: return R270;
            case R270: return R0;
        }
        throw new IllegalArgumentException("Won't happen!");
    }
}
