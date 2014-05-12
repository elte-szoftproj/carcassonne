package hu.elte.szoftproj.carcassonne.model.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hu.elte.szoftproj.carcassonne.model.Place;
import hu.elte.szoftproj.carcassonne.model.Side;
import hu.elte.szoftproj.carcassonne.model.Tile;

public class BasicTile implements Tile {

	Map<Place, Side> sideMap;
	Set<BasicSide> sides;
	
	public class BasicSide implements Side {

		String slotType;
		Place[] directions;
		
		public BasicSide(String slotType, Place[] directions) {
			super();
			this.slotType = slotType;
			this.directions = directions;
		}

		@Override
		public String getSlotType() {
			return slotType;
		}
		
	}
	
	public BasicTile() {
		sideMap = new HashMap<>();
		sides = new HashSet<>();
	}
	
	void addSlot(String type, Place[] places) {
	
		BasicSide bs = new BasicSide(type, places);
		
		for(Place p: places) {
			if (sideMap.containsKey(p)) {
				throw new RuntimeException("Duplicate key: " + p);
			}
			sideMap.put(p, bs);
		}
		
	}
	
	@Override
	public Set<Side> getSideList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Side getSide(Place direction) {
		return sideMap.get(direction);
	}

	@Override
	public boolean canPlacePieceType(String pieceType) {
		for (BasicSide bs: sides) {
			if (bs.slotType.equals(pieceType)) {
				return true;
			}
		}
		return false;
	}

	
}
