package hu.elte.szoftproj.carcassonne.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SampleTest {

	@Test
	public void testGetHello() {
		assertEquals("Hello World!", new Sample().getHello());
	}

}
