package hu.elte.szoftproj.carcassonne.model.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hu.elte.szoftproj.carcassonne.model.AreaType;
import hu.elte.szoftproj.carcassonne.model.Place;
import hu.elte.szoftproj.carcassonne.model.Side;
import hu.elte.szoftproj.carcassonne.model.Tile;

/**
 * Egy teruletelem tipus implementacioja
 * @author Zsolt
 *
 */
public class BasicTile implements Tile {

	/**
	 * Poziciok es reszegysegek osszerendelese
	 */
	Map<Place, Side> sideMap;
	/**
	 * Reszegysegek
	 */
	Set<Side> sides;
	
	/**
	 * A terulettipus neve
	 */
	String name;
	
	/**
	 * Egy reszegyseg implemetacioja
	 * @author Zsolt
	 *
	 */
	public class BasicSide implements Side {

		/**
		 * A reszegyseg tipusa
		 */
		AreaType slotType;
		/**
		 * A reszegyseget alkoto poziciok listaja
		 */
		Set<Place> directions;
		
		public BasicSide(AreaType slotType, Place[] directions) {
			super();
			this.slotType = slotType;
			this.directions = new HashSet<Place>();
			for(Place p: directions) {
				this.directions.add(p);
			}
		}
		
		@Override
		public Set<Place> getPlaces() {
			return directions;
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
	
	public BasicTile(String name) {
		sideMap = new HashMap<>();
		sides = new HashSet<>();
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Felvesz egy uj reszegyseget
	 * @param type
	 * @param places
	 */
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
	
	/**
	 * Kiboviti a reszegyseget megegy pozicioval
	 * @param s
	 * @param p
	 */
	void addSideToSlot(Side s, Place p) {
		((BasicSide)s).directions.add(p);
		sideMap.put(p,  s);
	}
	
	@Override
	public Set<Side> getSideList() {
		return sides;
	}

	@Override
	public Side getSide(Place direction) {
		return sideMap.get(direction);
	}
	
	/**
	 * Kitorol egy reszegyseget
	 * @param s
	 */
	void deleteSide(Side s) {
		sides.remove(s);
		for(Place p: s.getPlaces()) {
			sideMap.remove(p);
		}
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
	
	@Override
	public String toString() {
		String s = getName() + " [";
		
		for (Side bs: sides) {
			s += "(";
			s += ((BasicSide)bs).directions.toString();
			s += ")";
		}
		
		s +="]";
		
		return s;
	}

	
}
