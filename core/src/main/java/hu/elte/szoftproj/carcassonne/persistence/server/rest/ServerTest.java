package hu.elte.szoftproj.carcassonne.persistence.server.rest;

import hu.elte.szoftproj.carcassonne.persistence.client.impl.LobbyRestClientImpl;
import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.GameCreateActionDto;

/**
 * Created by Zsolt on 2014.12.08..
 */
public class ServerTest {

    public static void main(String[] args) throws Exception {
        System.out.println(new LobbyRestClientImpl("http://localhost:8080").createGame(new GameCreateActionDto("test", "basicx")).getStatus());
    }
}
