package hu.elte.szoftproj.carcassonne.model.impl;

import java.util.HashMap;
import java.util.Map;

import hu.elte.szoftproj.carcassonne.model.Area;
import hu.elte.szoftproj.carcassonne.model.Board;
import hu.elte.szoftproj.carcassonne.model.Rotation;
import hu.elte.szoftproj.carcassonne.model.Square;
import hu.elte.szoftproj.carcassonne.model.Tile;

public class BasicBoard implements Board {

	public class Position implements Comparable<Position>{
		int x;
		int y;
		
		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
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
	}
	
	Map<Position, Square> squares;
	
	Position topLeft;
	Position bottomRight;
	
	public BasicBoard(Tile initialPiece, Rotation r) {
		squares = new HashMap<>();
		topLeft = new Position(0,0);
		bottomRight = new Position(0,0);
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
	public Square getStartTile() {
		
		return null;
	}

	@Override
	public Square getTileAt(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canPlaceTileAt(int x, int y, Tile t, Rotation r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Area[] getAreaList(Object type) {
		// TODO Auto-generated method stub
		return null;
	}

}
