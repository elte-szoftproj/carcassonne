package hu.elte.szoftproj.carcassonne.service.impl;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.persistence.GameDao;
import hu.elte.szoftproj.carcassonne.service.Deck;
import hu.elte.szoftproj.carcassonne.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;

public class GameServiceImplWDao implements GameService {

    @Autowired
    GameDao dao;

    @Override
    public Game getGameById(String gameId) {
        return dao.getGameById(gameId);
    }

    @Override
    public Game placeTile(Game g, Player owner, Tile t, Rotation r, int y, int x) {
        Game realGame = dao.getGameById(g.getId());

        if (realGame.isFinished()) {
            throw new IllegalArgumentException("ALREADY_FINISHED");
        }

        if (realGame.getStatus() != GameState.PLAYING) {
            throw new IllegalArgumentException("STILL_WAITING");
        }

        if (!realGame.getCurrentPlayer().isPresent() || !realGame.getCurrentPlayer().get().getPlayer().equals(owner)) {
            throw new IllegalArgumentException("NOT_YOUR_TURN");
        }

        if (!realGame.getCurrentPlayer().get().getAction().equals(GameAction.PLACE_TILE)) {
            throw new IllegalArgumentException("NOT_TILE_ACTION");
        }

        if (!realGame.getDeck().get().peekNext().equals(t)) {
            throw new IllegalArgumentException("NOT_CURRENT_TILE");
        }

        if (!realGame.getBoard().get().canPlaceAt(t, r, y, x)) {
            throw new IllegalArgumentException("NOT_PLACABLE");
        }

        Board newBoard = realGame.getBoard().get().placeTile(t, r, y, x);

        Deck newDeck = realGame.getDeck().get().removeNext();

        if (newDeck.getRemainingPieceCount() != 0) {
            // first ensure that there is a tile we can place
            Deck tmp = newDeck;
            while (tmp.getRemainingPieceCount() != 0 && !newBoard.canBePlaced(newDeck.peekNext())) {
                tmp = tmp.removeNext();
            }

            if (tmp.getRemainingPieceCount() == 0) { // trash
                newDeck = tmp;
            } else {
                while (!newBoard.canBePlaced(newDeck.peekNext())) {
                    newDeck = newDeck.reshuffle();
                }
            }
        }

        CurrentPlayer nextPlayer = realGame.getCurrentPlayer().get().next(realGame.getPlayers());

        // TODO: refactor this elsewhere!
        if (newBoard.notUsedFollowers(nextPlayer.getPlayer().getFollowers()).isEmpty()) {
            // skip follower turn if we don't have one
            System.out.println("BUG! " + nextPlayer.getPlayer().getFollowers().size());
            nextPlayer = nextPlayer.next(realGame.getPlayers());
        }

        realGame = new Game(
                realGame.getId(),
                realGame.getPlayers(),
                Optional.of(nextPlayer),
                Optional.of(newBoard),
                realGame.getStatus(),
                Optional.of(newDeck)
        );

        dao.updateGameInfo(realGame);

        return realGame;
    }

    @Override
    public Game placeFollower(Game g, Player owner, Follower f, int y, int x, int dx, int dy) {

        Game realGame = dao.getGameById(g.getId());

        if (realGame.isFinished()) {
            throw new IllegalArgumentException("ALREADY_FINISHED");
        }

        if (realGame.getStatus() != GameState.PLAYING) {
            throw new IllegalArgumentException("STILL_WAITING");
        }

        if (!realGame.getCurrentPlayer().isPresent() || !realGame.getCurrentPlayer().get().getPlayer().equals(owner)) {
            throw new IllegalArgumentException("NOT_YOUR_TURN");
        }

        if (!f.getOwner().equals(owner)) {
            throw new IllegalArgumentException("NOT_YOUR_FOLLOWER");
        }

        if (!realGame.getCurrentPlayer().get().getAction().equals(GameAction.PLACE_FOLLOWER)) {
            throw new IllegalArgumentException("NOT_FOLLOWER_ACTION");
        }

        Board board = realGame.getBoard().get();

        if (!board.getLastPlacedTile().isPresent()) {
            throw new IllegalArgumentException("NO_LAST_PLACED_TILE");
        }

        if (!board.getGrid().contains(y, x)) {
            throw new IllegalArgumentException("NOT_CURRENT_TILE");
        }

        Tile target = board.getGrid().get(y, x).getTile();

        if (!board.getLastPlacedTile().get().equals(target)) {
            throw new IllegalArgumentException("NOT_CURRENT_TILE");
        }

        Board newBoard = board.placeFollower(y, x, f, dy, dx);

        ImmutableList<Player> players = realGame.getPlayers();

        for(Area a: new HashSet<Area>(newBoard.getUsedFollowers().values())) {
            if (a.isClosed() && a.removeFollowersWhenClosed()) {
                // recalculate scores!
            }
        }

        boolean finished = realGame.getDeck().get().getRemainingPieceCount() == 0;

        if (finished) {
            // remove remaining followers & add scores
            for(Area a: new HashSet<Area>(newBoard.getUsedFollowers().values())) {
                if (a.isClosed() && !a.removeFollowersWhenClosed()) {
                    // recalculate scores!
                }
            }
        }

        realGame = new Game(
                realGame.getId(),
                players,
                finished ? Optional.empty() : Optional.of(realGame.getCurrentPlayer().get().next(realGame.getPlayers())),
                Optional.of(newBoard),
                finished ? GameState.FINISHED : realGame.getStatus(),
                realGame.getDeck()
        );

        dao.updateGameInfo(realGame);

        return realGame;
    }
}
