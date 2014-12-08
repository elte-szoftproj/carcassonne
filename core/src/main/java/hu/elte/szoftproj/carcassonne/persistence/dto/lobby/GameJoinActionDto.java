package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class GameJoinActionDto {
    String playerName;
    String gameId;

    public GameJoinActionDto() {
    }

    public GameJoinActionDto(String playerName, String gameId) {
        this.playerName = playerName;
        this.gameId = gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getGameId() {
        return gameId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
