package hu.elte.szoftproj.carcassonne.model;

/**
 * 90 fokokban torteno forgatast leiro lista.
 * @author Zsolt
 *
 */
public enum Rotation {
	// a negy lehetsges ertek
    D0, D90, D180, D270;
    
    /**
     * Megadja a forgatas mereteket fokokban
     * @return
     */
    public float getDegrees() {
    	switch(this) {
    	case D0: return 0.0f;
    	case D90: return 90.0f;
    	case D180: return 180.0f;
    	case D270: return 270.0f;
    	}
    	return 0;
    }
    
    /**
     * Tovabb forgat meg 90 fokkal az ora mutato jarasaval megegyezoleg
     * @return
     */
    public Rotation next() {
    	switch(this) {
    	case D0: return D90;
    	case D90: return D180;
    	case D180: return D270;
    	case D270: return D0;
    	}
    	
    	return D0;
    }
    
    /**
     * Az ora mutato jarasaval ellentetben forgat 90 fokkal
     * @return
     */
    public Rotation prev() {
    	switch(this) {
    	case D0: return D270;
    	case D90: return D0;
    	case D180: return D90;
    	case D270: return D180;
    	}
    	
    	return D0;
    }
}
