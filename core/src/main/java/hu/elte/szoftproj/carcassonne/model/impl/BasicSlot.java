package hu.elte.szoftproj.carcassonne.model.impl;

import java.util.HashSet;
import java.util.Set;

import hu.elte.szoftproj.carcassonne.model.Area;
import hu.elte.szoftproj.carcassonne.model.Piece;
import hu.elte.szoftproj.carcassonne.model.Place;
import hu.elte.szoftproj.carcassonne.model.Side;
import hu.elte.szoftproj.carcassonne.model.Slot;
import hu.elte.szoftproj.carcassonne.model.Square;

/**
 * Egy lerakott teruletelem reszegyseget implementalo osztaly
 * @author Zsolt
 *
 */
public class BasicSlot implements Slot {

	/**
	 * A lerakott elem
	 */
	Square square;
	/**
	 * Az elem reszegysege
	 */
	Side side;
	/**
	 * A hozza tartozo terulet
	 */
	Area area;
	
	public BasicSlot(Square square, Side side) {
		super();
		this.square = square;
		this.side = side;
		this.area = null;
	}

	@Override
	public Square getSquare() {
		return square;
	}

	@Override
	public Area getArea() {
		return area;
	}
	
	/**
	 * Hozzarendeli a teruletet
	 * @param area
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public Set<Place> getPlaces() {
		Set<Place> s = new HashSet<>();
		
		for(Place p: side.getPlaces()) {
			s.add(p.rotateCw(square.getTileRotation()));
		}
		
		return s;
	}

	@Override
	public Set<Place> getUnrotatedPlaces() {
		return side.getPlaces();
	}

	@Override
	public void setPiece(Piece piece) {
		// TODO Auto-generated method stub

	}

	@Override
	public Piece getPiece() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Side getSide() {
		return side;
	}

}