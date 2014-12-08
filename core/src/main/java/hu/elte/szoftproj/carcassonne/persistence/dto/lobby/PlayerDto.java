package hu.elte.szoftproj.carcassonne.persistence.dto.lobby;


import hu.elte.szoftproj.carcassonne.domain.PlayerType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contains information about a player (currently only his name)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class PlayerDto {

    private String name;

    private PlayerType type;

    public PlayerDto() {
    }


    public PlayerDto(String name, PlayerType type) {
        this.name = name;
        this.type=type;
    }

    public String getName() {
        return name;
    }
}
