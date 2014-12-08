package hu.elte.szoftproj.carcassonne.service.impl.wdao;

import hu.elte.szoftproj.carcassonne.config.ApplicationConfig;
import hu.elte.szoftproj.carcassonne.service.LobbyServiceTest;
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
public class LobbyServiceImplWDaoTest extends LobbyServiceTest {

    @Autowired
    private LobbyServiceImplWDao service;

    @Before
    public void initLobby() throws Exception {
        serviceToTest = service;
    }

}
