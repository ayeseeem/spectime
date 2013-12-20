package org.ayeseeem.spec_time.example;

import static org.ayeseeem.spec_time.TimeFactory.time;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class ExamplesTest {

	@Test
	public void testExamples() {
		assertEquals(new Date(1234),
				time(234).milliseconds().from(new Date(1000)));
		assertEquals(new Date(1001),
				time(1).millisecond().from(new Date(1000)));

		assertEquals(new Date(new Date().getTime() + 10),
				time(10).millisecond().fromNow());
	}

}
