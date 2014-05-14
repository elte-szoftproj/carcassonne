package hu.elte.szoftproj.carcassonne.model.impl;

import java.util.HashMap;
import java.util.Map;

import hu.elte.szoftproj.carcassonne.model.Area;
import hu.elte.szoftproj.carcassonne.model.Board;
import hu.elte.szoftproj.carcassonne.model.Place;
import hu.elte.szoftproj.carcassonne.model.PositionInterface;
import hu.elte.szoftproj.carcassonne.model.Rotation;
import hu.elte.szoftproj.carcassonne.model.Side;
import hu.elte.szoftproj.carcassonne.model.Square;
import hu.elte.szoftproj.carcassonne.model.Tile;

public class BasicBoard implements Board {

	public class Position implements Comparable<Position>, PositionInterface{
		int x;
		int y;
		
		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		/* (non-Javadoc)
		 * @see hu.elte.szoftproj.carcassonne.model.impl.PositionInterface#getX()
		 */
		@Override
		public int getX() {
			return x;
		}
		
		/* (non-Javadoc)
		 * @see hu.elte.szoftproj.carcassonne.model.impl.PositionInterface#getY()
		 */
		@Override
		public int getY() {
			return y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Position)) {
				return false;
			}
			
			return ((Position)obj).compareTo(this) == 0;
		}
		
		BasicBoard getBasicBoard() {
			return BasicBoard.this;
		}
		
		@Override
		public int compareTo(Position o) {
			if (BasicBoard.this != o.getBasicBoard()) {
				return -1;
			}
			return 0;
		}
		
		@Override
		public int hashCode() {
			return new Integer(x+y*100000).hashCode();
		}
	}
	
	Map<Position, BasicSquare> squares;
	
	Position topLeft;
	Position bottomRight;
	
	public BasicBoard(Tile initialPiece, Rotation r) {
		squares = new HashMap<>();
		topLeft = new Position(0,0);
		bottomRight = new Position(0,0);
		
		Position p = new Position(0,0);
		squares.put(p, new BasicSquare(initialPiece, r, p));
		
		calculateAreas((BasicSquare)getStartSquare());
	}
	
	@Override
	public int width() {
		return bottomRight.x - topLeft.x + 1;
	}

	@Override
	public int height() {
		return bottomRight.y - topLeft.y + 1;
	}

	@Override
	public Square getStartSquare() {
		
		return squares.get(new Position(0,0));
	}

	@Override
	public Square getTileAt(int x, int y) {
		return squares.get(new Position(x,y));
	}

	@Override
	public boolean canPlaceTileAt(int x, int y, Tile t, Rotation r) {
		
		if (!(t instanceof BasicTile)) {
			throw new RuntimeException("Not supported tile class");
		}
		
		PositionInterface key = new Position(x,y);
		if(squares.containsKey(key)) {
			return false;
		}
		int nearbyTiles = 0;
		Square s = null;
		if ((s = squares.get(new Position(x, y-1))) != null) { // top
			if(!tileGoodThere(s, t, r, new Place[]{ Place.TOP_LEFT_TOP, Place.TOP, Place.TOP_RIGHT_TOP } )) {
				return false;
			}
			nearbyTiles++;
		}
		if ((s = squares.get(new Position(x-1, y))) != null) { // right
			if(!tileGoodThere(s, t, r, new Place[]{ Place.TOP_RIGHT_RIGHT, Place.RIGHT, Place.BOTTOM_RIGHT_RIGHT } )) {
				return false;
			}
			nearbyTiles++;
		}
		if ((s = squares.get(new Position(x, y+1))) != null) { // bottom
			if(!tileGoodThere(s, t, r, new Place[]{ Place.BOTTOM_LEFT_BOTTOM, Place.BOTTOM, Place.BOTTOM_RIGHT_BOTTOM } )) {
				return false;
			}
			nearbyTiles++;
		}
		if ((s = squares.get(new Position(x+1, y))) != null) { // left
			if(!tileGoodThere(s, t, r, new Place[]{ Place.TOP_LEFT_LEFT, Place.LEFT, Place.BOTTOM_LEFT_LEFT } )) {
				return false;
			}
			nearbyTiles++;
		}
		
		if (nearbyTiles == 0) {
			return false;
		}
		
		return true;
	}

	@Override
	public Square placeTileAt(int x, int y, Tile t, Rotation r) {
		if (!canPlaceTileAt(x, y, t, r)) {
			return null;
		}
		
		Position p = new Position(x,y);
		BasicSquare s = new BasicSquare(t,r,p);
		squares.put(p, s);
		
		// update bounding box
		if (x > bottomRight.x) { bottomRight.x = x; }
		if (x < topLeft.x)     { topLeft.x = x; }
		if (y > bottomRight.y) { bottomRight.y = y; }
		if (y < topLeft.y)     { topLeft.y = y; }
				
		calculateAreas(s);
		
		return s;
	}
	
	private PositionInterface modifyPositionFor(Position p, Place pl) {
		switch(pl) {
		case TOP: return new Position(p.x, p.y-1);
		case TOP_LEFT_TOP: return new Position(p.x, p.y-1);
		case TOP_RIGHT_TOP: return new Position(p.x, p.y-1);
		
		case BOTTOM: return new Position(p.x, p.y+1);
		case BOTTOM_LEFT_BOTTOM: return new Position(p.x, p.y+1);
		case BOTTOM_RIGHT_BOTTOM: return new Position(p.x, p.y+1);
		
		case LEFT: return new Position(p.x-1, p.y);
		case BOTTOM_LEFT_LEFT: return new Position(p.x-1, p.y);
		case TOP_LEFT_LEFT: return new Position(p.x-1, p.y);
		
		case RIGHT: return new Position(p.x+1, p.y);
		case BOTTOM_RIGHT_RIGHT: return new Position(p.x+1, p.y);
		case TOP_RIGHT_RIGHT: return new Position(p.x+1, p.y);
		
		case CENTER: return p;
		}
		throw new RuntimeException("unhandles place: " + p);
	}
	
	private void calculateAreas(BasicSquare s) {
		for(Place p : Place.values()) {
			
			PositionInterface oth = modifyPositionFor(s.position, p);
			BasicSquare otherSquare = squares.get(oth);
			
			BasicSlot mySlot = (BasicSlot)s.getSlotAt(p);
			BasicSlot otherSlot = otherSquare == null ? null : (BasicSlot)otherSquare.getSlotAt(p.opposite());
			
			if (p == Place.CENTER) {
				// special case!
				if (mySlot.getPlaces().size() == 1) {
					// egydarabos area, pl. kolostor, szimplan felvesszuk
					mySlot.setArea(new BasicArea(s, Place.CENTER));
				} else {
					// majd foglalkozunk vele az egyik rendes oldalnal...
					continue;
				}
			}
			
			if (mySlot.getArea() != null) {
				if (otherSquare != null && mySlot.getSide().getType().equals(otherSlot.getSide().getType())) {
					if (!mySlot.getArea().equals(otherSlot.getArea())) {
						// - van szembe masik terulet
						// - azonos tipusu
						// - masik area resze
						mySlot.getArea().mergeTo(otherSlot.getArea());
						// TODO: remove original area from the list
					} else {
						// nop, mar amugy is ugyanazok
					}
				} else {
					// nem azonos tipusuak => hanyjuk, csak jelezzuk, hogy egyel kevesebb szomszedjuk / elemuk van
					mySlot.getArea().addPart(s, p);
					if (otherSlot != null) {
						mySlot.getArea().addNeighbour(otherSlot.getArea());
						otherSlot.getArea().addNeighbour(mySlot.getArea());
					}
				}
			} else {
				if (otherSquare != null) {
					if (mySlot.getSide().getType().equals(otherSlot.getSide().getType())) {
						otherSlot.getArea().addPart(s, p);
					} else {
						mySlot.setArea(new BasicArea(s, p));
						mySlot.getArea().addPart(s, p);
						mySlot.getArea().addNeighbour(otherSlot.getArea());
						otherSlot.getArea().addNeighbour(mySlot.getArea());
					}
					
				} else {
					mySlot.setArea(new BasicArea(s, p));
				}
			}
		}
		
		for(Place p : Place.values()) {
			if (p == Place.CENTER) {
				continue;
			}
			
			if (!s.getSlotAt(p).getArea().equals(s.getSlotAt(Place.CENTER).getArea())) {
				((BasicArea)s.getSlotAt(Place.CENTER).getArea()).neighbours.add(s.getSlotAt(p).getArea());
				((BasicArea)s.getSlotAt(p).getArea()).neighbours.add(s.getSlotAt(Place.CENTER).getArea());
			}
			
			if (!s.getSlotAt(p).getArea().equals(s.getSlotAt(p.next()).getArea())) {
				((BasicArea)s.getSlotAt(p.next()).getArea()).neighbours.add(s.getSlotAt(p).getArea());
				((BasicArea)s.getSlotAt(p).getArea()).neighbours.add(s.getSlotAt(p.next()).getArea());
			}
		}
		
	}

	/**
	 * Checks the sides of the tiles if they are compatible 
	 * @param s
	 * @param t
	 * @param r
	 * @param places
	 * @return
	 */
	private boolean tileGoodThere(Square s, Tile t, Rotation r, Place[] places) {
		for(Place p: places) {
			Side existingSide = s.getSlotAt(p).getSide();
			Side facingSide = t.getSide((p.opposite().rotateCcw(r)));
			if (!facingSide.compatibleWith(existingSide)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public Area[] getAreaList(Object type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public hu.elte.szoftproj.carcassonne.model.PositionInterface getTopLeftPosition() {
		return topLeft;
	}
	
	@Override
	public hu.elte.szoftproj.carcassonne.model.PositionInterface getBottomRightPosition() {
		return bottomRight;
	}

}
