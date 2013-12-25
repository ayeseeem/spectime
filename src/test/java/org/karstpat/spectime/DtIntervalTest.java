package org.karstpat.spectime;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DtIntervalTest {

	@Test
	public void testFrom() {
		DtInterval test = new DtInterval(234, Calendar.MILLISECOND);
		assertEquals(new Date(1234),
				test.from(new Date(1000)));
	}

	@Test
	public void testAfter() {
		DtInterval test = new DtInterval(234, Calendar.MILLISECOND);
		assertEquals(new Date(1234),
				test.after(new Date(1000)));
	}

	@Test
	public void testAfterIsSynonymForFrom() {
		DtInterval test = new DtInterval(123, Calendar.MILLISECOND);
		assertEquals(test.from(new Date(1000)), test.after(new Date(1000)));
	}

	@Test
	public void testBefore() {
		DtInterval test = new DtInterval(234, Calendar.MILLISECOND);
		assertEquals(new Date(1000),
				test.before(new Date(1234)));
	}

}
