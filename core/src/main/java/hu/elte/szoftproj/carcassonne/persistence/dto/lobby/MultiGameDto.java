package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class MultiGameDto {

    List<GameDto> games;

    String status;

    public MultiGameDto() {
    }

    public MultiGameDto(List<GameDto> games, String status) {
        this.games = games;
        this.status = status;
    }

    public List<GameDto> getGames() {
        return games;
    }

    public String getStatus() {
        return status;
    }
}
