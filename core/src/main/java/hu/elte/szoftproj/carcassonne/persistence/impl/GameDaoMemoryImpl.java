package hu.elte.szoftproj.carcassonne.persistence.impl;

import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.domain.GameState;
import hu.elte.szoftproj.carcassonne.persistence.GameDao;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class GameDaoMemoryImpl implements GameDao {

    @Autowired
    DeckFactory deckFactory;

    HashMap<String, CarcassonneGame> games;

    public GameDaoMemoryImpl() {
        this.games = new HashMap<>();
    }

    @Override
    public CarcassonneGame createNewGame(String initialPlayerName, String boardName) {
        String uuid = UUID.randomUUID().toString();
        games.put(uuid, new CarcassonneGame(uuid, Optional.of(deckFactory.getDeck(boardName)), initialPlayerName));

        return getGameById(uuid);
    }

    @Override
    public List<CarcassonneGame> listActiveGames() {
        LinkedList<CarcassonneGame> activeGames = new LinkedList<>();

        for(CarcassonneGame g: games.values()) {
            if (g.getStatus() == GameState.PLAYING) {
                activeGames.push(g);
            }
        }

        return activeGames;
    }

    @Override
    public List<CarcassonneGame> listWaitingGames() {
        LinkedList<CarcassonneGame> waitingGames = new LinkedList<>();

        for(CarcassonneGame g: games.values()) {
            if (g.getStatus() == GameState.WAITING) {
                waitingGames.push(g);
            }
        }

        return waitingGames;
    }

    @Override
    public CarcassonneGame getGameById(String gameId) {
        return games.get(gameId);
    }

    @Override
    public CarcassonneGame updateGameInfo(CarcassonneGame newGameInfo) {
        games.put(newGameInfo.getId(), newGameInfo);

        return newGameInfo;
    }

}
