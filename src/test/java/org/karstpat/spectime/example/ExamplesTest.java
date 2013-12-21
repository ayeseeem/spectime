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

	private final int t1Second = 1000;

	@Test
	public void examplesSeconds() {
		assertEquals(new Date(1234 * t1Second),
				time(234).seconds().from(new Date(1000 * t1Second)));
		assertEquals(new Date(1001 * t1Second),
				time(1).second().from(new Date(1000 * t1Second)));
	}

	private final int t1Minute = 60 * t1Second;

	@Test
	public void examplesMinutes() {
		assertEquals(new Date(1234 * t1Minute),
				time(234).minutes().from(new Date(1000 * t1Minute)));
		assertEquals(new Date(1001 * t1Minute),
				time(1).minute().from(new Date(1000 * t1Minute)));
	}

	private int t1Hour = 60 * t1Minute;

	@Test
	public void examplesHours() {
		assertEquals(new Date(1234 * t1Hour),
				time(234).hours().from(new Date(1000 * t1Hour)));
		assertEquals(new Date(1001 * t1Hour),
				time(1).hour().from(new Date(1000 * t1Hour)));
	}

}
