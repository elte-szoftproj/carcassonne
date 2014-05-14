package hu.elte.szoftproj.carcassonne.model.impl;

import hu.elte.szoftproj.carcassonne.model.Area;

/**
 * A mezo terulettipust megvalosito osztaly.
 * 
 * @author Zsolt
 *
 */
public class FieldAreaType extends AbstractAreaType {

	@Override
	public String getName() {
		return "field";
	}

	@Override
	public int getScore(Area a) {
		// TODO Auto-generated method stub
		return 0;
	}

}
