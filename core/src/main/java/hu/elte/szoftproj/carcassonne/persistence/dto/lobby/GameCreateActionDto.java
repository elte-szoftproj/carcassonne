package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class GameCreateActionDto {
    String playerName;
    String deckName;

    public GameCreateActionDto(){
    }

    public GameCreateActionDto(String playerName, String boardName) {
        this.playerName = playerName;
        this.deckName = boardName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
