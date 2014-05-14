package hu.elte.szoftproj.carcassonne.model;

import java.net.URL;
import java.util.List;

// Top level object for a carcassonne game. Manages all other objects
public interface Game {

    // add players to the game
    Player addLocalPlayer(String name);
    
    Player addComputerPlayer(String name);

    // get game objects
    List<Player> getPlayerList();
    Board    getBoard();
    Deck     getDeck();
}
