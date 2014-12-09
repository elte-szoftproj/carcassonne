package hu.elte.szoftproj.carcassonne.screen;

import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.domain.Follower;
import hu.elte.szoftproj.carcassonne.domain.Rotation;
import hu.elte.szoftproj.carcassonne.domain.Tile;

import java.util.Optional;

/**
 * Created by Zsolt on 2014.12.09..
 */
public interface CurrentGameInterface {
    CarcassonneGame getCurrentGame();

    String getCurrentPlayerName();

    public Optional<String> getCurrentFollowerSelection();

    public Optional<Rotation> getCurrentTileRotation();

    public Optional<Tile> getCurrentTile();

    public boolean canPlaceFollowersNow();

    public Follower getFollowerForType(String type);
}

