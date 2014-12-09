package hu.elte.szoftproj.carcassonne.domain.follower;

import hu.elte.szoftproj.carcassonne.domain.Player;

public class BigFollower extends BasicFollower {

    public BigFollower(Player owner) {
        super(owner);
    }

    @Override
    public int getValue() {
        return 2;
    }

    @Override
    public String getName() {
        return "big";
    }
}
