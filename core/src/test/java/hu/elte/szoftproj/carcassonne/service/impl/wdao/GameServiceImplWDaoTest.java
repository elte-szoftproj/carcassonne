package hu.elte.szoftproj.carcassonne.service.impl.wdao;

import hu.elte.szoftproj.carcassonne.config.ApplicationConfig;
import hu.elte.szoftproj.carcassonne.service.GameServiceTest;
import hu.elte.szoftproj.carcassonne.service.impl.GameServiceImplWDao;
import hu.elte.szoftproj.carcassonne.service.impl.LobbyServiceImplWDao;
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
public class GameServiceImplWDaoTest extends GameServiceTest {

    @Autowired
    GameServiceImplWDao gameServ;

    @Autowired
    LobbyServiceImplWDao lobbyServ;

    @Before
    public void createServicesAndGame() {
        this.gameService = gameServ;
        this.lobbyService = lobbyServ;
        createGame();
    }

}
