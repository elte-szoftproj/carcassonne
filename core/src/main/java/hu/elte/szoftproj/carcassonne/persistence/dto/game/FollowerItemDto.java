package hu.elte.szoftproj.carcassonne.persistence.dto.game;

public class FollowerItemDto {

    String playerName;

    int dx;
    int dy;

    String followerType;

    public FollowerItemDto() {

    }

    public FollowerItemDto(String playerName, int dx, int dy, String followerType) {
        this.playerName = playerName;
        this.dx = dx;
        this.dy = dy;
        this.followerType = followerType;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getFollowerType() {
        return followerType;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
