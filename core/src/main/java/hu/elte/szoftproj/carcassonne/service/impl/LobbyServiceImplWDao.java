package hu.elte.szoftproj.carcassonne.service.impl;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.domain.player.StandardPlayer;
import hu.elte.szoftproj.carcassonne.persistence.GameDao;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LobbyServiceImplWDao implements LobbyService {

    @Autowired
    GameDao dao;

    @Override
    public CarcassonneGame createNewGame(String initialPlayerName, String boardName) {
        return dao.createNewGame(initialPlayerName, boardName);
    }

    @Override
    public List<CarcassonneGame> listActiveGames() {
        return dao.listActiveGames();
    }

    @Override
    public List<CarcassonneGame> listWaitingGames() {
        return dao.listWaitingGames();
    }

    @Override
    public CarcassonneGame joinGame(String gameId, String player, boolean ai) {
        CarcassonneGame g = dao.getGameById(gameId);

        if (g == null) {
            throw new IllegalArgumentException("UNKNOWN_GAME");
        }

        if (g.getStatus() != GameState.WAITING) {
            throw new IllegalArgumentException("BAD_STATUS");
        }

        if (g.hasPlayer(player)) {
            throw new IllegalArgumentException("ALREADY_HAS_PLAYER");
        }

        return dao.updateGameInfo(new CarcassonneGame(
                g.getId(),
                (new ImmutableList.Builder<Player>().addAll(g.getPlayers()).add(new StandardPlayer(player, ai ? PlayerType.AI : PlayerType.HUMAN))).build(),
                g.getCurrentPlayer(),
                g.getBoard(),
                g.getStatus(),
                g.getDeck()
        ));
    }

    @Override
    public CarcassonneGame startGame(String gameId) {
        CarcassonneGame g = dao.getGameById(gameId);

        if (g == null) {
            throw new IllegalArgumentException("UNKNOWN_GAME");
        }

        if (g.getStatus() != GameState.WAITING) {
            throw new IllegalArgumentException("BAD_STATUS");
        }

        if (g.getPlayers().size() < 2) {
            throw new IllegalArgumentException("TOO_FEW_PLAYERS");
        }

        return dao.updateGameInfo(new CarcassonneGame(
                g.getId(),
                g.getPlayers(),
                Optional.of(new CurrentPlayer(g.getPlayers().get(0), GameAction.PLACE_TILE)),
                Optional.of(g.getDeck().get().getStarterBoard()),
                GameState.PLAYING,
                g.getDeck()
        ));
    }
}
