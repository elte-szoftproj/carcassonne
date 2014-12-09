package hu.elte.szoftproj.carcassonne;

import hu.elte.szoftproj.carcassonne.persistence.client.ClientFactory;
import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import hu.elte.szoftproj.carcassonne.service.impl.GameServiceImplSwitchable;
import hu.elte.szoftproj.carcassonne.service.impl.LobbyServiceImplSwitchable;
import org.eclipse.jetty.server.Server;

/**
 * Created by Zsolt on 2014.12.09..
 */
public interface CarcassonneServiceProvider {
    GameServiceImplSwitchable getGameService();

    LobbyServiceImplSwitchable getLobbyService();

    Server getServer();

    ClientFactory getClientFactory();
}
