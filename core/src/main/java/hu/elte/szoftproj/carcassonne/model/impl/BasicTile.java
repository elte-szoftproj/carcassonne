package hu.elte.szoftproj.carcassonne.model.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hu.elte.szoftproj.carcassonne.model.AreaType;
import hu.elte.szoftproj.carcassonne.model.Place;
import hu.elte.szoftproj.carcassonne.model.Side;
import hu.elte.szoftproj.carcassonne.model.Tile;

public class BasicTile implements Tile {

	Map<Place, Side> sideMap;
	Set<Side> sides;
	
	public class BasicSide implements Side {

		AreaType slotType;
		Place[] directions;
		
		public BasicSide(AreaType slotType, Place[] directions) {
			super();
			this.slotType = slotType;
			this.directions = directions;
		}
		
		@Override
		public Set<Place> getPlaces() {
			Set<Place> s = new HashSet<Place>();
			for(Place p: directions) {
				s.add(p);
			}
			return s;
		}

		@Override
		public AreaType getType() {
			return slotType;
		}

		@Override
		public boolean compatibleWith(Side s) {
			return s.getType().equals(getType());
		}
		
	}
	
	public BasicTile() {
		sideMap = new HashMap<>();
		sides = new HashSet<>();
	}
	
	void addSlot(AreaType type, Place[] places) {
	
		BasicSide bs = new BasicSide(type, places);
		
		for(Place p: places) {
			if (sideMap.containsKey(p)) {
				throw new RuntimeException("Duplicate key: " + p);
			}
			sideMap.put(p, bs);
		}
		sides.add(bs);
		
	}
	
	@Override
	public Set<Side> getSideList() {
		return sides;
	}

	@Override
	public Side getSide(Place direction) {
		return sideMap.get(direction);
	}

	@Override
	public boolean canPlacePieceType(AreaType pieceType) {
		for (Side bs: sides) {
			if (((BasicSide)bs).slotType.equals(pieceType)) {
				return true;
			}
		}
		return false;
	}

	
}
