package hu.elte.szoftproj.carcassonne;

import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.service.LobbyService;

/**
 * Created by Zsolt on 2014.12.09..
 */
public interface CarcassonneServiceProvider {
    GameService getGameService();

    LobbyService getLobbyService();
}
