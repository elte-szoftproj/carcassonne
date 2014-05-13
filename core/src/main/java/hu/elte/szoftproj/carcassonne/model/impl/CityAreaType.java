package hu.elte.szoftproj.carcassonne.model.impl;

import hu.elte.szoftproj.carcassonne.model.Area;
import hu.elte.szoftproj.carcassonne.model.AreaType;

public class CityAreaType extends AbstractAreaType implements AreaType {

	@Override
	public String getName() {
		return "city";
	}

	@Override
	public int getScore(Area a) {
		// TODO Auto-generated method stub
		return 0;
	}

}
