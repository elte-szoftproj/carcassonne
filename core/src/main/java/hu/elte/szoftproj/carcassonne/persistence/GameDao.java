package hu.elte.szoftproj.carcassonne.persistence;

import hu.elte.szoftproj.carcassonne.domain.Game;

import java.util.List;

public interface GameDao {

    Game createNewGame(String initialPlayerName, String boardName);

    List<Game> listActiveGames();

    List<Game> listWaitingGames();

    Game getGameById(String gameId);

    Game updateGameInfo(Game newGameInfo);
}
