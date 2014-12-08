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

    public PlayerType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        Player oth = (Player)obj;
        return (oth.getName() == null && this.name == null) || oth.getName().equals(getName());
    }
}
