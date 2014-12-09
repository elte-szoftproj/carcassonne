package hu.elte.szoftproj.carcassonne.service.impl;

import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.service.RemoteGameService;

public class GameServiceImplSwitchable implements GameService {

    GameService local;

    RemoteGameService remote;

    boolean remoteActive;

    public GameServiceImplSwitchable(GameService local, RemoteGameService remote, boolean remoteActive) {
        this.local = local;
        this.remote = remote;
        this.remoteActive = remoteActive;
    }

    public GameService getActiveService() {
        return remoteActive ? remote : local;
    }

    public RemoteGameService getRemote() {
        return remote;
    }

    public void switchToRemote() {
        this.remoteActive = true;
    }

    public void switchToLocal() {
        this.remoteActive = false;
    }


    @Override
    public Game getGameById(String playerName, String gameId) {
        return getActiveService().getGameById(playerName, gameId);
    }

    @Override
    public Game placeTile(Game g, Player owner, Tile t, Rotation r, int y, int x) {
        return getActiveService().placeTile(g, owner, t, r, y, x);
    }

    @Override
    public Game placeFollower(Game g, Player owner, Follower f, int y, int x, int dy, int dx) {
        return getActiveService().placeFollower(g, owner, f, y, x, dy, dx);
    }

    @Override
    public Game dontPlaceFollower(Game g, Player owner) {
        return getActiveService().dontPlaceFollower(g, owner);
    }
}
