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
	
	public BasicDeck() {
		remainingTiles = new HashMap<>();
		
		
		randomHelper = new ArrayList<>(remainingTiles.keySet());
		Collections.shuffle(randomHelper);
		
		starterTile = createTile("city1rwe",	 "FCCCF-RRR-FFFFF"); 
		
		addTile(createTile("city4", 	"CCCCC-CCC-CCCCC"), 1);
		addTile(createTile("city3", 	"CCCCC-CCC-CFFFC"), 4);
		addTile(createTile("city2we", 	"CFFFC-CCC-CFFFC"), 3);
		addTile(createTile("city2nw",	"CCCCF-CFF-CFFFF"), 5);
		addTile(createTile("city11ne",	"FCCCC-FFC-FFFFC"), 2); // BAD, REWRITE: this is two city!
		addTile(createTile("city11we", 	"CFFFC-CFC-CFFFC"), 3);
		addTile(createTile("city1", 	"FCCCF-FFF-FFFFF"), 3);
		
		addTile(createTile("city3r", 	"CCCCC-CCC-CFRFC"), 3);
		addTile(createTile("city2nwr", 	"CCCCF-CFR-CFRFF"), 5); // BAD, REWRITE: roads are connected!!!
		addTile(createTile("city1rse", 	"FCCCF-FRR-FFRFF"), 3); // BAD, ROADS!
		addTile(createTile("city1rsw", 	"FCCCF-RRF-FFRFF"), 3); // BAD, ROADS!
		addTile(createTile("city1rswe", "FCCCF-RFR-FFRFF"), 3);
		addTile(starterTile, 3);
		
		addTile(createTile("road4",	 	"FFRFF-RXR-FFRFF"), 1);
		addTile(createTile("road3", 	"FFFFF-RXR-FFRFF"), 4);
		addTile(createTile("road2ns", 	"FFRFF-FRF-FFRFF"), 8);
		addTile(createTile("road2sw", 	"FFFFF-RRF-FFRFF"), 9);
		
		addTile(createTile("cloister", 	"FFFFF-FMF-FFFFF"), 4);
		addTile(createTile("cloisterr", "FFFFF-FMF-FFRFF"), 2);
		
	}
	
	@Override
	public BasicTile getStarterTile() {
		return starterTile;
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
