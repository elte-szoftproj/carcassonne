package hu.elte.szoftproj.carcassonne.model;

public interface Side {

    boolean canConnectTo(Side side);

    boolean isBorder();
    boolean isConnectedTo(Direction dir);

}
