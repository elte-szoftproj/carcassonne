package hu.elte.szoftproj.carcassonne.model.impl;

import hu.elte.szoftproj.carcassonne.model.Area;

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
