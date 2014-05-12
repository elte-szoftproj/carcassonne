package hu.elte.szoftproj.carcassonne.model.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.elte.szoftproj.carcassonne.model.Deck;
import hu.elte.szoftproj.carcassonne.model.Tile;

public class BasicDeck implements Deck {

	int remainingPieces;
	
	Map<BasicTile, Integer> remainingTiles;
	List<BasicTile> randomHelper;
	
	public BasicDeck(String filename) {
		remainingTiles = new HashMap<>();
		
		
		randomHelper = new ArrayList<>(remainingTiles.keySet());
		Collections.shuffle(randomHelper);
	}
	
	@Override
	public Tile drawTile() {
		while (remainingTiles.get(randomHelper.get(0)) == 0) {
			Collections.shuffle(randomHelper);
		}
		BasicTile next = randomHelper.get(0);
		
		if (remainingTiles.get(next) == 1) {
			remainingTiles.remove(next);
		} else {
			remainingTiles.put(next, remainingTiles.get(next)-1);
		}
		
		return next;
	}

}
