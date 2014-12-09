package hu.elte.szoftproj.carcassonne.persistence.dto.game;

import java.util.List;

public class BoardItemDto {

    int x;
    int y;
    String t;

    List<FollowerItemDto> followers;

    public BoardItemDto() {

    }

    public BoardItemDto(int x, int y, String t, List<FollowerItemDto> followers) {
        this.x = x;
        this.y = y;
        this.t = t;
        this.followers = followers;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getT() {
        return t;
    }

    public List<FollowerItemDto> getFollowers() {
        return followers;
    }
}
