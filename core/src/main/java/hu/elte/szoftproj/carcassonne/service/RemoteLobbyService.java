package hu.elte.szoftproj.carcassonne.service;


import hu.elte.szoftproj.carcassonne.persistence.client.LobbyRestClient;

public interface RemoteLobbyService extends LobbyService {

    public void setClient(LobbyRestClient client);
}
