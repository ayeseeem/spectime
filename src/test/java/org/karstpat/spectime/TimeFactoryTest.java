package org.karstpat.spectime;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeFactoryTest {

	@Test
	public void testTime() {
		assertEquals(new DtNumber(1), TimeFactory.time(1));
		assertEquals(new DtNumber(2), TimeFactory.time(2));
	}

	@Test
	public void testDate() {
		assertEquals(new DtNumber(1), TimeFactory.date(1));
		assertEquals(new DtNumber(2), TimeFactory.date(2));
	}

	@Test
	public void testTimeAndDateAreSynonyms() {
		assertEquals(TimeFactory.date(123), TimeFactory.time(123));
	}

}
