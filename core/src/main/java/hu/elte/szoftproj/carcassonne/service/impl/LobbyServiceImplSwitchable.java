package hu.elte.szoftproj.carcassonne.service.impl;

import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import hu.elte.szoftproj.carcassonne.service.RemoteLobbyService;

import java.util.List;

public class LobbyServiceImplSwitchable implements LobbyService {

    LobbyService local;

    RemoteLobbyService remote;

    boolean remoteActive;

    public LobbyServiceImplSwitchable(LobbyService local, RemoteLobbyService remote, boolean remoteActive) {
        this.local = local;
        this.remote = remote;
        this.remoteActive = remoteActive;
    }

    public LobbyService getActiveService() {
        return remoteActive ? remote : local;
    }


    @Override
    public CarcassonneGame createNewGame(String initialPlayerName, String boardName) {
        return getActiveService().createNewGame(initialPlayerName, boardName);
    }

    @Override
    public List<CarcassonneGame> listActiveGames() {
        return getActiveService().listActiveGames();
    }

    @Override
    public List<CarcassonneGame> listWaitingGames() {
        return getActiveService().listWaitingGames();
    }

    @Override
    public CarcassonneGame joinGame(String gameId, String player, boolean ai) {
        return getActiveService().joinGame(gameId, player, ai);
    }

    @Override
    public CarcassonneGame startGame(String gameId) {
        return getActiveService().startGame(gameId);
    }

    public void switchToRemote() {
        this.remoteActive = true;
    }

    public void switchToLocal() {
        this.remoteActive = false;
    }

    public RemoteLobbyService getRemote() {
        return remote;
    }
}
