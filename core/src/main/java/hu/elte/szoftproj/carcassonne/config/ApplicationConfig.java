package hu.elte.szoftproj.carcassonne.config;

import hu.elte.szoftproj.carcassonne.domain.Game;
import hu.elte.szoftproj.carcassonne.persistence.LobbyDao;
import hu.elte.szoftproj.carcassonne.persistence.server.LobbyDaoLocalImpl;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import hu.elte.szoftproj.carcassonne.service.impl.DeckFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan("hu.elte.szoftproj.carcassonne")
public class ApplicationConfig {


    @Bean
    public LobbyDao getLobbyDao() {
        // TODO: server specific implementation!
        return new LobbyDaoLocalImpl();
    }

    @Bean
    public DeckFactory getDeckFactory() {
        return new DeckFactoryImpl();
    }

}
