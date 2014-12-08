package hu.elte.szoftproj.carcassonne.domain;

public class Coordinate {
    int x;
    int y;

    public Coordinate(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinate)) {
            return false;
        }

        Coordinate other = (Coordinate) obj;
        return other.x == x && other.y == y;
    }

    @Override
    public int hashCode() {
        return x+y;
    }

    @Override
    public String toString() {
        return "[y:" + y + ",x:" + x + "]";
    }
}
