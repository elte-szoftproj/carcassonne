package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.service.Deck;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Stores information about a game
 */
public class Game {

    private String id;

    private ImmutableList<Player> players;

    private Optional<CurrentPlayer> currentPlayer;

    private Optional<Board> board;

    private GameState status;

    private Optional<Deck> deck;

    // constructor for new games
    public Game(String id, Optional<Deck> d, String initialPlayerName) {
        this.id = id;
        this.players = ImmutableList.of(new Player(initialPlayerName));
        this.currentPlayer = Optional.empty();
        this.board = Optional.empty();
        this.status = GameState.WAITING;
        this.deck = d;
    }

    // constructor for modifications
    public Game(String id, ImmutableList<Player> players, Optional<CurrentPlayer> currentPlayer, Optional<Board> board, GameState status, Optional<Deck> deck) {
        this.id = id;
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.status = status;
        this.deck = deck;
    }

    public boolean isValid() {
        return true;
    }


    public String getId() {
        return id;
    }

    public ImmutableList<Player> getPlayers() {
        return players;
    }

    public Optional<CurrentPlayer> getCurrentPlayer() {
        return currentPlayer;
    }

    public Optional<Board> getBoard() {
        return board;
    }

    public GameState getStatus() {
        return status;
    }

    public boolean hasPlayer(String player) {
        for (Player p: players) {
            if (p.getName().equals(player)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Deck> getDeck() {
        return deck;
    }

    public boolean isPlayersTurn(Player p) {
        return currentPlayer.isPresent() && currentPlayer.get().getPlayer().equals(p);
    }

    public boolean canPlaceTileNow() {
        return currentPlayer.isPresent() && currentPlayer.get().getAction().equals(GameAction.PLACE_TILE);
    }

    public boolean canPlaceFollowerNow() {
        return currentPlayer.isPresent() && currentPlayer.get().getAction().equals(GameAction.PLACE_FOLLOWER);
    }

    public ImmutableList<Player> getPlayersByScore() {
        LinkedList<Player> ll = new LinkedList<>(players);
        Collections.sort(ll);

        return ImmutableList.copyOf(ll);
    }
}
