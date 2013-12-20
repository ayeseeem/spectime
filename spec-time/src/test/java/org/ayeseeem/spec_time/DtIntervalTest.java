package org.ayeseeem.spec_time;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DtIntervalTest {

	@Test
	public void testFrom() {
		DtInterval test = new DtInterval(123, Calendar.MILLISECOND);
		assertEquals(new Date(new Date().getTime() + 123),
				test.fromNow());
	}

	@Test
	public void testFromNow() {
		DtInterval test = new DtInterval(123, Calendar.MILLISECOND);
		assertEquals(test.from(new Date()), test.fromNow());
	}

}
