package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class GameJoinActionDto {
    String playerName;
    String gameId;

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
