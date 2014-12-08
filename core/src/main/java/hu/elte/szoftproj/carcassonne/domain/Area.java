package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import hu.elte.szoftproj.carcassonne.domain.follower.BasicFollower;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Area {

    private Character type;

    int openEdgeCount;

    private ImmutableSet<Coordinate> coordinates;

    private ImmutableSet<Follower> followers;

    public Area(Character type) {
        this.type = type;
        followers = ImmutableSet.of();
    }

    public Character getType() {
        return type;
    }

    void setCoordinates(ImmutableSet<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    void setOpenEdgeCount(int openEdgeCount) {
        this.openEdgeCount = openEdgeCount;
    }

    void setFollowers(ImmutableSet<Follower> followers) {
        this.followers = followers;
    }

    public ImmutableSet<Coordinate> getCoordinates() {
        return coordinates;
    }

    public boolean isClosed() {
        return openEdgeCount == 0;
    }

    public int getOpenEdgeCount() {
        return openEdgeCount;
    }

    public ImmutableSet<Follower> getFollowers() {
        return followers;
    }

    public ImmutableList<AreaScore> getScores() {

        HashMap<Player, AreaScore> scores = new HashMap<>();

        for(Follower f: followers) {
            if (f instanceof BasicFollower) {
                if (!scores.containsKey(f.getOwner())) {
                    scores.put(f.getOwner(), new AreaScore(f.getOwner(), 0));
                }
                scores.get(f.getOwner()).addScore(f.getValue());
            }
        }

        LinkedList<AreaScore> l = new LinkedList<>(scores.values());
        Collections.sort(l);

        return ImmutableList.copyOf(l);

    }
}
