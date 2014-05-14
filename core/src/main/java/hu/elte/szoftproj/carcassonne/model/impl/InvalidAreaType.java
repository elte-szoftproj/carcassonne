package hu.elte.szoftproj.carcassonne.model.impl;

import hu.elte.szoftproj.carcassonne.model.Area;

/**
 * Nem hasznalhato terulettipust megvalosito osztaly (utkeresztezodesek)
 * @author Zsolt
 *
 */
public class InvalidAreaType extends AbstractAreaType {

	@Override
	public String getName() {
		return "invalid";
	}

	@Override
	public int getScore(Area a) {
		// TODO Auto-generated method stub
		return 0;
	}

}
