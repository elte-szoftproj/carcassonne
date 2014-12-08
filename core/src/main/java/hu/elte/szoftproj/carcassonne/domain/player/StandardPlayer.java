package hu.elte.szoftproj.carcassonne.domain.player;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.Follower;
import hu.elte.szoftproj.carcassonne.domain.Player;
import hu.elte.szoftproj.carcassonne.domain.PlayerType;
import hu.elte.szoftproj.carcassonne.domain.follower.BasicFollower;
import hu.elte.szoftproj.carcassonne.domain.follower.BigFollower;

public class StandardPlayer extends Player {

    public StandardPlayer(String name, PlayerType type) {
        super(name, 0, type, null);
        this.followers = buildFollowers(this);
    }

    private static ImmutableList<Follower> buildFollowers(Player p) {
        ImmutableList.Builder<Follower> followers = new ImmutableList.Builder<>();

        for(int i=0;i<7;i++) {
            followers.add(new BasicFollower(p));
        }
        followers.add(new BigFollower(p));

        return followers.build();
    }
}
