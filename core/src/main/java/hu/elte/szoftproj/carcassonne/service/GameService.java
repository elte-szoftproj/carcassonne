package hu.elte.szoftproj.carcassonne.service;

import hu.elte.szoftproj.carcassonne.domain.*;

public interface GameService {

    public Game getGameById(String gameId);

    public Game placeTile(Game g, Player owner, Tile t, Rotation r, int y, int x);

    public Game placeFollower(Game g, Player owner, Follower f, int y, int x, int dx, int dy);

}
