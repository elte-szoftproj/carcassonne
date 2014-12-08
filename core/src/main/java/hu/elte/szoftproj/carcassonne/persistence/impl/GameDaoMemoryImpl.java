package hu.elte.szoftproj.carcassonne.persistence.impl;

import hu.elte.szoftproj.carcassonne.domain.Game;
import hu.elte.szoftproj.carcassonne.domain.GameState;
import hu.elte.szoftproj.carcassonne.persistence.GameDao;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class GameDaoMemoryImpl implements GameDao {

    @Autowired
    DeckFactory deckFactory;

    HashMap<String, Game> games;

    public GameDaoMemoryImpl() {
        this.games = new HashMap<>();
    }

    @Override
    public Game createNewGame(String initialPlayerName, String boardName) {
        String uuid = UUID.randomUUID().toString();
        games.put(uuid, new Game(uuid, Optional.of(deckFactory.getDeck(boardName)), initialPlayerName));

        return getGameById(uuid);
    }

    @Override
    public List<Game> listActiveGames() {
        LinkedList<Game> activeGames = new LinkedList<>();

        for(Game g: games.values()) {
            if (g.getStatus() == GameState.PLAYING) {
                activeGames.push(g);
            }
        }

        return activeGames;
    }

    @Override
    public List<Game> listWaitingGames() {
        LinkedList<Game> waitingGames = new LinkedList<>();

        for(Game g: games.values()) {
            if (g.getStatus() == GameState.WAITING) {
                waitingGames.push(g);
            }
        }

        return waitingGames;
    }

    @Override
    public Game getGameById(String gameId) {
        return games.get(gameId);
    }

    @Override
    public Game updateGameInfo(Game newGameInfo) {
        games.put(newGameInfo.getId(), newGameInfo);

        return newGameInfo;
    }

}
