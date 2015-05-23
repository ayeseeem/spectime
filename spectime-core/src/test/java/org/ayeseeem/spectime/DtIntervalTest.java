package org.ayeseeem.spectime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

	@Test
	public void testAgo() {
		DtInterval test = new DtInterval(123, Calendar.MILLISECOND, constDateSource);
		assertEquals(new Date(constNow.getTime() - 123), test.ago());
	}

	@Test
	public void testAgoIsSynonymForBeforeNow() {
		DtInterval test = new DtInterval(123, Calendar.MILLISECOND, constDateSource);
		assertEquals(test.before(constNow), test.ago());
	}

	@Test
	public void testAnd() {
		DtInterval test = new DtInterval(7, Calendar.SECOND);
		test = test.and(123).milliseconds();

		assertEquals(new Date(8123), test.after(new Date(1000)));
	}

	@Test
	public void testAnd_MultipleChains() {
		DtInterval test = new DtInterval(1, Calendar.MILLISECOND);
		test = test.and(2).milliseconds();
		test = test.and(3).milliseconds();

		assertEquals(new Date(1006), test.after(new Date(1000)));
	}

	@Test
	public void testAnd_MultipleChains_DifferentUnits() {
		DtInterval test = new DtInterval(1, Calendar.MILLISECOND);
		test = test.and(2).seconds();
		test = test.and(3).minutes();

		assertEquals(new Date(1000
				+ 1
				+ 2 * 1000
				+ 3 * (60 * 1000)),
				test.after(new Date(1000)));
	}

	@Test
	public void testEquals() {
		assertEquals(new DtInterval(123, Calendar.MILLISECOND), new DtInterval(123, Calendar.MILLISECOND));
		assertFalse(new DtInterval(123, Calendar.MILLISECOND).equals(new DtInterval(999, Calendar.MILLISECOND)));
		assertFalse(new DtInterval(123, Calendar.MILLISECOND).equals(new DtInterval(123, Calendar.HOUR)));
		assertFalse(new DtInterval(123, Calendar.MILLISECOND).equals(new String("999")));
	}

	@Test
	public void testEquals_WithPrevious() {
		DtInterval dtInterval1 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval1.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		DtInterval dtInterval2 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval2.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		assertEquals(dtInterval1, dtInterval2);
	}

	@Test
	public void testEquals_WithPrevious_NotEqual() {
		DtInterval dtInterval1 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval1.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		DtInterval dtInterval2 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval2.setPrevious(new DtInterval(999, Calendar.MILLISECOND));

		assertFalse(dtInterval1.equals(dtInterval2));
	}

	@Test
	public void testEquals_WithPrevious_NotEqual_NoPrevious() {
		DtInterval dtInterval1 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval1.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		DtInterval dtIntervalWithNoPrevious = new DtInterval(123, Calendar.MILLISECOND);

		assertFalse(dtInterval1.equals(dtIntervalWithNoPrevious));
	}

	@Test
	public void testEquals_WithPrevious_EquivalentChainedAnds() {
		DtInterval dtInterval1 = new DtInterval(1, Calendar.MILLISECOND);
		dtInterval1 = dtInterval1.and(2).milliseconds();
		dtInterval1 = dtInterval1.and(3).milliseconds();

		DtInterval dtInterval2 = new DtInterval(3, Calendar.MILLISECOND);
		dtInterval2 = dtInterval2.and(1).milliseconds();
		dtInterval2 = dtInterval2.and(2).milliseconds();

		// HACK: current way to test equivalent chains is to apply them
		assertEquals(dtInterval1.after(new Date(0)), dtInterval2.after(new Date(0)));
		// TODO: make this work explicitly, by applying chains internally in equals/hashcode
		//assertEquals(dtInterval1, dtInterval2);
	}

	private final Date constNow = new Date();

	private final DateSource constDateSource = new DateSource() {
		@Override
		public Date getDate() {
			return constNow;
		}
	};
}
