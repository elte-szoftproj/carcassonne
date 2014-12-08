package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class SingleGameDto {
    private GameDto game; // might be null, jersey doesn't sup

    private String status;

    public SingleGameDto() {
    }

    public SingleGameDto(GameDto game, String status) {
        this.game = game;
        this.status = status;
    }

    public GameDto getGame() {
        return game;
    }

    public String getStatus() {
        return status;
    }
}
