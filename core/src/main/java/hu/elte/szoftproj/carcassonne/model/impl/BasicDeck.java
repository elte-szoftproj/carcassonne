package hu.elte.szoftproj.carcassonne.model.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hu.elte.szoftproj.carcassonne.model.AreaType;
import hu.elte.szoftproj.carcassonne.model.Deck;
import hu.elte.szoftproj.carcassonne.model.Place;
import hu.elte.szoftproj.carcassonne.model.Side;
import hu.elte.szoftproj.carcassonne.model.Tile;

public class BasicDeck implements Deck {

	int remainingPieces;
	
	BasicTile starterTile;
	
	Map<BasicTile, Integer> remainingTiles;
	List<BasicTile> randomHelper;
	
	Random random;
	
	public BasicDeck() {

		long seed = System.nanoTime();
		
		random = new Random(seed);
		
		remainingTiles = new HashMap<>();
		randomHelper = new ArrayList<>();
		
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
	
		Collections.shuffle(randomHelper);
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
	
	protected String getSlotTypeFor(char c) {
		switch(c) {
		case 'F': return "field";
		case 'C': return "city";
		case 'M': return "monastery";
		case 'R': return "road";
		case 'X': return "invalid";
		}
		return null;
	}
	
	protected AreaType generateAt(char type) {
		switch(type) {
		case 'F': return new FieldAreaType();
		case 'C': return new CityAreaType();
		case 'M': return new MonasteryAreaType();
		case 'R': return new RoadAreaType();
		case 'X': return new InvalidAreaType();
		}
		return null;
	}
	
	protected void addOneMoreSlot(BasicTile bt, char newC, char prevC, Place newPos, Place prevPos) {
		if (prevC == newC) {
			bt.addSideToSlot(bt.getSide(prevPos), newPos);
		} else {
			bt.addSlot(generateAt(newC), new Place[] { newPos });
		}
	}
	
	protected void addOneMoreSlot(BasicTile bt, char newC, char prevC, Place newPos, Place prevPos, Side collapse) {
		if (prevC == newC) {
			bt.addSideToSlot(bt.getSide(prevPos), newPos);
		} else if ( collapse.getType().getName().equals(getSlotTypeFor(newC)) ){ // second direction!
			bt.addSideToSlot(collapse, newPos);
		} else {
			bt.addSlot(generateAt(newC), new Place[] { newPos });
		}
		
		if (newC == prevC && collapse.getType().getName().equals(getSlotTypeFor(newC)) && !collapse.equals(bt.getSide(newPos))) {
			bt.deleteSide(collapse);
			for(Place p: collapse.getPlaces()) {
				bt.addSideToSlot(bt.getSide(newPos), p);
			}
		}
	}
	
	protected BasicTile createTile(String fileName, String config) {
		BasicTile bt = new BasicTile(fileName);
		String[] c = config.split("-");
		
		bt.addSlot(generateAt(c[0].charAt(0)), new Place[] { Place.TOP_LEFT_LEFT });
		addOneMoreSlot(bt, c[0].charAt(1), c[0].charAt(0), Place.TOP_LEFT_TOP, Place.TOP_LEFT_LEFT);
		addOneMoreSlot(bt, c[0].charAt(2), c[0].charAt(1), Place.TOP, Place.TOP_LEFT_TOP);
		addOneMoreSlot(bt, c[0].charAt(3), c[0].charAt(2), Place.TOP_RIGHT_TOP, Place.TOP);
		addOneMoreSlot(bt, c[0].charAt(4), c[0].charAt(3), Place.TOP_RIGHT_RIGHT, Place.TOP_RIGHT_TOP);
		
		addOneMoreSlot(bt, c[1].charAt(0), c[0].charAt(0), Place.LEFT, Place.TOP_LEFT_LEFT);
		
		// innentol azt is vizsgalni kell, hogy egyesithetunk ket mar letezo teruletet
		addOneMoreSlot(bt, c[1].charAt(1), c[1].charAt(0), Place.CENTER, Place.LEFT, bt.getSide(Place.TOP));
		addOneMoreSlot(bt, c[1].charAt(2), c[1].charAt(1), Place.RIGHT, Place.CENTER, bt.getSide(Place.TOP_RIGHT_RIGHT));
		
		// az also sarok ket eleme csak egy masiktol fugg
		addOneMoreSlot(bt, c[2].charAt(0), c[1].charAt(0), Place.BOTTOM_LEFT_LEFT, Place.LEFT);
		addOneMoreSlot(bt, c[2].charAt(1), c[2].charAt(0), Place.BOTTOM_LEFT_BOTTOM, Place.BOTTOM_LEFT_LEFT);
		
		// aztan megint ket szomszed
		addOneMoreSlot(bt, c[2].charAt(2), c[2].charAt(1), Place.BOTTOM, Place.BOTTOM_LEFT_BOTTOM, bt.getSide(Place.CENTER));
		addOneMoreSlot(bt, c[2].charAt(3), c[2].charAt(2), Place.BOTTOM_RIGHT_BOTTOM, Place.BOTTOM); // csak balrol fugg
		addOneMoreSlot(bt, c[2].charAt(4), c[2].charAt(3), Place.BOTTOM_RIGHT_RIGHT, Place.BOTTOM_RIGHT_BOTTOM, bt.getSide(Place.RIGHT));
		
		return bt;
	}
	
	@Override
	public Tile drawTile() {
				
		Collections.shuffle(randomHelper, random);
		while (remainingTiles.get(randomHelper.get(0)).equals(new Integer(0))) {
			Collections.shuffle(randomHelper, random);
		}
		BasicTile next = randomHelper.get(0);
		
		if (remainingTiles.get(next) == 1) {
			remainingTiles.remove(next);
			randomHelper.remove(next);
		} else {
			remainingTiles.put(next, remainingTiles.get(next)-1);
		}
		remainingPieces--;
		return next;
	}
	
	@Override
	public int getRemainingPieceCount() {
		return remainingPieces;
	}

}
