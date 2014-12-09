package hu.elte.szoftproj.carcassonne.service.impl.rest;

import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.persistence.GameConverter;
import hu.elte.szoftproj.carcassonne.persistence.client.GameRestClient;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.ActionDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.FollowerDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.PlaceDto;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import hu.elte.szoftproj.carcassonne.service.RemoteGameService;
import org.springframework.beans.factory.annotation.Autowired;

public class GameServiceImplRest implements RemoteGameService {

    GameRestClient client;

    @Autowired
    GameConverter converter;

    @Autowired
    DeckFactory deckFactory;

    public GameServiceImplRest() {
    }

    @Override
    public void setClient(GameRestClient client) {
        this.client = client;
    }

    @Override
    public Game getGameById(String playerName, String gameId) {
        return converter.fromDto(client.getGameById(new ActionDto(gameId, playerName)));
    }

    @Override
    public Game placeTile(Game g, Player owner, Tile t, Rotation r, int y, int x) {
        if (!t.equals(g.getDeck().get().peekNext())) {
            throw new IllegalArgumentException("NOT_CURRENT_TILE");
        }
        return converter.fromDto(client.placeTile(new PlaceDto(g.getId(), owner.getName(), y, x, r)));
    }

    @Override
    public Game placeFollower(Game g, Player owner, Follower f, int y, int x, int dy, int dx) {
        if (!f.getOwner().equals(owner)) {
            throw new IllegalArgumentException("NOT_YOUR_FOLLOWER");
        }
        return converter.fromDto(client.placeFollower(new FollowerDto(g.getId(), owner.getName(), y, x, f.getName(), dy, dx)));
    }

    @Override
    public Game dontPlaceFollower(Game g, Player owner) {
        return converter.fromDto(client.skipFollower(new ActionDto(g.getId(), owner.getName())));
    }
}
