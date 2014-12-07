package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableTable;
import hu.elte.szoftproj.carcassonne.service.Deck;

import java.util.List;
import java.util.Optional;

/**
 * Stores information about a game
 */
public class Game {

    private String id;

    private ImmutableList<Player> players;

    private Optional<Player> currentPlayer;

    Optional<ImmutableTable<Integer, Integer, Square>> board;

    private GameState status;

    private Deck deck;

    // constructor for new games
    public Game(String id, Deck d, String initialPlayerName) {
        this.id = id;
        this.players = ImmutableList.of(new Player(initialPlayerName));
        this.currentPlayer = Optional.empty();
        this.board = Optional.empty();
        this.status = GameState.WAITING;
        this.deck = d;
    }

    // constructor for modifications
    public Game(String id, ImmutableList<Player> players, Optional<Player> currentPlayer, Optional<ImmutableTable<Integer, Integer, Square>> board, GameState status, Deck deck) {
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

    public Optional<Player> getCurrentPlayer() {
        return currentPlayer;
    }

    public Optional<ImmutableTable<Integer, Integer, Square>> getBoard() {
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

    public Deck getDeck() {
        return deck;
    }
}
