package hu.elte.szoftproj.carcassonne.config;

import hu.elte.szoftproj.carcassonne.persistence.LobbyDao;
import hu.elte.szoftproj.carcassonne.persistence.impl.LobbyDaoMemoryImpl;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import hu.elte.szoftproj.carcassonne.service.impl.DeckFactoryImpl;
import hu.elte.szoftproj.carcassonne.service.impl.LobbyServiceImplWDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("hu.elte.szoftproj.carcassonne")
public class ApplicationConfig {


    @Bean
    public LobbyDao getLobbyDao() {
        return new LobbyDaoMemoryImpl();
    }

    @Bean
    public LobbyService getLobbyService() {
        // TODO: server specific implementation!
        return new LobbyServiceImplWDao();
    }

    @Bean
    public DeckFactory getDeckFactory() {
        return new DeckFactoryImpl();
    }

}
