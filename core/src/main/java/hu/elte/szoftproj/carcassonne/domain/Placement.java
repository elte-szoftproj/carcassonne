package hu.elte.szoftproj.carcassonne.domain;

public enum Placement {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT;

    public boolean requiresVerticalCheck() {
        return this.equals(LEFT) || this.equals(RIGHT);
    }
}
