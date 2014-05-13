package hu.elte.szoftproj.carcassonne.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlaceTest {

	@Test
	public void testRotate() {
		assertEquals(Place.TOP, Place.TOP.rotateCw(Rotation.D270).rotateCw(Rotation.D90));
		assertEquals(Place.RIGHT, Place.TOP.rotateCw(Rotation.D90));
		assertEquals(Place.TOP_RIGHT_TOP, Place.TOP_LEFT_LEFT.rotateCw(Rotation.D90));
	}
	
	@Test
	public void testOpposition() {
		assertEquals(Place.BOTTOM, Place.TOP.opposite());
		assertEquals(Place.TOP, Place.TOP.opposite().opposite());
	}

}
