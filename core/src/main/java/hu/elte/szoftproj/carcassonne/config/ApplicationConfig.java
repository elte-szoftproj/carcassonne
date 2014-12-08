package hu.elte.szoftproj.carcassonne.config;

import hu.elte.szoftproj.carcassonne.persistence.GameDao;
import hu.elte.szoftproj.carcassonne.persistence.impl.GameDaoMemoryImpl;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import hu.elte.szoftproj.carcassonne.service.impl.DeckFactoryImpl;
import hu.elte.szoftproj.carcassonne.service.impl.LobbyServiceImplSwitchable;
import hu.elte.szoftproj.carcassonne.service.impl.LobbyServiceImplWDao;
import hu.elte.szoftproj.carcassonne.service.impl.rest.LobbyServiceImplRest;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("hu.elte.szoftproj.carcassonne")
public class ApplicationConfig {


    @Bean
    public GameDao getGameDao() {
        return new GameDaoMemoryImpl();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public LobbyService getLobbyService() {

        switch (System.getProperty("server.type","desktop")) {
            case "dedicated":
                return new LobbyServiceImplWDao();
            case "desktop":
            default:
                return new LobbyServiceImplSwitchable(new LobbyServiceImplWDao(), new LobbyServiceImplRest(), false);
        }
    }

    @Bean
    public DeckFactory getDeckFactory() {
        return new DeckFactoryImpl();
    }

}
