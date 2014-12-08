package hu.elte.szoftproj.carcassonne.service;


import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.domain.follower.BasicFollower;
import hu.elte.szoftproj.carcassonne.domain.follower.BigFollower;
import org.junit.Test;

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
        Game g = gameService.getGameById(gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        g = gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);

        assertThat(g.getBoard().get().getGrid().size(), equalTo(2));
        assertThat(g.getCurrentPlayer().get().getPlayer(), equalTo(current));
        assertThat(g.getCurrentPlayer().get().getAction(), equalTo(GameAction.PLACE_FOLLOWER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadPlayerFailsTest() {
        Game g = gameService.getGameById(gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getPlayerByName("testUser2");
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadTileFailsTest() {
        Game g = gameService.getGameById(gameId);
        Tile currentTile = StandardTiles.stdCity2nw;
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadPositionFailsTest() {
        Game g = gameService.getGameById(gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadStepFailsTest() {
        Game g = gameService.getGameById(gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    protected void placeFirstTile() {
        Game g = gameService.getGameById(gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    @Test
    public void placeFollowerTest() {

        placeFirstTile();

        Game g = gameService.getGameById(gameId);
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

        Game g = gameService.getGameById(gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BigFollower.class).get(0);
        gameService.placeFollower(g, current, f, -1, -1, 3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadPlayerFollowerFailTest() {

        placeFirstTile();

        Game g = gameService.getGameById(gameId);
        Player current = g.getPlayerByName("testUser2");
        Follower f = current.getFollowers(BigFollower.class).get(0);
        gameService.placeFollower(g, current, f, -1, 0, 3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadFollowerFailTest() {

        placeFirstTile();

        Game g = gameService.getGameById(gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Player other = g.getPlayerByName("testUser2");
        Follower f = other.getFollowers(BigFollower.class).get(0);
        gameService.placeFollower(g, current, f, -1, 0, 3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeDoubleFollowerFailTest() {

        placeFirstTile();

        Game g = gameService.getGameById(gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BigFollower.class).get(0);
        gameService.placeFollower(g, current, f, -1, 0, 3, 3);
        gameService.placeFollower(g, current, f, -1, 0, 3, 3);
    }

    protected void placeFirstFollower() {
        Game g = gameService.getGameById(gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BigFollower.class).get(0);
        g = gameService.placeFollower(g, current, f, -1, 0, 3, 3);
    }

    @Test
    public void placeSecondPartsTest() {
        placeFirstTile();
        placeFirstFollower();

        Game g = gameService.getGameById(gameId);
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

        Game g = gameService.getGameById(gameId);
        Player current = g.getCurrentPlayer().get().getPlayer();
        Tile currentTile = g.getDeck().get().peekNext();
        System.out.println(currentTile.getName());
        g = gameService.placeTile(g, current, currentTile, Rotation.R0, -1, 1);

        current = g.getCurrentPlayer().get().getPlayer();
        Follower f = current.getFollowers(BasicFollower.class).get(0);
        g = gameService.placeFollower(g, current, f, -1, 1, 1, 1);
    }

}
