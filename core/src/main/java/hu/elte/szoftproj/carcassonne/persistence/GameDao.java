package hu.elte.szoftproj.carcassonne.persistence;

import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;

import java.util.List;

public interface GameDao {

    CarcassonneGame createNewGame(String initialPlayerName, String boardName);

    List<CarcassonneGame> listActiveGames();

    List<CarcassonneGame> listWaitingGames();

    CarcassonneGame getGameById(String gameId);

    CarcassonneGame updateGameInfo(CarcassonneGame newGameInfo);
}
