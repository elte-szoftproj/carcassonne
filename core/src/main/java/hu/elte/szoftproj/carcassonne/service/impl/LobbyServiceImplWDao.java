package hu.elte.szoftproj.carcassonne.service.impl;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.Game;
import hu.elte.szoftproj.carcassonne.domain.GameState;
import hu.elte.szoftproj.carcassonne.domain.Player;
import hu.elte.szoftproj.carcassonne.domain.PlayerType;
import hu.elte.szoftproj.carcassonne.persistence.LobbyDao;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LobbyServiceImplWDao implements LobbyService {

    @Autowired
    LobbyDao dao;

    @Override
    public Game createNewGame(String initialPlayerName, String boardName) {
        return dao.createNewGame(initialPlayerName, boardName);
    }

    @Override
    public List<Game> listActiveGames() {
        return dao.listActiveGames();
    }

    @Override
    public List<Game> listWaitingGames() {
        return dao.listWaitingGames();
    }

    @Override
    public Game getGameById(String gameId) {
        return dao.getGameById(gameId);
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

        return dao.updateGameInfo(new Game(
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

        return dao.updateGameInfo(new Game(
                g.getId(),
                g.getPlayers(),
                g.getCurrentPlayer(),
                g.getBoard(),
                GameState.PLAYING,
                g.getDeck()
        ));
    }
}
