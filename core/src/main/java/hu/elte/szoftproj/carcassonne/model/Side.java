package hu.elte.szoftproj.carcassonne.model;

public interface Side {

    boolean isBorder();
    boolean canConnectTo(Side side);

}
