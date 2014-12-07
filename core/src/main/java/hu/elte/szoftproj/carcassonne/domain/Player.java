package hu.elte.szoftproj.carcassonne.domain;


public class Player {

    private String name;

    private int score;

    PlayerType type;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.type = PlayerType.HUMAN;
    }

    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
    }

    public Player(String name, int score, PlayerType type) {
        this.name = name;
        this.score = score;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public PlayerType getType() {
        return type;
    }
}
