package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class GameCreateActionDto {
    String playerName;
    String boardName;

    public GameCreateActionDto(){
    }

    public GameCreateActionDto(String playerName, String boardName) {
        this.playerName = playerName;
        this.boardName = boardName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
