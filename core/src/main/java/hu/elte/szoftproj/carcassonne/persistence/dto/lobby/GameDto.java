package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.GameState;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contains minimal information about a game
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class GameDto {

    private String id;

    private ImmutableList<PlayerDto> playerList;

    private GameState status;

    public GameDto(String id, ImmutableList<PlayerDto> playerList, GameState status) {
        this.id = id;
        this.playerList = playerList;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public ImmutableList<PlayerDto> getPlayerList() {
        return playerList;
    }

    public GameState getStatus() {
        return status;
    }
}
