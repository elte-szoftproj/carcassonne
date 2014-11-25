package hu.elte.szoftproj.carcassonne.service;

import hu.elte.szoftproj.carcassonne.domain.Game;

import java.util.List;

public interface LobbyService {

    Game createNewGame(String initialPlayerName, String boardName);

    List<Game> listActiveGames();

    List<Game> listWaitingGames();

    Game getGameById(String gameId);

    Game joinGame(String gameId, String player, boolean ai);

    Game startGame(String gameId);
}
