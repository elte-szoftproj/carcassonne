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
	
	BasicTile starterTile;
	
	Map<BasicTile, Integer> remainingTiles;
	List<BasicTile> randomHelper;
	
	public BasicDeck(String filename) {
		remainingTiles = new HashMap<>();
		
		
		randomHelper = new ArrayList<>(remainingTiles.keySet());
		Collections.shuffle(randomHelper);
		
		starterTile = createTile("v1", "FCCCF-RRR-FFFFF"); 
		
		addTile(createTile("1", "CCCCC-CCC-CCCCC"), 1);
		addTile(createTile("1", "CCCCC-CCC-CFFFC"), 4);
		addTile(createTile("1", "CFFFC-CCC-CFFFC"), 3);
		addTile(createTile("1", "CCCCF-CFF-CFFFF"), 5);
		addTile(createTile("1", "FCCCC-FFC-FFFFC"), 2); // BAD, REWRITE: this is two city!
		addTile(createTile("1", "CFFFC-CFC-CFFFC"), 3);
		addTile(createTile("1", "FCCCF-FFF-FFFFF"), 3);
		
		addTile(createTile("1", "CCCCC-CCC-CFRFC"), 3);
		addTile(createTile("1", "CCCCF-CFR-CFRFF"), 5); // BAD, REWRITE: roads are connected!!!
		addTile(createTile("1", "FCCCF-FRR-FFRFF"), 3);
		addTile(createTile("1", "FCCCF-RRF-FFRFF"), 3);
		addTile(createTile("1", "FCCCF-RFR-FFRFF"), 3);
		addTile(starterTile, 3);
		
		addTile(createTile("1", "FFRFF-RXR-FFRFF"), 1);
		addTile(createTile("1", "FFFFF-RXR-FFRFF"), 4);
		addTile(createTile("1", "FFRFF-FRF-FFRFF"), 8);
		addTile(createTile("1", "FFFFF-RRF-FFRFF"), 9);
		
		addTile(createTile("1", "FFFFF-FMF-FFFFF"), 4);
		addTile(createTile("1", "FFFFF-FMF-FFRFF"), 2);
		
	}
	
	protected void addTile(BasicTile t, int num) {
		remainingTiles.put(t, new Integer(num));
		remainingPieces+=num;
		randomHelper.add(t);
	}
	
	protected BasicTile createTile(String fileName, String config) {
		return null;
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
