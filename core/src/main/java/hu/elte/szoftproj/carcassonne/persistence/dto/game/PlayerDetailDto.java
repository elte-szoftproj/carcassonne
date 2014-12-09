package hu.elte.szoftproj.carcassonne.persistence.dto.game;

import hu.elte.szoftproj.carcassonne.domain.PlayerType;

import java.util.List;

public class PlayerDetailDto {

    private String name;

    private PlayerType type;

    private int score;

    private List<String> followers;

    public PlayerDetailDto() {

    }

    public PlayerDetailDto(String name, PlayerType type, int score, List<String> followers) {
        this.name = name;
        this.type = type;
        this.score = score;
        this.followers = followers;
    }

    public String getName() {
        return name;
    }

    public PlayerType getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    public List<String> getFollowers() {
        return followers;
    }
}
