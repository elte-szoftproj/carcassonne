package hu.elte.szoftproj.carcassonne.model.impl;

import hu.elte.szoftproj.carcassonne.model.AreaType;

/**
 * Kozos ososztaly a terulettipusoknak, ami lekezeli az egyenloseget.
 * @author Zsolt
 *
 */
public abstract class AbstractAreaType implements AreaType {

	public AbstractAreaType() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AreaType)) {
			return false;
		}
		return getName().equals(((AreaType)obj).getName());
	}

}