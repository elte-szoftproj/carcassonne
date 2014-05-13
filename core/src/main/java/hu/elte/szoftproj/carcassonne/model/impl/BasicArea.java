package hu.elte.szoftproj.carcassonne.model.impl;

import java.util.HashSet;
import java.util.Set;

import hu.elte.szoftproj.carcassonne.model.Area;
import hu.elte.szoftproj.carcassonne.model.AreaType;
import hu.elte.szoftproj.carcassonne.model.Piece;
import hu.elte.szoftproj.carcassonne.model.Place;
import hu.elte.szoftproj.carcassonne.model.Slot;
import hu.elte.szoftproj.carcassonne.model.Square;

public class BasicArea implements Area {

	private AreaType type;
	
	private int openPlaces;// TODO: how to manage this?
	
	private Set<Square> squares;
	private Set<Slot> containedSlots;
	Set<Area> neighbours;
	
	Area mergedWith;
	
	public BasicArea(Square s, Place direction) {
		
		squares = new HashSet<>();
		containedSlots = new HashSet<>();
		neighbours = new HashSet<>();
		
		openPlaces = direction == Place.CENTER ? 0 : 1;
		Slot ss = s.getSlotAt(direction);
		squares.add(s);
		containedSlots.add(ss);
		mergedWith = null;
		
		type = ss.getSide().getType();
	}
	
	@Override
	public AreaType getType() {
		return (mergedWith == null ? type : mergedWith.getType());
	}

	@Override
	public boolean isComplete() {
		return (mergedWith == null ? (openPlaces == 0) : mergedWith.isComplete());
	}

	@Override
	public Piece[] getPieceList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addNeighbour(Area a) {
		
		if (mergedWith != null) {
			mergedWith.addNeighbour(a);
			return;
		}
		
		neighbours.add(a);
		openPlaces--;
	}
	
	public void addPart(Square s, Place direction) {
		
		if (mergedWith != null) {
			mergedWith.addPart(s, direction);
			return;
		}
		if (s.getSlotAt(direction).getArea() != null) {
			throw new RuntimeException("Already already set!");
		}
		((BasicSlot)s.getSlotAt(direction)).setArea(this);
		if (direction != Place.CENTER) {
			openPlaces++;
		}
		containedSlots.add(s.getSlotAt(direction));
		squares.add(s);
	}

	@Override
	public Set<Area> getNeighbours() {
		return (mergedWith == null ? neighbours : mergedWith.getNeighbours());
	}

	@Override
	public Set<Square> getSquares() {
		return (mergedWith == null ? squares : mergedWith.getSquares());
	}

	@Override
	public void mergeTo(Area area) {
		if (mergedWith != null) {
			mergedWith.mergeTo(area);
		}
		if (!area.getType().equals(getType())) {
			throw new RuntimeException("Different area types!");
		}
		if (!(area instanceof BasicArea)) {
			throw new RuntimeException("Unsupported merge!");
		}
		BasicArea ba = (BasicArea)area;
		ba.neighbours.addAll(neighbours);
		ba.containedSlots.addAll(containedSlots);
		ba.squares.addAll(squares);
		ba.openPlaces += openPlaces;
		
		squares.clear();
		neighbours.clear();
		containedSlots.clear();
	}

	@Override
	public Set<Slot> getSlots() {
		return (mergedWith == null ? containedSlots : mergedWith.getSlots());
	}

}
