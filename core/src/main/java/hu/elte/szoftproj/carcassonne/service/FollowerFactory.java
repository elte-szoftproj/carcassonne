package hu.elte.szoftproj.carcassonne.service;

import hu.elte.szoftproj.carcassonne.domain.Follower;
import hu.elte.szoftproj.carcassonne.domain.Player;

public interface FollowerFactory {

    public Follower createFollower(String name, Player p);

}
