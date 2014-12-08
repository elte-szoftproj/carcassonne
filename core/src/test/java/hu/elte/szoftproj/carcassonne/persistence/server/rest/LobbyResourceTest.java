package hu.elte.szoftproj.carcassonne.persistence.server.rest;


import hu.elte.szoftproj.carcassonne.config.ApplicationConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader= AnnotationConfigContextLoader.class, classes=ApplicationConfig.class)
public class LobbyResourceTest {


}
