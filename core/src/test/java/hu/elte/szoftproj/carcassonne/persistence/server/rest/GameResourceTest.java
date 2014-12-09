package hu.elte.szoftproj.carcassonne.persistence.server.rest;

import hu.elte.szoftproj.carcassonne.config.ApplicationConfig;
import hu.elte.szoftproj.carcassonne.domain.GameAction;
import hu.elte.szoftproj.carcassonne.domain.GameState;
import hu.elte.szoftproj.carcassonne.domain.Rotation;
import hu.elte.szoftproj.carcassonne.persistence.client.ClientFactory;
import hu.elte.szoftproj.carcassonne.persistence.client.GameRestClient;
import hu.elte.szoftproj.carcassonne.persistence.client.LobbyRestClient;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.ActionDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.FollowerDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.GameDetailDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.PlaceDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.GameCreateActionDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.GameIdDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.GameJoinActionDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.SingleGameDto;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigWebContextLoader.class, classes=ApplicationConfig.class)
@WebAppConfiguration
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameResourceTest {

    private final String SERVER_URL = "http://localhost:8080";

    @Autowired
    private Server server;

    @Autowired
    private ClientFactory clientFactory;

    private String gameId;

    private LobbyRestClient lobbyClient;

    private GameRestClient gameClient;

    @Before
    public void startServer() throws Exception {
        server.start();
        lobbyClient = clientFactory.getLobbyClient(SERVER_URL);
        gameClient = clientFactory.getGameClient(SERVER_URL);

        SingleGameDto gc = lobbyClient.createGame(new GameCreateActionDto("testUser1", "test"));
        lobbyClient.joinGame(new GameJoinActionDto("testUser2", gc.getGame().getId()));
        lobbyClient.startGame(new GameIdDto(gc.getGame().getId()));
        gameId = gc.getGame().getId();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void getGameInfoWCurrentPlayerTest() {
        GameDetailDto result = gameClient.getGameById(new ActionDto(gameId, "testUser1"));

        assertThat(result.getStatus(), equalTo("OK"));
        assertThat(result.getGameId(), equalTo(gameId));
        assertThat(result.getPlayers().size(), equalTo(2));
        assertThat(result.getGameStatus(), equalTo(GameState.PLAYING));
        assertThat(result.getCurrentPlayerName(), equalTo("testUser1"));
        assertThat(result.getCurrentStepType(), equalTo(GameAction.PLACE_TILE));
        assertThat(result.getBoard().getItems().size(), equalTo(1));
        assertThat(result.getDeck().getDeckType(), equalTo("test"));
        assertThat(result.getDeck().getRemainingTiles(), equalTo(11));
        assertThat(result.getDeck().getCurrentTile(), equalTo("city3"));
    }

    @Test
    public void getGameInfoWOtherPlayerTest() {
        GameDetailDto result = gameClient.getGameById(new ActionDto(gameId, "testUser2"));

        assertThat(result.getStatus(), equalTo("OK"));
        assertThat(result.getGameId(), equalTo(gameId));
        assertThat(result.getPlayers().size(), equalTo(2));
        assertThat(result.getGameStatus(), equalTo(GameState.PLAYING));
        assertThat(result.getCurrentPlayerName(), equalTo("testUser1"));
        assertThat(result.getCurrentStepType(), equalTo(GameAction.PLACE_TILE));
        assertThat(result.getBoard().getItems().size(), equalTo(1));
        assertThat(result.getDeck().getDeckType(), equalTo("test"));
        assertThat(result.getDeck().getRemainingTiles(), equalTo(11));
        assertThat(result.getDeck().getCurrentTile(), nullValue());
    }

    @Test
    public void getGameInfoWUnknownPlayerTest() {
        GameDetailDto result = gameClient.getGameById(new ActionDto(gameId, "testUser3"));
        assertThat(result.getStatus(), equalTo("ERROR_NO_SUCH_PLAYER"));
    }

    @Test
    public void getGameInfoWUnknownIdTest() {
        GameDetailDto result = gameClient.getGameById(new ActionDto(gameId+"x", "testUser1"));
        assertThat(result.getStatus(), equalTo("ERROR_NO_SUCH_GAME"));
    }

    @Test
    public void placeTileTest() {
        GameDetailDto result = gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 0, Rotation.R180));

        assertThat(result.getStatus(), equalTo("OK"));
        assertThat(result.getGameId(), equalTo(gameId));
        assertThat(result.getPlayers().size(), equalTo(2));
        assertThat(result.getGameStatus(), equalTo(GameState.PLAYING));
        assertThat(result.getCurrentPlayerName(), equalTo("testUser1"));
        assertThat(result.getCurrentStepType(), equalTo(GameAction.PLACE_FOLLOWER));
        assertThat(result.getBoard().getItems().size(), equalTo(2));
        assertThat(result.getDeck().getDeckType(), equalTo("test"));
        assertThat(result.getDeck().getRemainingTiles(), equalTo(10));
        assertThat(result.getDeck().getCurrentTile(), nullValue());
    }

    @Test
    public void placeTileWOtherTest() {
        GameDetailDto result = gameClient.placeTile(new PlaceDto(gameId, "testUser2", -1, 0, Rotation.R180));

        assertThat(result.getStatus(), equalTo("ERROR_NOT_YOUR_TURN"));
    }

    @Test
    public void placeTileBadPositionTest() {
        GameDetailDto result = gameClient.placeTile(new PlaceDto(gameId, "testUser1", -5, 0, Rotation.R180));

        assertThat(result.getStatus(), equalTo("ERROR_NOT_PLACABLE"));
    }

    @Test
    public void placeTileBadRotationTest() {
        GameDetailDto result = gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 0, Rotation.R0));

        assertThat(result.getStatus(), equalTo("ERROR_NOT_PLACABLE"));
    }

    @Test
    public void placeTileBadIdTest() {
        GameDetailDto result = gameClient.placeTile(new PlaceDto(gameId+"z", "testUser1", -1, 0, Rotation.R180));

        assertThat(result.getStatus(), equalTo("ERROR_NO_SUCH_GAME"));
    }

    @Test
    public void placeTileNotPlacingTurnTest() {
        gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 0, Rotation.R180));

        GameDetailDto result = gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 1, Rotation.R0));

        assertThat(result.getStatus(), equalTo("ERROR_NOT_TILE_ACTION"));
    }

    @Test
    public void placeFollowerTest() {
        gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 0, Rotation.R180));
        GameDetailDto result = gameClient.placeFollower(new FollowerDto(gameId, "testUser1", -1, 0, "basic", 2, 2));

        assertThat(result.getStatus(), equalTo("OK"));
        assertThat(result.getGameId(), equalTo(gameId));
        assertThat(result.getPlayers().size(), equalTo(2));
        assertThat(result.getGameStatus(), equalTo(GameState.PLAYING));
        assertThat(result.getCurrentPlayerName(), equalTo("testUser2"));
        assertThat(result.getCurrentStepType(), equalTo(GameAction.PLACE_TILE));
        assertThat(result.getBoard().getItems().size(), equalTo(2));
        assertThat(result.getDeck().getDeckType(), equalTo("test"));
        assertThat(result.getDeck().getRemainingTiles(), equalTo(10));
        assertThat(result.getDeck().getCurrentTile(), nullValue());
    }

    @Test
    public void placeFollowerBadTileTest() {
        gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 0, Rotation.R180));
        GameDetailDto result = gameClient.placeFollower(new FollowerDto(gameId, "testUser1", 0, 0, "basic", 2, 2));

        assertThat(result.getStatus(), equalTo("ERROR_NOT_CURRENT_TILE"));
    }

    @Test
    public void placeFollowerBadTypeTest() {
        gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 0, Rotation.R180));
        GameDetailDto result = gameClient.placeFollower(new FollowerDto(gameId, "testUser1", -1, 0, "404", 2, 2));

        assertThat(result.getStatus(), equalTo("ERROR_NO_SUCH_FOLLOWER"));
    }

    @Test
    public void placeFollowerBadUserTest() {
        gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 0, Rotation.R180));
        GameDetailDto result = gameClient.placeFollower(new FollowerDto(gameId, "testUser2", -1, 0, "basic", 2, 2));

        assertThat(result.getStatus(), equalTo("ERROR_NOT_YOUR_TURN"));
    }

    @Test
    public void placeFollowerNoFollowerTurnTest() {
        GameDetailDto result = gameClient.placeFollower(new FollowerDto(gameId, "testUser1", -1, 0, "basic", 2, 2));

        assertThat(result.getStatus(), equalTo("ERROR_NOT_FOLLOWER_ACTION"));
    }

    @Test
    public void placeFollowerBadIdTest() {
        gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 0, Rotation.R180));
        GameDetailDto result = gameClient.placeFollower(new FollowerDto(gameId+"x", "testUser1", -1, 0, "basic", 2, 2));

        assertThat(result.getStatus(), equalTo("ERROR_NO_SUCH_GAME"));
    }

    @Test
    public void placeFollowerBadPosTest() {
        gameClient.placeTile(new PlaceDto(gameId, "testUser1", -1, 0, Rotation.R180));
        gameClient.placeFollower(new FollowerDto(gameId, "testUser1", -1, 0, "basic", 2, 2));

        gameClient.placeTile(new PlaceDto(gameId, "testUser2", -1, 1, Rotation.R0));
        GameDetailDto result = gameClient.placeFollower(new FollowerDto(gameId, "testUser2", -1, 1, "basic", 1, 1));

        assertThat(result.getStatus(), equalTo("ERROR_CANT_PLACE_THERE"));
    }

}
