package hu.elte.szoftproj.carcassonne.persistence.dto.game;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.elte.szoftproj.carcassonne.domain.GameAction;
import hu.elte.szoftproj.carcassonne.domain.GameState;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class GameDetailDto {

    String gameId;

    List<PlayerDetailDto> players;

    GameState gameStatus;

    DeckDto deck;

    BoardDto board;

    String currentPlayerName;

    GameAction currentStepType;

    String status;

    public GameDetailDto(String gameId, List<PlayerDetailDto> players, GameState gameStatus, DeckDto deck, BoardDto board, String currentPlayerName, GameAction currentStepType) {
        this.gameId = gameId;
        this.players = players;
        this.gameStatus = gameStatus;
        this.deck = deck;
        this.board = board;
        this.currentPlayerName = currentPlayerName;
        this.currentStepType = currentStepType;
        this.status = "OK";
    }

    public GameDetailDto(String status) {
        this.status = status;
    }

    public GameDetailDto() {
    }

    public String getGameId() {
        return gameId;
    }

    public List<PlayerDetailDto> getPlayers() {
        return players;
    }

    public GameState getGameStatus() {
        return gameStatus;
    }

    public DeckDto getDeck() {
        return deck;
    }

    public BoardDto getBoard() {
        return board;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public GameAction getCurrentStepType() {
        return currentStepType;
    }

    public String getStatus() {
        return status;
    }
}
