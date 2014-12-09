package hu.elte.szoftproj.carcassonne.service.impl.rest;

import hu.elte.szoftproj.carcassonne.config.ApplicationConfig;
import hu.elte.szoftproj.carcassonne.persistence.client.ClientFactory;
import hu.elte.szoftproj.carcassonne.service.GameServiceTest;
import hu.elte.szoftproj.carcassonne.service.impl.GameServiceImplSwitchable;
import hu.elte.szoftproj.carcassonne.service.impl.LobbyServiceImplSwitchable;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigWebContextLoader.class, classes=ApplicationConfig.class)
@WebAppConfiguration
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameServiceImplRestTest extends GameServiceTest {

    @Autowired
    private Server server;

    @Autowired
    private ClientFactory clientFactory;

    @Autowired
    private GameServiceImplSwitchable gameServ;

    @Autowired
    private LobbyServiceImplSwitchable lobbyServ;

    @Before
    public void createServicesAndGame() throws Exception {
        server.start();
        gameServ.getRemote().setClient(clientFactory.getGameClient("http://localhost:8080"));
        lobbyServ.getRemote().setClient(clientFactory.getLobbyClient("http://localhost:8080"));
        gameServ.switchToRemote();
        lobbyServ.switchToRemote();

        this.gameService = gameServ;
        this.lobbyService = lobbyServ;
        createGame();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }
}
