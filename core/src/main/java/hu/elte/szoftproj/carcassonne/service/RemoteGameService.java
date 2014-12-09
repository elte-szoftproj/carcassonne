package hu.elte.szoftproj.carcassonne.service;

import hu.elte.szoftproj.carcassonne.persistence.client.GameRestClient;

public interface RemoteGameService extends GameService {

    public void setClient(GameRestClient client);
}
