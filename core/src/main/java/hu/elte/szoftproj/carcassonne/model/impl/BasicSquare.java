package hu.elte.szoftproj.carcassonne.model.impl;

import hu.elte.szoftproj.carcassonne.model.Place;
import hu.elte.szoftproj.carcassonne.model.Rotation;
import hu.elte.szoftproj.carcassonne.model.Side;
import hu.elte.szoftproj.carcassonne.model.Slot;
import hu.elte.szoftproj.carcassonne.model.Square;
import hu.elte.szoftproj.carcassonne.model.Tile;
import hu.elte.szoftproj.carcassonne.model.impl.BasicBoard.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Egy lerakott teruletelem implementacioja
 * @author Zsolt
 *
 */
public class BasicSquare implements Square {

	
	/**
	 * A teruletelem tipusa
	 */
	Tile	 t;
	/**
	 * A teruletelem elforgatasa
	 */
	Rotation r;
	/**
	 * A teruletelem pozicioja
	 */
	Position position;
	
	/**
	 * A reszegysegek listaja
	 */
	Set<Slot> slots;
			
	public BasicSquare(Tile t, Rotation r, Position position) {
		super();
		this.t = t;
		this.r = r;
		this.position = position;
		this.slots = new HashSet<>();
		
		for(Side s: t.getSideList()) {
			slots.add(new BasicSlot(this, s));
		}
	}

	@Override
	public int getX() {
		return position.x;
	}

	@Override
	public int getY() {
		return position.y;
	}

	@Override
	public Tile getTile() {
		return t;
	}
	
	@Override
	public Rotation getTileRotation() {
		return r;
	}

	@Override
	public Set<Slot> getSlotList() {
		return slots;
	}

	@Override
	public Slot getSlotAt(Place direction) {
		for(Slot s: slots) {
			if (s.getPlaces().contains(direction)) {
				return s;
			}
		}
		return null;
	}
	
}