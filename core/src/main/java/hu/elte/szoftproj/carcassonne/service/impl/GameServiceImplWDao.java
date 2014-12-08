package hu.elte.szoftproj.carcassonne.service.impl;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.persistence.GameDao;
import hu.elte.szoftproj.carcassonne.service.Deck;
import hu.elte.szoftproj.carcassonne.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.LinkedList;
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
        if (newBoard.notUsedFollowers(nextPlayer.getPlayer().getFollowers()).isEmpty() ||
                !newBoard.canPlaceAFollower(y, x, newBoard.notUsedFollowers(nextPlayer.getPlayer().getFollowers()))) {
            // skip follower turn if we don't have one
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
    public Game placeFollower(Game g, Player owner, Follower f, int y, int x, int dy, int dx) {

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

        if (!board.canPlaceFollower(y, x, f, dy, dx)) {
            throw new IllegalArgumentException("CANT_PLACE_THERE");
        }

        Board newBoard = board.placeFollower(y, x, f, dy, dx);

        return updateGameAfterFollower(newBoard, realGame);
    }

    @Override
    public Game dontPlaceFollower(Game g, Player owner) {
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

        if (!realGame.getCurrentPlayer().get().getAction().equals(GameAction.PLACE_FOLLOWER)) {
            throw new IllegalArgumentException("NOT_FOLLOWER_ACTION");
        }

        Board board = realGame.getBoard().get();

        return updateGameAfterFollower(board, realGame);
    }

    protected void updateBestScores(LinkedList<Player> players, Area a, int scoreSum) {
        ImmutableList<AreaScore> scores = a.getScores();

        int max = scores.get(0).getScore();

        int count = 0;
        for (AreaScore as: scores) {
            if (as.getScore() == max) {
                count++;
            }
        }

        int toAdd = scoreSum / count;

        for (AreaScore as: scores) {
            if (as.getScore() == max) {
                updatePlayerScore(players, as.getPlayer(), toAdd);
            }
        }
    }

    protected void updatePlayerScore(LinkedList<Player> players, Player p, int added) {
        for(int i=0;i<players.size();i++) {
            if (players.get(i).equals(p)) {
                players.set(i, players.get(i).addScore(added));
            }
        }
    }

    protected Game updateGameAfterFollower(Board newBoard, Game realGame) {

        LinkedList<Player> players = new LinkedList<>(realGame.getPlayers());

        for(Area a: new HashSet<Area>(newBoard.getUsedFollowers().values())) {
            if (a.getFollowers().isEmpty()) continue;

            if (a.isClosed() && a.getType().equals('C')) {
                int toAddScore = a.getContainedTileCount() * 2;
                updateBestScores(players, a, toAddScore);
                newBoard = newBoard.removeFollowersFromArea(a);
            }
            if (a.isClosed() && a.getType().equals('R')) {
                int toAddScore = a.getContainedTileCount() * 1;
                updateBestScores(players, a, toAddScore);
                newBoard = newBoard.removeFollowersFromArea(a);
            }
            if (a.getType().equals('T')) {
                Coordinate c = a.getCoordinates().asList().get(0);
                int toAddScore = newBoard.tileCountAround(c.getY() /5, c.getX()/5);
                if (toAddScore == 9) {
                    updateBestScores(players, a, toAddScore);
                }
                newBoard = newBoard.removeFollowersFromArea(a);
            }
        }

        boolean finished = realGame.getDeck().get().getRemainingPieceCount() == 0;

        if (finished) {
            // remove remaining followers & add scores
            for(Area a: new HashSet<Area>(newBoard.getUsedFollowers().values())) {
                if (a.getFollowers().isEmpty()) continue;

                if (a.getType().equals('G')) {
                    int toAddScore = newBoard.getClosedNeighborAreasOfTYpe(a, 'C').size() * 2;
                    updateBestScores(players, a, toAddScore);
                }
                if (a.getType().equals('C')) {
                    int toAddScore = a.getContainedTileCount();
                    updateBestScores(players, a, toAddScore);
                }
                if (a.isClosed() && a.getType().equals('R')) {
                    int toAddScore = a.getContainedTileCount() * 1;
                    updateBestScores(players, a, toAddScore);
                }
                if (a.getType().equals('T')) {
                    Coordinate c = a.getCoordinates().asList().get(0);
                    int toAddScore = newBoard.tileCountAround(c.getY() /5, c.getX()/5);
                    updateBestScores(players, a, toAddScore);
                }

            }
        }

        realGame = new Game(
                realGame.getId(),
                ImmutableList.copyOf(players),
                finished ? Optional.empty() : Optional.of(realGame.getCurrentPlayer().get().next(realGame.getPlayers())),
                Optional.of(newBoard),
                finished ? GameState.FINISHED : realGame.getStatus(),
                realGame.getDeck()
        );

        dao.updateGameInfo(realGame);

        return realGame;
    }
}
