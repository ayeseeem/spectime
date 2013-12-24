package org.karstpat.spectime;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

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

	@Test
	public void dateFormatString() {
		assertEquals("yyyy-MM-dd", TimeFactory.DATE_FORMAT);
	}

	@Test
	public void testDate_FromString() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09"));

		assertEquals(2013, cal.get(Calendar.YEAR));
		assertEquals(Calendar.MARCH, cal.get(Calendar.MONTH));
		assertEquals(9, cal.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testDate_FromString_ZeroesTimeFields() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09"));

		assertEquals(0, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, cal.get(Calendar.MINUTE));
		assertEquals(0, cal.get(Calendar.SECOND));
		assertEquals(0, cal.get(Calendar.MILLISECOND));
	}

	@Test
	public void testNow() {
		assertEquals(new Date(), TimeFactory.now());
	}

}
