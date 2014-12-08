package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableList;

public class CurrentPlayer {

    private Player player;
    private GameAction action;

    public CurrentPlayer(Player player, GameAction action) {
        this.player = player;
        this.action = action;
    }

    public CurrentPlayer next(ImmutableList<Player> playerList) {
        int currIndex = playerList.indexOf(player);

        if (currIndex == -1) {
            throw new IllegalArgumentException("Player not found in list");
        }

        if (action == GameAction.PLACE_FOLLOWER) {
            currIndex++;
        }

        if (currIndex >= playerList.size()) {
            currIndex -= playerList.size();
        }

        return new CurrentPlayer(playerList.get(currIndex), action == GameAction.PLACE_FOLLOWER ? GameAction.PLACE_TILE : GameAction.PLACE_FOLLOWER);
    }


}
