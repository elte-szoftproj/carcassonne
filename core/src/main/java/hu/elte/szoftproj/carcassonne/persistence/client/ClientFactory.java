package hu.elte.szoftproj.carcassonne.persistence.client;

public interface ClientFactory {

    LobbyRestClient getLobbyClient(String urlBase);

}
