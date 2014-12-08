package hu.elte.szoftproj.carcassonne.persistence.server.rest;


import hu.elte.szoftproj.carcassonne.config.ApplicationConfig;
import hu.elte.szoftproj.carcassonne.domain.GameState;
import hu.elte.szoftproj.carcassonne.persistence.client.impl.LobbyRestClientImpl;
import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.*;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigWebContextLoader.class, classes=ApplicationConfig.class)
@WebAppConfiguration
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LobbyResourceTest {

    @Autowired
    private Server server;

    @Before
    public void startServer() throws Exception {
        server.start();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void testEmptyWaitingListSuccess() {
        MultiGameDto result = new LobbyRestClientImpl("http://localhost:8080").getWaitingGameList();

        assertThat(result.getStatus(), equalTo("OK"));
        assertThat(result.getGames().size(), equalTo(0));
    }

    @Test
    public void testEmptyActiveListSuccess() {
        MultiGameDto result = new LobbyRestClientImpl("http://localhost:8080").getActiveGameList();

        assertThat(result.getStatus(), equalTo("OK"));
        assertThat(result.getGames().size(), equalTo(0));
    }



    @Test
    public void testCreateSuccess() {
        SingleGameDto result = new LobbyRestClientImpl("http://localhost:8080").createGame(new GameCreateActionDto("testPlayer", "basic"));

        assertThat(result.getStatus(), equalTo("OK"));
        assertThat(result.getGame().getDeckName(), equalTo("basic"));
        assertThat(result.getGame().getPlayerList().size(), equalTo(1));
        assertThat(result.getGame().getPlayerList().get(0).getName(), equalTo("testPlayer"));
        assertThat(result.getGame().getStatus(), equalTo(GameState.WAITING));

        MultiGameDto listResult = new LobbyRestClientImpl("http://localhost:8080").getWaitingGameList();

        assertThat(listResult.getStatus(), equalTo("OK"));
        assertThat(listResult.getGames().size(), equalTo(1));
        assertThat(listResult.getGames().get(0).getId(), equalTo(result.getGame().getId()));
    }

    @Test
    public void testCreateBadDeck() {
        SingleGameDto result = new LobbyRestClientImpl("http://localhost:8080").createGame(new GameCreateActionDto("testPlayer", "404"));

        assertThat(result.getStatus(), equalTo("ERROR_NO_SUCH_DECK"));
    }

    @Test
    public void testJoinSuccess() {
        SingleGameDto result = new LobbyRestClientImpl("http://localhost:8080").createGame(new GameCreateActionDto("testPlayer", "basic"));
        SingleGameDto joinResult = new LobbyRestClientImpl("http://localhost:8080").joinGame(new GameJoinActionDto("testPlayer2", result.getGame().getId()));

        assertThat(joinResult.getStatus(), equalTo("OK"));
        assertThat(joinResult.getGame().getPlayerList().size(), equalTo(2));
        assertThat(joinResult.getGame().getId(), equalTo(result.getGame().getId()));
        assertThat(joinResult.getGame().getStatus(), equalTo(GameState.WAITING));
    }

    @Test
    public void testJoinAiSuccess() {
        SingleGameDto result = new LobbyRestClientImpl("http://localhost:8080").createGame(new GameCreateActionDto("testPlayer", "basic"));
        SingleGameDto joinResult = new LobbyRestClientImpl("http://localhost:8080").joinGameWithAi(new GameJoinActionDto("testPlayer2", result.getGame().getId()));

        assertThat(joinResult.getStatus(), equalTo("OK"));
        assertThat(joinResult.getGame().getPlayerList().size(), equalTo(2));
        assertThat(joinResult.getGame().getId(), equalTo(result.getGame().getId()));
        assertThat(joinResult.getGame().getStatus(), equalTo(GameState.WAITING));
    }

    @Test
    public void testJoinFailSame() {
        SingleGameDto result = new LobbyRestClientImpl("http://localhost:8080").createGame(new GameCreateActionDto("testPlayer", "basic"));
        SingleGameDto joinResult = new LobbyRestClientImpl("http://localhost:8080").joinGame(new GameJoinActionDto("testPlayer", result.getGame().getId()));

        assertThat(joinResult.getStatus(), equalTo("ERROR_ALREADY_HAS_PLAYER"));
    }

    @Test
    public void testStartSuccess() {
        SingleGameDto result = new LobbyRestClientImpl("http://localhost:8080").createGame(new GameCreateActionDto("testPlayer", "basic"));
        new LobbyRestClientImpl("http://localhost:8080").joinGameWithAi(new GameJoinActionDto("testPlayer2", result.getGame().getId()));
        SingleGameDto startResult = new LobbyRestClientImpl("http://localhost:8080").startGame(new GameIdDto(result.getGame().getId()));

        assertThat(startResult.getStatus(), equalTo("OK"));
        assertThat(startResult.getGame().getPlayerList().size(), equalTo(2));
        assertThat(startResult.getGame().getId(), equalTo(result.getGame().getId()));
        assertThat(startResult.getGame().getStatus(), equalTo(GameState.PLAYING));
    }

    @Test
    public void testStartFailWithOnePlayer() {
        SingleGameDto result = new LobbyRestClientImpl("http://localhost:8080").createGame(new GameCreateActionDto("testPlayer", "basic"));
        SingleGameDto startResult = new LobbyRestClientImpl("http://localhost:8080").startGame(new GameIdDto(result.getGame().getId()));

        assertThat(startResult.getStatus(), equalTo("ERROR_TOO_FEW_PLAYERS"));
    }

    @Test
    public void testStartFailDouble() {
        SingleGameDto result = new LobbyRestClientImpl("http://localhost:8080").createGame(new GameCreateActionDto("testPlayer", "basic"));
        new LobbyRestClientImpl("http://localhost:8080").joinGameWithAi(new GameJoinActionDto("testPlayer2", result.getGame().getId()));
        new LobbyRestClientImpl("http://localhost:8080").startGame(new GameIdDto(result.getGame().getId()));
        SingleGameDto startResult = new LobbyRestClientImpl("http://localhost:8080").startGame(new GameIdDto(result.getGame().getId()));

        assertThat(startResult.getStatus(), equalTo("ERROR_BAD_STATUS"));
    }
}
