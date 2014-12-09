package hu.elte.szoftproj.carcassonne.persistence.dto.game;

import hu.elte.szoftproj.carcassonne.domain.Rotation;

import java.util.List;

public class BoardItemDto {

    int x;
    int y;
    String t;
    Rotation r;

    List<FollowerItemDto> followers;

    public BoardItemDto() {

    }

    public BoardItemDto(int x, int y, String t, Rotation r, List<FollowerItemDto> followers) {
        this.x = x;
        this.y = y;
        this.t = t;
        this.r = r;
        this.followers = followers;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rotation getR() {
        return r;
    }

    public String getT() {
        return t;
    }

    public List<FollowerItemDto> getFollowers() {
        return followers;
    }
}
