package hu.elte.szoftproj.carcassonne.model.impl;

import static org.junit.Assert.*;
import hu.elte.szoftproj.carcassonne.model.Deck;

import org.junit.Before;
import org.junit.Test;

public class BasicDeckTest {

	Deck deck;
	
	@Before
	public void setUp() throws Exception {
		deck = new BasicDeck();
	}

	@Test
	public void testStartTile() {
		assertEquals("city1rwe", deck.getStarterTile().getName());
	}

	@Test
	public void testDrawing() {
		int prev = deck.getRemainingPieceCount(); // assert terminate
		while (deck.getRemainingPieceCount() > 0) {
			assertNotNull(deck.drawTile());
			assertEquals(prev-1, deck.getRemainingPieceCount());
			prev = deck.getRemainingPieceCount();
		}
	}
}
