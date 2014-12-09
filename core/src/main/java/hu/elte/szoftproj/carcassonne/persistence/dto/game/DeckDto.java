package hu.elte.szoftproj.carcassonne.persistence.dto.game;

/**
 * Created by Zsolt on 2014.12.09..
 */
public class DeckDto {

    String deckType;

    int remainingTiles;

    String currentTile;

    public DeckDto() {

    }

    public DeckDto(String deckType, int remainingTiles, String currentTile) {
        this.deckType = deckType;
        this.remainingTiles = remainingTiles;
        this.currentTile = currentTile;
    }

    public String getDeckType() {
        return deckType;
    }

    public int getRemainingTiles() {
        return remainingTiles;
    }

    public String getCurrentTile() {
        return currentTile;
    }
}
