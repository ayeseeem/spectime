package org.ayeseeem.spectime.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.TimeZone;

import org.junit.Test;

public class TimeZoneFactoryTest {

	@Test
	public void testRawDifference() {
		TimeZone zone = TimeZone.getTimeZone("GMT+05:00");
		TimeZone other = TimeZone.getTimeZone("GMT+08:00");

		assertThat(TimeZoneFactory.rawDifference(zone, other), is((8 - 5) * 60 * 60 * 1000));
	}

}
