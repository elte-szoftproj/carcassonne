package hu.elte.szoftproj.carcassonne.service;

import hu.elte.szoftproj.carcassonne.domain.*;

public interface GameService {

    public CarcassonneGame getGameById(String playerName, String gameId);

    public CarcassonneGame placeTile(CarcassonneGame g, Player owner, Tile t, Rotation r, int y, int x);

    public CarcassonneGame placeFollower(CarcassonneGame g, Player owner, Follower f, int y, int x, int dy, int dx);

    public CarcassonneGame dontPlaceFollower(CarcassonneGame g, Player owner);
}
