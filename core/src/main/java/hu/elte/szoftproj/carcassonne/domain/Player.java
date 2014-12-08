package hu.elte.szoftproj.carcassonne.domain;


import com.google.common.collect.ImmutableList;

public class Player implements Comparable<Player> {

    private String name;

    private int score;

    PlayerType type;

    ImmutableList<Follower> followers;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.type = PlayerType.HUMAN;
        this.followers = ImmutableList.of();
    }

    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
        this.score = 0;
        this.followers = ImmutableList.of();
    }

    public Player(String name, int score, PlayerType type, ImmutableList<Follower> followers) {
        this.name = name;
        this.score = score;
        this.type = type;
        this.followers = followers;
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

    @Override
    public int compareTo(Player o) {
        return o.score - score;
    }
}
