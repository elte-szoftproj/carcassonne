package hu.elte.szoftproj.carcassonne.persistence.client.impl;

import hu.elte.szoftproj.carcassonne.persistence.client.GameRestClient;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.ActionDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.FollowerDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.GameDetailDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.PlaceDto;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 * Created by Zsolt on 2014.12.09..
 */
public class GameRestClientImpl extends RestClientImpl implements GameRestClient {

    final static String URL_GET_GAME = "game/get";
    final static String URL_PLACE_TILE = "game/place/tile";
    final static String URL_PLACE_FOLLOWER = "game/place/follower";
    final static String URL_SKIP_FOLLOWER = "game/skip/follower";

    public GameRestClientImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public GameDetailDto getGameById(ActionDto params) {
        return client()
                .target(getUrlBuilder().pathSegment(URL_GET_GAME).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(params))
                .readEntity(GameDetailDto.class)
                ;
    }

    @Override
    public GameDetailDto placeTile(PlaceDto placing) {
        return client()
                .target(getUrlBuilder().pathSegment(URL_PLACE_TILE).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(placing))
                .readEntity(GameDetailDto.class)
                ;
    }

    @Override
    public GameDetailDto placeFollower(FollowerDto placing) {
        return client()
                .target(getUrlBuilder().pathSegment(URL_PLACE_FOLLOWER).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(placing))
                .readEntity(GameDetailDto.class)
                ;
    }

    @Override
    public GameDetailDto skipFollower(ActionDto placing) {
        return client()
                .target(getUrlBuilder().pathSegment(URL_SKIP_FOLLOWER).build().toUriString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(placing))
                .readEntity(GameDetailDto.class)
                ;
    }
}
