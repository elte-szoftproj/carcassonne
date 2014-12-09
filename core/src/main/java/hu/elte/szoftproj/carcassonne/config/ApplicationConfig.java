package hu.elte.szoftproj.carcassonne.config;

import hu.elte.szoftproj.carcassonne.persistence.GameConverter;
import hu.elte.szoftproj.carcassonne.persistence.GameDao;
import hu.elte.szoftproj.carcassonne.persistence.client.ClientFactory;
import hu.elte.szoftproj.carcassonne.persistence.client.impl.ClientFactoryImpl;
import hu.elte.szoftproj.carcassonne.persistence.impl.GameDaoMemoryImpl;
import hu.elte.szoftproj.carcassonne.persistence.server.ServerFactory;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import hu.elte.szoftproj.carcassonne.service.impl.DeckFactoryImpl;
import hu.elte.szoftproj.carcassonne.service.impl.GameServiceImplWDao;
import hu.elte.szoftproj.carcassonne.service.impl.LobbyServiceImplSwitchable;
import hu.elte.szoftproj.carcassonne.service.impl.LobbyServiceImplWDao;
import hu.elte.szoftproj.carcassonne.service.impl.rest.LobbyServiceImplRest;
import org.eclipse.jetty.server.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("hu.elte.szoftproj.carcassonne")
public class ApplicationConfig {

    @Bean
    public ClientFactory getClientFactory() {
        return new ClientFactoryImpl();
    }

    @Bean
    public GameDao getGameDao() {
        return new GameDaoMemoryImpl();
    }

    @Bean
    public GameServiceImplWDao getGameServiceImplWDao() {
        return new GameServiceImplWDao();
    }

    @Bean
    public GameConverter getGameConverter() {
        return new GameConverter();
    }

    @Bean
    public LobbyServiceImplWDao getLobbyServiceImplWDao() {
        return new LobbyServiceImplWDao();
    }

    @Bean
    public LobbyServiceImplRest getLobbyServiceImplRest() {
        return new LobbyServiceImplRest();
    }

    @Bean
    @Primary
    public LobbyService getLobbyService() {

        switch (System.getProperty("server.type","desktop")) {
            case "dedicated":
                return getLobbyServiceImplWDao();
            case "desktop":
            default:
                return getLobbyServiceImplSwitchable();
        }
    }

    @Bean
    public LobbyServiceImplSwitchable getLobbyServiceImplSwitchable() {
        return new LobbyServiceImplSwitchable(getLobbyServiceImplWDao(), getLobbyServiceImplRest(), false);
    }

    @Bean
    public DeckFactory getDeckFactory() {
        return new DeckFactoryImpl();
    }

    @Bean
    public ServerFactory getServerFactory() {
        return new ServerFactory();
    }

    @Bean
    public Server getRestServer() throws Exception {
        return getServerFactory().getServer(Integer.parseInt(System.getProperty("server.port", ServerFactory.DEFAULT_PORT + "")));
    }


}
