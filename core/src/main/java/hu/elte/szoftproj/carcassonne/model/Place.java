package hu.elte.szoftproj.carcassonne.model;

/**
 * Egy p!lyaelem r!szpoz!ci!inak a list!ja. 
 * Ezek azok a poz!ci!k, amik az elemen a ter!letek alapjait alkotj!k, illetve ahova a b!bukat le lehet helyezni.
 * 
 * @author Zsolt
 *
 */
public enum Place {
	
	// a negy alap egtaly
	TOP, BOTTOM, LEFT, RIGHT,
	
	// az elem kozepe
	CENTER,
	
	
	// a sarkokat ket reszre kell bontani, az atloknak megfeleloen
	TOP_LEFT_TOP, TOP_RIGHT_TOP,
	BOTTOM_LEFT_BOTTOM, BOTTOM_RIGHT_BOTTOM,
	
	TOP_LEFT_LEFT, TOP_RIGHT_RIGHT,
	BOTTOM_LEFT_LEFT, BOTTOM_RIGHT_RIGHT
	;

	/**
	 * visszaadja a poziciot az ora mutato jarasa szerint forgatva 
	 * @param r
	 * @return
	 */
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
		
		case TOP_LEFT_TOP: return TOP_RIGHT_RIGHT;
		case TOP_RIGHT_TOP: return BOTTOM_RIGHT_RIGHT;
		case BOTTOM_RIGHT_BOTTOM: return BOTTOM_LEFT_LEFT;
		case BOTTOM_LEFT_BOTTOM: return TOP_LEFT_LEFT;
		
		case TOP_LEFT_LEFT: return TOP_RIGHT_TOP;
		case TOP_RIGHT_RIGHT: return BOTTOM_RIGHT_BOTTOM;
		case BOTTOM_RIGHT_RIGHT: return BOTTOM_LEFT_BOTTOM;
		case BOTTOM_LEFT_LEFT: return TOP_LEFT_TOP;
		}
		
		throw new RuntimeException("Unknown enum value!");
	}
	
	/**
	 * Visszaadja a pozicioval szemkozti elemet, azaz hogy a palyaelem mellett a megfelelo oldalon milyen pozicio talalhato mellette.
	 * @return
	 */
	public Place opposite() {
		switch(this) {
		case TOP: return BOTTOM;
		case BOTTOM: return TOP;
		case RIGHT: return LEFT;
		case LEFT: return RIGHT;
		
		case CENTER: return CENTER;
		
		case TOP_LEFT_TOP: return BOTTOM_LEFT_BOTTOM;
		case BOTTOM_RIGHT_BOTTOM: return TOP_RIGHT_TOP;
		case TOP_RIGHT_TOP: return BOTTOM_RIGHT_BOTTOM;
		case BOTTOM_LEFT_BOTTOM: return TOP_LEFT_TOP;
		
		case TOP_LEFT_LEFT: return TOP_RIGHT_RIGHT;
		case BOTTOM_RIGHT_RIGHT: return BOTTOM_LEFT_LEFT;
		case TOP_RIGHT_RIGHT: return TOP_LEFT_LEFT;
		case BOTTOM_LEFT_LEFT: return BOTTOM_RIGHT_RIGHT;
		}
		
		throw new RuntimeException("Unknown enum value!");
	}
	
	/**
	 * Ora mutato jarasa szerinti iranyban melyik a kovetkezo elem.
	 * @return
	 */
	public Place next() {
		switch(this) {
		case TOP: return TOP_RIGHT_TOP;
		case TOP_RIGHT_TOP: return TOP_RIGHT_RIGHT;
		case TOP_RIGHT_RIGHT: return RIGHT;
		case RIGHT: return BOTTOM_RIGHT_RIGHT;
		case BOTTOM_RIGHT_RIGHT: return BOTTOM_RIGHT_BOTTOM;
		case BOTTOM_RIGHT_BOTTOM: return BOTTOM;
		case BOTTOM: return BOTTOM_LEFT_BOTTOM;
		case BOTTOM_LEFT_BOTTOM: return BOTTOM_LEFT_LEFT;
		case BOTTOM_LEFT_LEFT: return LEFT;
		case LEFT: return TOP_LEFT_LEFT;
		case TOP_LEFT_LEFT: return TOP_LEFT_TOP;
		case TOP_LEFT_TOP: return TOP;
		
		case CENTER: return CENTER;
		}
		
		throw new RuntimeException("Unknown enum value!");
	}

	/**
	 * Ora mutato jarasaval ellentetes forgatast vegez.
	 * @param r
	 * @return
	 */
	public Place rotateCcw(Rotation r) {
		if (r == Rotation.D0) {
			return this;
		}
		if (r == Rotation.D180) {
			return this.rotateCcw(Rotation.D90).rotateCcw(Rotation.D90);
		}
		if (r == Rotation.D270) {
			return this.rotateCcw(Rotation.D90).rotateCcw(Rotation.D90).rotateCcw(Rotation.D90);
		}
		
		return rotateCw(Rotation.D270);
	}
}
