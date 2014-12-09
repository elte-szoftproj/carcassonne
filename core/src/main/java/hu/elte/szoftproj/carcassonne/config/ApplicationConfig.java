package hu.elte.szoftproj.carcassonne.config;

import hu.elte.szoftproj.carcassonne.persistence.GameConverter;
import hu.elte.szoftproj.carcassonne.persistence.GameDao;
import hu.elte.szoftproj.carcassonne.persistence.client.ClientFactory;
import hu.elte.szoftproj.carcassonne.persistence.client.impl.ClientFactoryImpl;
import hu.elte.szoftproj.carcassonne.persistence.impl.GameDaoMemoryImpl;
import hu.elte.szoftproj.carcassonne.persistence.server.ServerFactory;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import hu.elte.szoftproj.carcassonne.service.FollowerFactory;
import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import hu.elte.szoftproj.carcassonne.service.impl.*;
import hu.elte.szoftproj.carcassonne.service.impl.rest.GameServiceImplRest;
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
    public GameServiceImplRest getGameServiceImplRest() {
        return new GameServiceImplRest();
    }

    @Bean
    public GameServiceImplWDao getGameServiceImplWDao() {
        return new GameServiceImplWDao();
    }

    @Bean
    public GameServiceImplSwitchable getGameServiceImplSwitchable() {
        return new GameServiceImplSwitchable(getGameServiceImplWDao(), getGameServiceImplRest(), false);
    }

    @Bean
    @Primary
    public GameService getGameService() {
        switch (System.getProperty("server.type","desktop")) {
            case "dedicated":
                return getGameServiceImplWDao();
            case "desktop":
            default:
                return getGameServiceImplSwitchable();
        }
    }

    @Bean
    public GameConverter getGameConverter() {
        return new GameConverter();
    }

    @Bean
    public FollowerFactory getFollowerFactory() {
        return new FollowerFactoryImpl();
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
