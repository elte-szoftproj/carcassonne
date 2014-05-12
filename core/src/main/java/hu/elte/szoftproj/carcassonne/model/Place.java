package hu.elte.szoftproj.carcassonne.model;

public enum Place {
	TOP, BOTTOM, LEFT, RIGHT,
	CENTER,
	TOP_LEFT, TOP_RIGHT,
	BOTTOM_LEFT, BOTTOM_RIGHT;
	
	public Place rotateCw(Rotation r) {
		if (r == Rotation.D0) {
			return this;
		}
		if (r == Rotation.D180) {
			return this.rotateCw(Rotation.D90).rotateCw(Rotation.D90);
		}
		if (r == Rotation.D270) {
			return this.rotateCw(Rotation.D90).rotateCw(Rotation.D90).rotateCw(Rotation.D90);
		}
		switch (this) {
		case TOP: return RIGHT;
		case RIGHT: return BOTTOM;
		case BOTTOM: return LEFT;
		case LEFT: return TOP;
		
		case CENTER: return CENTER;
		
		case TOP_LEFT: return TOP_RIGHT;
		case TOP_RIGHT: return BOTTOM_RIGHT;
		case BOTTOM_RIGHT: return BOTTOM_LEFT;
		case BOTTOM_LEFT: return TOP_LEFT;
		}
		
		throw new RuntimeException("Unknown enum value!");
	}
}
