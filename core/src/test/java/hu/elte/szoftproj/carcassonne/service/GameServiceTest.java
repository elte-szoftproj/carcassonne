package hu.elte.szoftproj.carcassonne.service;


import hu.elte.szoftproj.carcassonne.domain.*;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
    public void placeBadPlayerFails() {
        Game g = gameService.getGameById(gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getPlayerByName("testUser2");
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadTileFails() {
        Game g = gameService.getGameById(gameId);
        Tile currentTile = StandardTiles.stdCity2nw;
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadPositionFails() {
        Game g = gameService.getGameById(gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBadStepFails() {
        Game g = gameService.getGameById(gameId);
        Tile currentTile = g.getDeck().get().peekNext();
        Player current = g.getCurrentPlayer().get().getPlayer();
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
        gameService.placeTile(g, current, currentTile, Rotation.R180, -1, 0);
    }

    

}