package hu.elte.szoftproj.carcassonne.persistence.client.impl;

import hu.elte.szoftproj.carcassonne.persistence.client.ClientFactory;
import hu.elte.szoftproj.carcassonne.persistence.client.GameRestClient;
import hu.elte.szoftproj.carcassonne.persistence.client.LobbyRestClient;

public class ClientFactoryImpl implements ClientFactory {


    @Override
    public LobbyRestClient getLobbyClient(String urlBase) {
        return new LobbyRestClientImpl(urlBase);
    }

    @Override
    public GameRestClient getGameClient(String urlBase) {
        return new GameRestClientImpl(urlBase);
    }
}
