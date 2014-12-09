package hu.elte.szoftproj.carcassonne.persistence.dto.game;

import java.util.List;

public class PlayerDetailDto {

    private String name;

    private String type;

    private int score;

    private List<String> followers;

    public PlayerDetailDto() {

    }

    public PlayerDetailDto(String name, String type, int score, List<String> followers) {
        this.name = name;
        this.type = type;
        this.score = score;
        this.followers = followers;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    public List<String> getFollowers() {
        return followers;
    }
}
