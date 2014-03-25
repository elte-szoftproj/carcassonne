package hu.elte.szoftproj.carcassonne.model;

import java.net.URL;

// Top level object for a carcassonne game. Manages all other objects
public interface Game {

    // add players to the game
    Player addLocalPlayer(String name);
    Player addRemotePlayer(String name, URL address);
    Player addComputerPlayer(String name);

    // get game objects
    Player[] getPlayerList();
    Board    getBoard();
    Deck     getDeck();
    RuleBook getRuleBook();
}
