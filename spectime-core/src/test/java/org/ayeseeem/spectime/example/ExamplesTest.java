package org.ayeseeem.spectime.example;

import static org.ayeseeem.spectime.TimeFactory.date;
import static org.ayeseeem.spectime.TimeFactory.now;
import static org.ayeseeem.spectime.TimeFactory.startOf;
import static org.ayeseeem.spectime.TimeFactory.time;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class ExamplesTest {

	@Test
	public void examplesAnd() {
		assertEquals(new Date(1003 * t1Second + 2),
				time(3).seconds().and(2).milliseconds().from(new Date(1000 * t1Second)));
		assertEquals(new Date(1000 + 3 * t1Minute + 2 * t1Second + 1),
				time(3).minutes().and(2).seconds().and(1).millisecond().from(new Date(1000)));

		assertEquals(date("2013-05-11"),
				date(3).years().and(2).months().and(1).day().from(date("2010-03-10")));
	}

	@Test
	public void examplesStartOf() {
		assertEquals(date("2013-03-10"),
				startOf(date("2013-03-10 23:58:59.123")));
	}

	@Test
	public void examplesMilliseconds() {
		assertEquals(new Date(1234),
				time(234).milliseconds().from(new Date(1000)));
		assertEquals(new Date(1001),
				time(1).millisecond().from(new Date(1000)));

		assertEquals(new Date(1234),
				time(234).milliseconds().after(new Date(1000)));
		assertEquals(new Date(1001),
				time(1).millisecond().after(new Date(1000)));

		assertEquals(new Date(1000),
				time(234).milliseconds().before(new Date(1234)));
		assertEquals(new Date(1000),
				time(1).millisecond().before(new Date(1001)));
	}

	@Test
	public void examplesNow() {
		assertEquals(new Date(),
				now());
	}

	@Test
	public void examplesAgo() {
		assertEquals(new Date(new Date().getTime() - 10),
				time(10).milliseconds().ago());
	}

	private final long t1Second = 1000;

	@Test
	public void examplesSeconds() {
		assertEquals(new Date(1234 * t1Second),
				time(234).seconds().from(new Date(1000 * t1Second)));
		assertEquals(new Date(1001 * t1Second),
				time(1).second().from(new Date(1000 * t1Second)));

		assertEquals(new Date(1000 * t1Second),
				time(234).seconds().before(new Date(1234 * t1Second)));
		assertEquals(new Date(1000 * t1Second),
				time(1).second().before(new Date(1001 * t1Second)));
	}

	private final long t1Minute = 60 * t1Second;

	@Test
	public void examplesMinutes() {
		assertEquals(new Date(1234 * t1Minute),
				time(234).minutes().from(new Date(1000 * t1Minute)));
		assertEquals(new Date(1001 * t1Minute),
				time(1).minute().from(new Date(1000 * t1Minute)));

		assertEquals(new Date(1000 * t1Minute),
				time(234).minutes().before(new Date(1234 * t1Minute)));
		assertEquals(new Date(1000 * t1Minute),
				time(1).minute().before(new Date(1001 * t1Minute)));
	}

	private long t1Hour = 60 * t1Minute;

	@Test
	public void examplesHours() {
		assertEquals(new Date(1234 * t1Hour),
				time(234).hours().from(new Date(1000 * t1Hour)));
		assertEquals(new Date(1001 * t1Hour),
				time(1).hour().from(new Date(1000 * t1Hour)));

		assertEquals(new Date(1000 * t1Hour),
				time(234).hours().before(new Date(1234 * t1Hour)));
		assertEquals(new Date(1000 * t1Hour),
				time(1).hour().before(new Date(1001 * t1Hour)));
	}

	private long t1Day = 24 * t1Hour;

	@Test
	public void examplesDays() {
		assertEquals(new Date(1234 * t1Day),
				date(234).days().from(new Date(1000 * t1Day)));
		assertEquals(new Date(1001 * t1Day),
				date(1).day().from(new Date(1000 * t1Day)));

		assertEquals(new Date(1000 * t1Day),
				date(234).days().before(new Date(1234 * t1Day)));
		assertEquals(new Date(1000 * t1Day),
				date(1).day().before(new Date(1001 * t1Day)));
	}

	@Test
	public void examplesWeeks() {
		assertEquals(date("2013-03-23"),
				date(2).weeks().from(date("2013-03-09")));
		assertEquals(date("2013-03-23"),
				date(1).week().from(date("2013-03-16")));

		// end of month
		assertEquals(date("2013-05-04"),
				date(1).week().from(date("2013-04-27")));

		assertEquals(date("2013-03-09"),
				date(2).weeks().before(date("2013-03-23")));
		assertEquals(date("2013-03-09"),
				date(1).week().before(date("2013-03-16")));
	}

	@Test
	public void examplesMonths() {
		assertEquals(date("2013-05-09"),
				date(2).months().from(date("2013-03-09")));
		assertEquals(date("2014-01-01"),
				date(1).month().from(date("2013-12-01")));

		// normal Java Date wrapping of month where number of days in month is
		// too big
		assertEquals(date("2013-02-28"),
				date(1).month().from(date("2013-01-30")));

		assertEquals(date("2013-03-09"),
				date(2).months().before(date("2013-05-09")));
		assertEquals(date("2013-12-01"),
				date(1).month().before(date("2014-01-01")));

		// normal Java Date wrapping of month where number of days in month is
		// too big
		assertEquals(date("2013-01-28"),
				date(1).month().before(date("2013-02-28")));

	}

	@Test
	public void examplesYears() {
		assertEquals(date("2015-05-09"),
				date(2).years().from(date("2013-05-09")));
		assertEquals(date("2014-05-09"),
				date(1).year().from(date("2013-05-09")));

		assertEquals(date("2013-05-09"),
				date(2).years().before(date("2015-05-09")));
		assertEquals(date("2013-05-09"),
				date(1).year().before(date("2014-05-09")));
	}

}