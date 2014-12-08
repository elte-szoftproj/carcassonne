package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.GameState;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Contains minimal information about a game
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class GameDto {

    private String id;

    private List<PlayerDto> playerList;

    private String deckName;

    private GameState status;

    public GameDto() {
    }

    public GameDto(String id, ImmutableList<PlayerDto> playerList, String deckName, GameState status) {
        this.id = id;
        this.playerList = playerList;
        this.status = status;
        this.deckName = deckName;
    }

    public String getId() {
        return id;
    }

    public List<PlayerDto> getPlayerList() {
        return playerList;
    }

    public GameState getStatus() {
        return status;
    }

    public String getDeckName() {
        return deckName;
    }
}
