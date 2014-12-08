package hu.elte.szoftproj.carcassonne.persistence.client;

import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class LobbyRestClient extends RestClientImpl {

    final static String URL_WAITING_GAME_LIST = "lobby/list/waiting";
    final static String URL_ACTIVE_GAME_LIST = "lobby/list/active";
    final static String URL_GAME_CREATE = "lobby/create";
    final static String URL_GAME_JOIN_AI = "lobby/join/ai";
    final static String URL_GAME_JOIN = "lobby/join";
    final static String URL_GAME_START = "lobby/start";

    public LobbyRestClient(String baseUrl) {
        super(baseUrl);
    }

    public MultiGameDto getWaitingGameList() {
        return client()
                .target(getUrlBuilder().pathSegment(URL_WAITING_GAME_LIST).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get()
                .readEntity(MultiGameDto.class)
                ;
    }

    public MultiGameDto getActiveGameList() {
        return client()
                .target(getUrlBuilder().pathSegment(URL_ACTIVE_GAME_LIST).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get()
                .readEntity(MultiGameDto.class)
                ;
    }

    public SingleGameDto createGame(GameCreateActionDto gcd) {
        return client()
                .target(getUrlBuilder().pathSegment(URL_GAME_CREATE).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(gcd))
                .readEntity(SingleGameDto.class)
                ;
    }

    public SingleGameDto joinGameWithAi(GameJoinActionDto gcd) {
        return client()
                .target(getUrlBuilder().pathSegment(URL_GAME_JOIN_AI).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(gcd))
                .readEntity(SingleGameDto.class)
                ;
    }

    public SingleGameDto joinGame(GameJoinActionDto gcd) {
        return client()
                .target(getUrlBuilder().pathSegment(URL_GAME_JOIN).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(gcd))
                .readEntity(SingleGameDto.class)
                ;
    }

    public SingleGameDto startGame(GameIdDto gameId) {
        return client()
                .target(getUrlBuilder().pathSegment(URL_GAME_START).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(gameId))
                .readEntity(SingleGameDto.class)
                ;
    }

}
