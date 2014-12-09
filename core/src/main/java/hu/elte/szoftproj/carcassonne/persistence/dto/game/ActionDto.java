package hu.elte.szoftproj.carcassonne.persistence.dto.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class ActionDto {

    protected String gameId;

    protected String playerName;

    public ActionDto() {

    }

    public ActionDto(String gameId, String playerName) {
        this.gameId = gameId;
        this.playerName = playerName;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }
}
