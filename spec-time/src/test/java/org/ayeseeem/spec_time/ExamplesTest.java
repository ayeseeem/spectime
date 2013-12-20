package org.ayeseeem.spec_time;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class ExamplesTest {

	@Test
	public void testExamples() {
		assertEquals(new Date(1), TimeFactory.time(1).millisecond().from(new Date(0)));
		assertEquals(new Date(1234), TimeFactory.time(234).millisecond().from(new Date(1000)));
	}

}
