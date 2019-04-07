package org.ayeseeem.spectime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DtIntervalTest {

	@Test
	public void testFrom() {
		DtInterval test = new DtInterval(234, Calendar.MILLISECOND);
		assertThat(test.from(new Date(1000)), is(new Date(1234)));
	}

	@Test
	public void testAfter() {
		DtInterval test = new DtInterval(234, Calendar.MILLISECOND);
		assertThat(test.after(new Date(1000)), is(new Date(1234)));
	}

	@Test
	public void testAfterIsSynonymForFrom() {
		DtInterval test = new DtInterval(123, Calendar.MILLISECOND);
		assertThat(test.after(new Date(1000)), is(test.from(new Date(1000))));
	}

	@Test
	public void testBefore() {
		DtInterval test = new DtInterval(234, Calendar.MILLISECOND);
		assertThat(test.before(new Date(1234)), is(new Date(1000)));
	}

	@Test
	public void testAgo() {
		DtInterval test = new DtInterval(123, Calendar.MILLISECOND, constDateSupplier);
		assertThat(test.ago(), is(new Date(constNow.getTime() - 123)));
	}

	@Test
	public void testAgoIsSynonymForBeforeNow() {
		DtInterval test = new DtInterval(123, Calendar.MILLISECOND, constDateSupplier);
		assertThat(test.ago(), is(test.before(constNow)));
	}

	@Test
	public void testAnd() {
		DtInterval test = new DtInterval(7, Calendar.SECOND);
		test = test.and(123).milliseconds();

		assertThat(test.after(new Date(1000)), is(new Date(8123)));
	}

	@Test
	public void testAnd_MultipleChains() {
		DtInterval test = new DtInterval(1, Calendar.MILLISECOND);
		test = test.and(2).milliseconds();
		test = test.and(3).milliseconds();

		assertThat(test.after(new Date(1000)), is(new Date(1006)));
	}

	@Test
	public void testAnd_MultipleChains_DifferentUnits() {
		DtInterval test = new DtInterval(1, Calendar.MILLISECOND);
		test = test.and(2).seconds();
		test = test.and(3).minutes();

		assertThat(test.after(new Date(1000)),
				is(new Date(1000
						+ 1
						+ 2 * 1000
						+ 3 * (60 * 1000))));
	}

	@Test
	public void testEquals() {
		assertThat(new DtInterval(123, Calendar.MILLISECOND).equals(new DtInterval(123, Calendar.MILLISECOND)),
				is(true));

		assertThat(new DtInterval(123, Calendar.MILLISECOND).equals(new DtInterval(456, Calendar.MILLISECOND)),
				is(false));
		assertThat(new DtInterval(123, Calendar.MILLISECOND).equals(new DtInterval(123, Calendar.HOUR)),
				is(false));

		assertThat(new DtInterval(123, Calendar.MILLISECOND).equals(new String("456")),
				is(false));
	}

	@Test
	public void testEquals_Self() {
		DtInterval test = new DtInterval(123, Calendar.MILLISECOND);
		assertThat(test.equals(test), is(true));
	}

	@Test
	public void testEquals_WorksWithJunit() {
		assertEquals(new DtInterval(123, Calendar.MILLISECOND), new DtInterval(123, Calendar.MILLISECOND));
		assertNotEquals(new DtInterval(123, Calendar.MILLISECOND), new DtInterval(456, Calendar.MILLISECOND));

		assertThat(new DtInterval(123, Calendar.MILLISECOND), is(new DtInterval(123, Calendar.MILLISECOND)));
		assertThat(new DtInterval(123, Calendar.MILLISECOND), is(not(new DtInterval(456, Calendar.MILLISECOND))));
	}

	@Test
	public void testEquals_WithPrevious() {
		DtInterval dtInterval1 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval1.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		DtInterval dtInterval2 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval2.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		assertThat(dtInterval2, is(dtInterval1));
		assertThat(dtInterval2.equals(dtInterval1), is(true));
		assertThat(dtInterval1.equals(dtInterval2), is(true));
	}

	@Test
	public void testEquals_WithPrevious_NotEqual() {
		DtInterval dtInterval1 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval1.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		DtInterval dtInterval2 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval2.setPrevious(new DtInterval(222, Calendar.MILLISECOND));

		assertThat(dtInterval1.equals(dtInterval2), is(false));
		assertThat(dtInterval2.equals(dtInterval1), is(false));
	}

	@Test
	public void testEquals_WithPrevious_NotEqual_NoPrevious() {
		DtInterval dtInterval1 = new DtInterval(123, Calendar.MILLISECOND);
		dtInterval1.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		DtInterval dtIntervalWithNoPrevious = new DtInterval(123, Calendar.MILLISECOND);

		assertThat(dtInterval1.equals(dtIntervalWithNoPrevious), is(false));
		assertThat(dtIntervalWithNoPrevious.equals(dtInterval1), is(false));
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
		assertThat(dtInterval2.after(new Date(0)), is(dtInterval1.after(new Date(0))));
		// TODO: make this work explicitly, by applying chains internally in equals/hashcode
		//assertThat(dtInterval2, is(dtInterval1));
	}

	@Test
	public void testCreateWithPrevious() {
		DtInterval previous = new DtInterval(222, Calendar.MILLISECOND);
		DtInterval result = DtInterval.createWithPrevious(111, Calendar.MILLISECOND, previous);

		assertThat(result.after(new Date(0)).getTime(), is(111L + 222L));
		assertThat(result.after(new Date(123456)).getTime(), is(123456L + 111L + 222L));
	}

	@Test
	public void testCreateWithPrevious_WhenPreviousIsNull() {
		DtInterval previous = null;
		DtInterval result = DtInterval.createWithPrevious(111, Calendar.MILLISECOND, previous);

		assertThat(result.after(new Date(0)).getTime(), is(111L + 0));
		assertThat(result.after(new Date(123456)).getTime(), is(123456L + 111L + 0));
	}

	private final Date constNow = new Date();

	private final DateSupplier constDateSupplier = new DateSupplier() {
		@Override
		public Date get() {
			return constNow;
		}
	};

}
