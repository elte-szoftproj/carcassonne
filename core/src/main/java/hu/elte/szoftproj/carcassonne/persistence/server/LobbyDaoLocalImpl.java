package hu.elte.szoftproj.carcassonne.persistence.server;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.Game;
import hu.elte.szoftproj.carcassonne.domain.GameState;
import hu.elte.szoftproj.carcassonne.domain.Player;
import hu.elte.szoftproj.carcassonne.domain.PlayerType;
import hu.elte.szoftproj.carcassonne.persistence.LobbyDao;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class LobbyDaoLocalImpl implements LobbyDao{

    @Autowired
    DeckFactory deckFactory;

    HashMap<String, Game> games;

    public LobbyDaoLocalImpl() {
        this.games = new HashMap<>();
    }

    @Override
    public Game createNewGame(String initialPlayerName, String boardName) {
        String uuid = UUID.randomUUID().toString();
        games.put(uuid, new Game(uuid, deckFactory.getDeck(boardName), initialPlayerName));

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

    @Override
    public Game joinGame(String gameId, String player, boolean ai) {
        Game g = getGameById(gameId);

        if (g == null) {
            throw new IllegalArgumentException("UNKNOWN_GAME");
        }

        if (g.getStatus() != GameState.WAITING) {
            throw new IllegalArgumentException("BAD_STATUS");
        }

        if (g.hasPlayer(player)) {
            throw new IllegalArgumentException("ALREADY_HAS_PLAYER");
        }

        return updateGameInfo(new Game(
                g.getId(),
                (new ImmutableList.Builder<Player>().addAll(g.getPlayers()).add(new Player(player, ai ? PlayerType.AI : PlayerType.HUMAN))).build(),
                g.getCurrentPlayer(),
                g.getBoard(),
                g.getStatus(),
                g.getDeck()
        ));
    }

    @Override
    public Game startGame(String gameId) {
        Game g = getGameById(gameId);

        if (g == null) {
            throw new IllegalArgumentException("UNKNOWN_GAME");
        }

        if (g.getStatus() != GameState.WAITING) {
            throw new IllegalArgumentException("BAD_STATUS");
        }

        if (g.getPlayers().size() < 2) {
            throw new IllegalArgumentException("TOO_FEW_PLAYERS");
        }

        return updateGameInfo(new Game(
                g.getId(),
                g.getPlayers(),
                g.getCurrentPlayer(),
                g.getBoard(),
                GameState.PLAYING,
                g.getDeck()
        ));
    }

}
