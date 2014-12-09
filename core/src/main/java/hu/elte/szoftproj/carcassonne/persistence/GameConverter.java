package hu.elte.szoftproj.carcassonne.persistence;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.*;
import hu.elte.szoftproj.carcassonne.service.Deck;
import hu.elte.szoftproj.carcassonne.service.FollowerFactory;
import hu.elte.szoftproj.carcassonne.service.impl.deck.DummyDeck;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GameConverter {

    @Autowired
    FollowerFactory followerFactory;

    public CarcassonneGame fromDto(GameDetailDto gameDto) {

        if (!gameDto.getStatus().equals("OK")) {
            throw new IllegalArgumentException(gameDto.getStatus());
        }

        ImmutableList<Player> players = buildPlayerDao(gameDto.getPlayers());

        return new CarcassonneGame(
                gameDto.getGameId(),
                players,
                getPlayer(players, gameDto.getCurrentPlayerName(), gameDto.getCurrentStepType()),
                Optional.of(buildBoard(players, gameDto.getBoard())),
                gameDto.getGameStatus(),
                Optional.of(buildDeck(gameDto.getDeck()))
        );

    }

    private Board buildBoard(ImmutableList<Player> players, BoardDto board) {
        Board b = null;

        for (BoardItemDto item: board.getItems()) {
            if (b == null) {
                b = new Board(StandardTiles.get(item.getT()), item.getR());
            } else {
                b = b.placeTile(StandardTiles.get(item.getT()), item.getR(), item.getY(), item.getX());
                for (FollowerItemDto fi : item.getFollowers()) {

                    Player owner = null;
                    for (Player p: players) {
                        if (p.getName().equals(fi.getPlayerName())) {
                            owner = p;
                            break;
                        }
                    }

                    Follower f = b.notUsedFollowers(owner.getFollowersOfType(fi.getFollowerType())).get(0);
                    b = b.placeFollower(item.getY(), item.getX(), f, fi.getDy(), fi.getDx());
                }

            }
        }

        return b;
    }

    private DummyDeck buildDeck(DeckDto deck) {
        return new DummyDeck(deck.getDeckType(), deck.getRemainingTiles(), deck.getCurrentTile() == null ? null : StandardTiles.get(deck.getCurrentTile()));
    }

    private Optional<CurrentPlayer> getPlayer(ImmutableList<Player> players, String currentPlayerName, GameAction action) {
        for(Player p: players) {
            if (p.getName().equals(currentPlayerName)) {
                return Optional.of(new CurrentPlayer(p, action));
            }
        }
        return Optional.empty();
    }

    private ImmutableList<Player> buildPlayerDao(List<PlayerDetailDto> players) {
        ImmutableList.Builder<Player> pBuilder = new ImmutableList.Builder<>();

        for(PlayerDetailDto pdo : players) {

            pBuilder.add(new Player(
                    pdo.getName(),
                    pdo.getScore(),
                    pdo.getType(),
                    followerFactory,
                    pdo.getFollowers()
            ));
        }

        return pBuilder.build();
    }

    public GameDetailDto fromDao(String playerName, CarcassonneGame gameDao) {

        boolean isCurrentPlayer = gameDao.getCurrentPlayer().isPresent() && gameDao.getCurrentPlayer().get().getPlayer().getName().equals(playerName);

        boolean isPlacingTurn = gameDao.getCurrentPlayer().isPresent() && gameDao.getCurrentPlayer().get().getAction().equals(GameAction.PLACE_TILE);

        try {
            gameDao.getPlayerByName(playerName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("NO_SUCH_PLAYER");
        }

        return new GameDetailDto(
                gameDao.getId(),
                toPlayerDtoList(gameDao.getPlayers()),
                gameDao.getStatus(),
                gameDao.getDeck().isPresent() ? toDeckDto(isCurrentPlayer && isPlacingTurn, gameDao.getDeck().get()) : null,
                gameDao.getBoard().isPresent() ? toBoardDto(gameDao.getBoard().get()) : null,
                gameDao.getCurrentPlayer().isPresent() ? gameDao.getCurrentPlayer().get().getPlayer().getName() : null,
                gameDao.getCurrentPlayer().isPresent() ? gameDao.getCurrentPlayer().get().getAction() : null
        );
    }

    private DeckDto toDeckDto(boolean isCurrentPlayer, Deck deck) {
        return new DeckDto(deck.getName(), deck.getRemainingPieceCount(), isCurrentPlayer ? deck.peekNext().getName() : null);
    }

    private BoardDto toBoardDto(Board board) {
        LinkedList<BoardItemDto> bdos = new LinkedList<>();

        for(Table.Cell<Integer, Integer, Square> cell : board.getGrid().cellSet()) {

            LinkedList<FollowerItemDto> followers = new LinkedList<>();

            for(Table.Cell<Integer, Integer, Follower> cc : cell.getValue().getPlacedFollowers().cellSet()) {
                followers.add(new FollowerItemDto(cc.getValue().getOwner().getName(), cc.getColumnKey(), cc.getRowKey(), cc.getValue().getName()));
            }

            BoardItemDto bdo = new BoardItemDto(cell.getColumnKey(), cell.getRowKey(), cell.getValue().getTile().getName(), cell.getValue().getTileRotation(), followers);

            bdos.add(bdo);
        }

        return new BoardDto(
                bdos
        );
    }

    public List<PlayerDetailDto> toPlayerDtoList(List<Player> players) {
        LinkedList<PlayerDetailDto> pdo = new LinkedList<>();

        for(Player p: players) {
            LinkedList<String> followers = new LinkedList<>();

            for (Follower f : p.getFollowers()) {
                followers.add(f.getName());
            }

            pdo.add(new PlayerDetailDto(p.getName(), p.getType(), p.getScore(), followers));
        }

        return pdo;
    }
}
