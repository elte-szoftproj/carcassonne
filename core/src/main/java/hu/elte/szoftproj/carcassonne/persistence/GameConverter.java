package hu.elte.szoftproj.carcassonne.persistence;

import com.google.common.collect.Table;
import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.*;
import hu.elte.szoftproj.carcassonne.service.Deck;

import java.util.LinkedList;
import java.util.List;

public class GameConverter {

    public Game fromDto(GameDetailDto gameDto) {
        return null;
    }

    public GameDetailDto fromDao(String playerName, Game gameDao) {

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
                followers.add(new FollowerItemDto(cc.getValue().getOwner().getName(), cc.getColumnKey(), cc.getRowKey(), cc.getValue().getType()));
            }

            BoardItemDto bdo = new BoardItemDto(cell.getColumnKey(), cell.getRowKey(), cell.getValue().getTile().getName(), followers);

            bdos.push(bdo);
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

            pdo.add(new PlayerDetailDto(p.getName(), p.getType().toString(), p.getScore(), followers));
        }

        return pdo;
    }
}
