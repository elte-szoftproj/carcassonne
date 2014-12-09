package hu.elte.szoftproj.carcassonne.persistence.client;

import hu.elte.szoftproj.carcassonne.persistence.dto.game.ActionDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.FollowerDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.GameDetailDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.PlaceDto;

public interface GameRestClient {

    public GameDetailDto getGameById(ActionDto params);

    public GameDetailDto placeTile(PlaceDto placing);

    public GameDetailDto placeFollower(FollowerDto placing);

    public GameDetailDto skipFollower(ActionDto placing);

}
