package hu.elte.szoftproj.carcassonne.persistence.dto.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.elte.szoftproj.carcassonne.domain.Rotation;

import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class PlaceDto extends ActionDto {

    int y;

    int x;

    Rotation rot;

    public PlaceDto() {

    }

    public PlaceDto(String gameId, String playerName, int y, int x, Rotation rot) {
        super(gameId, playerName);
        this.y = y;
        this.x = x;
        this.rot = rot;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Rotation getRot() {
        return rot;
    }
}
