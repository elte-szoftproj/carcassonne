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
}
