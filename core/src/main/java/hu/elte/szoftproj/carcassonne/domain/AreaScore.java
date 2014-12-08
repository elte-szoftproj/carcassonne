package hu.elte.szoftproj.carcassonne.domain;

public class AreaScore implements Comparable<AreaScore>{


    Player player;

    int score;

    public AreaScore(Player player, int score) {
        this.player = player;
        this.score = score;
    }

    void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int s) {
        score += s;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public int compareTo(AreaScore o) {
        return o.score - score;
    }
}
