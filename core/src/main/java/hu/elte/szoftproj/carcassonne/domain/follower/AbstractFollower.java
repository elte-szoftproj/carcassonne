package hu.elte.szoftproj.carcassonne.domain.follower;

import hu.elte.szoftproj.carcassonne.domain.Area;
import hu.elte.szoftproj.carcassonne.domain.Follower;
import hu.elte.szoftproj.carcassonne.domain.Player;

/**
 * Created by Zsolt on 2014.12.08..
 */
public abstract class AbstractFollower implements Follower {

    private Player owner;

    public AbstractFollower(Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public boolean canBePlacedAt(Area area) {
        return (!area.isClosed() && area.getFollowers().isEmpty()) || area.getType().equals('T');
    }
}
