package hu.elte.szoftproj.carcassonne.service;


import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.domain.follower.BasicFollower;
import hu.elte.szoftproj.carcassonne.domain.follower.BigFollower;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public abstract class GameServiceTest {

    protected LobbyService lobbyService;

    protected GameService gameService;

    protected String gameId;

    protected void createGame() {
        // creates a 2 player game
        Game g = lobbyService.createNewGame("testUser1", "test");
        lobbyService.joinGame(g.getId(), "testUser2", false);
        lobbyService.startGame(g.getId());
        this.gameId = g.getId();
    }

    @Test
    public void placeTileTest() {
        Game g = gameService.getGameById("testUser1", gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        g = gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);

        assertThat(g.getBoard().get().getGrid().size(), equalTo(2));
        assertThat(g.getCurrentPlayer().get().getPlayer(), equalTo(current));
        assertThat(g.getCurrentPlayer().get().getAction(), equalTo(GameAction.PLACE_FOLLOWER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadPlayerFailsTest() {
        Game g = gameService.getGameById("testUser1", gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getPlayerByName("testUser2");
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadTileFailsTest() {
        Game g = gameService.getGameById("testUser1", gameId);
        Tile currentTile = StandardTiles.stdCity2nw;
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadPositionFailsTest() {
        Game g = gameService.getGameById("testUser1", gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadStepFailsTest() {
        Game g = gameService.getGameById("testUser1", gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    protected void placeFirstTile() {
        Game g = gameService.getGameById("testUser1", gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    @Test
    public void placeFollowerTest() {

        placeFirstTile();

        Game g = gameService.getGameById("testUser1", gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BigFollower.class).get(0);
        g = gameService.placeFollower(g, current, f, -1, 0, 3, 3);

        assertThat(g.getBoard().get().getGrid().size(), equalTo(2));
        assertThat(g.getCurrentPlayer().get().getPlayer(), not(equalTo(current)));
        assertThat(g.getCurrentPlayer().get().getAction(), equalTo(GameAction.PLACE_TILE));
        assertThat(g.getBoard().get().getUsedFollowers().size(), equalTo(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadPlaceFollowerFailTest() {

        placeFirstTile();

        Game g = gameService.getGameById("testUser1", gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BigFollower.class).get(0);
        gameService.placeFollower(g, current, f, -1, -1, 3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadPlayerFollowerFailTest() {

        placeFirstTile();

        Game g = gameService.getGameById("testUser1", gameId);
        Player current = g.getPlayerByName("testUser2");
        Follower f = current.getFollowers(BigFollower.class).get(0);
        gameService.placeFollower(g, current, f, -1, 0, 3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadFollowerFailTest() {

        placeFirstTile();

        Game g = gameService.getGameById("testUser1", gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Player other = g.getPlayerByName("testUser2");
        Follower f = other.getFollowers(BigFollower.class).get(0);
        gameService.placeFollower(g, current, f, -1, 0, 3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeDoubleFollowerFailTest() {

        placeFirstTile();

        Game g = gameService.getGameById("testUser1", gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BigFollower.class).get(0);
        gameService.placeFollower(g, current, f, -1, 0, 3, 3);
        gameService.placeFollower(g, current, f, -1, 0, 3, 3);
    }

    protected void placeFirstFollower() {
        Game g = gameService.getGameById("testUser1", gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BigFollower.class).get(0);
        g = gameService.placeFollower(g, current, f, -1, 0, 3, 3);
    }

    @Test
    public void placeSecondPartsTest() {
        placeFirstTile();
        placeFirstFollower();

        Game g = gameService.getGameById("testUser2", gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Tile currentTile = g.getDeck().get().peekNext();
        g = gameService.placeTile(g, current, currentTile, Rotation.R0, -1, 1);

        current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BasicFollower.class).get(0);
        g = gameService.placeFollower(g, current, f, -1, 1, 4, 4);

        assertThat(g.getBoard().get().getGrid().size(), equalTo(3));
        assertThat(g.getCurrentPlayer().get().getPlayer(), not(equalTo(current)));
        assertThat(g.getCurrentPlayer().get().getAction(), equalTo(GameAction.PLACE_TILE));
        assertThat(g.getBoard().get().getUsedFollowers().size(), equalTo(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadFollowerSecondPartsFailsTest() {

        placeFirstTile();
        placeFirstFollower();

        Game g = gameService.getGameById("testUser2", gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Tile currentTile = g.getDeck().get().peekNext();
        g = gameService.placeTile(g, current, currentTile, Rotation.R0, -1, 1);

        current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BasicFollower.class).get(0);
        gameService.placeFollower(g, current, f, -1, 1, 1, 1);
    }

    protected Game placeNextTile(String username, int y, int x, Rotation r) {
        return placeNextTile(username, y, x, r, true);
    }

    protected Game placeNextTile(String userName, int y, int x, Rotation r, boolean forceNoFollow) {
        Game g = gameService.getGameById(userName, gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Tile currentTile = g.getDeck().get().peekNext();
        System.out.println("Placing Tile " + currentTile.getName() + " for palyer " + current.getName() + " at [y:" + y + ",x:" + x + "]");
        g = gameService.placeTile(g, current, currentTile, r, y, x);
        if (forceNoFollow && g.getCurrentPlayer().get().getPlayer().equals(current)) {
            g = gameService.dontPlaceFollower(g, current);
        }

        return g;
    }

    protected Game placeNextTileWFollower(String userName, int y, int x, Rotation r, Class followerType, int dy, int dx) {
        Game g = placeNextTile(userName, y, x, r, false);
        Follower f = g.getBoard().get().notUsedFollowers(g.getCurrentPlayer().get().getPlayer().getFollowers(followerType)).get(0);
        Player current = g.getCurrentPlayer().get().getPlayer();
        System.out.println("Placing Follower " + f.getType() + " for player " + current.getName() + " at [y:" + y + ",x:" + x + "]:" + dy + ":" + dx);
        return gameService.placeFollower(g, current, f, y, x, dy, dx);
    }

    @Test
    public void finishCityTest() {
        placeFirstTile();
        placeFirstFollower();

        placeNextTileWFollower("testUser2", -1, 1, Rotation.R0, BasicFollower.class, 4, 4); // 2 city2nw
        placeNextTile("testUser1",  - 1, -1, Rotation.R90); // 1 city2nw
        placeNextTile("testUser2", -2, 1, Rotation.R180); // 2 city1
        Game g = placeNextTile("testUser1", -2, -1, Rotation.R180); // 1 city1

        g.getBoard().get().printAreaGrid();

        assertThat(g.getBoard().get().getUsedFollowers().size(), equalTo(1));
        assertThat(g.getBoard().get().getUsedFollowers().values().asList().get(0).getType(), equalTo('G'));

        ImmutableList<Player> scores = g.getPlayersByScore();
        assertThat(scores.get(0).getScore(), equalTo(12));
        assertThat(scores.get(1).getScore(), equalTo(0));

    }

    @Test
    public void finishMapTest() {
        placeFirstTile();
        placeFirstFollower();

        placeNextTileWFollower("testUser2", -1, 1, Rotation.R0, BasicFollower.class, 4, 4); // 2 city2nw
        placeNextTile("testUser1", -1, -1, Rotation.R90); // 1 city2nw
        placeNextTile("testUser2", -2, 1, Rotation.R180); // 2 city1
        placeNextTile("testUser1", -2, -1, Rotation.R180); // 1 city1

        placeNextTileWFollower("testUser2", -2, 0, Rotation.R0, BasicFollower.class, 2, 2); // 2 cloister

        placeNextTileWFollower("testUser1", 0, 1, Rotation.R0, BasicFollower.class, 2, 2); // 1 road
        placeNextTile("testUser2", 0, -1, Rotation.R270);
        placeNextTile("testUser1", 1, 1, Rotation.R90);
        placeNextTile("testUser2", 1, -1, Rotation.R270);
        Game g = placeNextTile("testUser1", 1, 0, Rotation.R90);

        assertThat(g.getStatus(), equalTo(GameState.FINISHED));
        assertThat(g.getCurrentPlayer(), equalTo(Optional.empty()));

        g.getBoard().get().printAreaGrid();

        for(Area a: g.getBoard().get().getAreas()) {
            if (a.getType().equals('G')) {
                System.out.println("GROUND SIZE: " + a.getContainedTileCount());
            }
        }

        ImmutableList<Player> scores = g.getPlayersByScore();
        assertThat(scores.get(0).getScore(), equalTo(17));
        assertThat(scores.get(0).getName(), equalTo("testUser1"));
        assertThat(scores.get(1).getScore(), equalTo(8));
        assertThat(scores.get(1).getName(), equalTo("testUser2"));
    }
}

