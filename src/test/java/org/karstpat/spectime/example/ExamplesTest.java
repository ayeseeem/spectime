package org.karstpat.spectime.example;

import static org.junit.Assert.assertEquals;
import static org.karstpat.spectime.TimeFactory.time;

import java.util.Date;

import org.junit.Test;

public class ExamplesTest {

	@Test
	public void examplesMilliseconds() {
		assertEquals(new Date(1234),
				time(234).milliseconds().from(new Date(1000)));
		assertEquals(new Date(1001),
				time(1).millisecond().from(new Date(1000)));
	}

	@Test
	public void examplesFromNow() {
		assertEquals(new Date(new Date().getTime() + 10),
				time(10).milliseconds().fromNow());
	}

	@Test
	public void examplesSeconds() {
		assertEquals(new Date(1234 * 1000),
				time(234).seconds().from(new Date(1000 * 1000)));
		assertEquals(new Date(1001 * 1000),
				time(1).second().from(new Date(1000 * 1000)));
	}

}
