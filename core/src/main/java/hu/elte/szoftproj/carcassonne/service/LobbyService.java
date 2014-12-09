package hu.elte.szoftproj.carcassonne.service;

import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;

import java.util.List;

public interface LobbyService {

    CarcassonneGame createNewGame(String initialPlayerName, String boardName);

    List<CarcassonneGame> listActiveGames();

    List<CarcassonneGame> listWaitingGames();

    CarcassonneGame joinGame(String gameId, String player, boolean ai);

    CarcassonneGame startGame(String gameId);
}
