package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class SingleGameDto {
    private GameDto game; // might be null, jersey doesn't sup

    private String gameStatus;

    public SingleGameDto() {
    }

    public SingleGameDto(GameDto game, String status) {
        this.game = game;
        this.gameStatus = status;
    }

    public GameDto getGame() {
        return game;
    }

    public String getGameStatus() {
        return gameStatus;
    }
}
