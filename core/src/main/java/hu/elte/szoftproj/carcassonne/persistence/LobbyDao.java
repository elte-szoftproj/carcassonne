package hu.elte.szoftproj.carcassonne.persistence;

import hu.elte.szoftproj.carcassonne.domain.Game;

import java.util.List;

public interface LobbyDao {

    Game createNewGame(String initialPlayerName, String boardName);

    List<Game> listActiveGames();

    List<Game> listWaitingGames();

    Game getGameById(String gameId);

    // used internally by GameDao
    Game updateGameInfo(Game newGameInfo);

    Game joinGame(String gameId, String player, boolean ai);

    Game startGame(String gameId);
}
