package hu.elte.szoftproj.carcassonne.model.impl;

import static org.junit.Assert.*;
import hu.elte.szoftproj.carcassonne.model.Place;

import org.junit.Before;
import org.junit.Test;

public class BasicTileTest {

	BasicTile t;
	
	@Before
	public void constructTile() {
		t = new BasicTile();
		t.addSlot("C", new Place[]{ Place.TOP, Place.TOP_LEFT_TOP, Place.TOP_RIGHT_TOP });
		t.addSlot("R", new Place[]{ Place.CENTER, Place.BOTTOM });
		t.addSlot("G", new Place[]{ Place.LEFT, Place.BOTTOM_LEFT_BOTTOM, Place.BOTTOM_LEFT_LEFT, Place.TOP_LEFT_LEFT });
		t.addSlot("G", new Place[]{ Place.RIGHT, Place.BOTTOM_RIGHT_BOTTOM, Place.BOTTOM_RIGHT_RIGHT, Place.TOP_RIGHT_RIGHT });
	}
	
	@Test
	public void testGetSide() {
		assertEquals("C", t.getSide(Place.TOP).getSlotType());
		assertEquals("C", t.getSide(Place.TOP_LEFT_TOP).getSlotType());
		assertEquals("C", t.getSide(Place.TOP_RIGHT_TOP).getSlotType());
		assertEquals(t.getSide(Place.TOP_LEFT_TOP), t.getSide(Place.TOP));
		assertNotSame(t.getSide(Place.LEFT), t.getSide(Place.RIGHT));
		assertEquals(4, t.getSideList().size());
	}
	
	@Test
	public void testCanPlaceType() {
		assertTrue(t.canPlacePieceType("C"));
		assertTrue(t.canPlacePieceType("R"));
		assertTrue(t.canPlacePieceType("G"));
		assertFalse(t.canPlacePieceType("T"));
	}

}
