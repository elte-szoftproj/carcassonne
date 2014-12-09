package hu.elte.szoftproj.carcassonne.domain.follower;

import hu.elte.szoftproj.carcassonne.domain.Player;

public class BasicFollower extends AbstractFollower {

    public BasicFollower(Player owner) {
        super(owner);
    }

    @Override
    public int getValue() {
        return 1;
    }

    @Override
    public String getType() {
        return "ownership";
    }

    @Override
    public String getName() {
        return "basic";
    }

}
