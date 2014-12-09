package hu.elte.szoftproj.carcassonne.service.impl;

import hu.elte.szoftproj.carcassonne.domain.Follower;
import hu.elte.szoftproj.carcassonne.domain.Player;
import hu.elte.szoftproj.carcassonne.domain.follower.BasicFollower;
import hu.elte.szoftproj.carcassonne.domain.follower.BigFollower;
import hu.elte.szoftproj.carcassonne.service.FollowerFactory;

public class FollowerFactoryImpl implements FollowerFactory{
    @Override
    public Follower createFollower(String name, Player p) {
        switch (name) {
            case "basic": return new BasicFollower(p);
            case "big": return new BigFollower(p);
        }

        throw new IllegalArgumentException("Unknown follower type: " + name);
    }
}
