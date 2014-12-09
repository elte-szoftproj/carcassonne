package hu.elte.szoftproj.carcassonne.service;

import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.domain.GameState;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public abstract class LobbyServiceTest {

    protected LobbyService serviceToTest;

    @Test
    public void testEmptyWaitingListSuccess() {
        List<CarcassonneGame> result = serviceToTest.listWaitingGames();

        assertThat(result.size(), equalTo(0));
    }

    @Test
    public void testEmptyActiveListSuccess() {
        List<CarcassonneGame> result = serviceToTest.listActiveGames();

        assertThat(result.size(), equalTo(0));
    }

    @Test
    public void testCreateSuccess() {
        CarcassonneGame g = serviceToTest.createNewGame("testPlayer", "basic");

        assertThat(g.getStatus(), equalTo(GameState.WAITING));
        assertThat(g.getDeck().get().getName(), equalTo("basic"));
        assertThat(g.getCurrentPlayer(), equalTo(Optional.empty()));
        assertThat(g.getPlayers().size(), equalTo(1));
        assertThat(g.getPlayers().get(0).getName(), equalTo("testPlayer"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCreateBadDeck() {
        serviceToTest.createNewGame("testPlayer", "404");
    }

    @Test
    public void testJoinSuccess() {
        CarcassonneGame g = serviceToTest.createNewGame("testPlayer", "basic");
        CarcassonneGame gJoin = serviceToTest.joinGame(g.getId(), "testPlayer2", false);

        assertThat(gJoin.getStatus(), equalTo(GameState.WAITING));
        assertThat(gJoin.getDeck().get().getName(), equalTo("basic"));
        assertThat(gJoin.getPlayers().size(), equalTo(2));
        assertThat(gJoin.getPlayers().get(0).getName(), equalTo("testPlayer"));
        assertThat(gJoin.getPlayers().get(1).getName(), equalTo("testPlayer2"));
        assertThat(gJoin.getId(), equalTo(g.getId()));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testJoinFailSame() {
        CarcassonneGame g = serviceToTest.createNewGame("testPlayer", "basic");
        serviceToTest.joinGame(g.getId(), "testPlayer", false);
    }

    @Test
    public void testStartSuccess() {
        CarcassonneGame g = serviceToTest.createNewGame("testPlayer", "basic");
        serviceToTest.joinGame(g.getId(), "testPlayer2", false);
        CarcassonneGame gStart = serviceToTest.startGame(g.getId());

        assertThat(gStart.getStatus(), equalTo(GameState.PLAYING));
        assertThat(gStart.getDeck().get().getName(), equalTo("basic"));
        assertThat(gStart.getPlayers().size(), equalTo(2));
        assertThat(gStart.getPlayers().get(0).getName(), equalTo("testPlayer"));
        assertThat(gStart.getPlayers().get(1).getName(), equalTo("testPlayer2"));
        assertThat(gStart.getId(), equalTo(g.getId()));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testStartFailWithOnePlayer() {
        CarcassonneGame g = serviceToTest.createNewGame("testPlayer", "basic");
        serviceToTest.startGame(g.getId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testStartFailDouble() {
        CarcassonneGame g = serviceToTest.createNewGame("testPlayer", "basic");
        serviceToTest.joinGame(g.getId(), "testPlayer2", false);
        serviceToTest.startGame(g.getId());
        serviceToTest.startGame(g.getId());
    }
}
