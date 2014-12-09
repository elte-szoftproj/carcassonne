package hu.elte.szoftproj.carcassonne.persistence.dto.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class FollowerDto extends ActionDto {

    protected int y;

    protected int x;

    protected String followerType;

    protected int dy;

    protected int dx;

    public FollowerDto() {

    }

    public FollowerDto(String gameId, String playerName, int y, int x, String followerId, int dy, int dx) {
        super(gameId, playerName);
        this.y = y;
        this.x = x;
        this.followerType = followerId;
        this.dy = dy;
        this.dx = dx;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getFollowerType() {
        return followerType;
    }

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }
}
