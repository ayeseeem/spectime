package org.ayeseeem.spectime.example;

import static org.ayeseeem.spectime.TimeFactory.date;
import static org.ayeseeem.spectime.TimeFactory.now;
import static org.ayeseeem.spectime.TimeFactory.startOf;
import static org.ayeseeem.spectime.TimeFactory.time;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

import java.util.Date;

import org.ayeseeem.spectime.test.junit.OtherTimeZone;
import org.junit.Rule;
import org.junit.Test;

public class ExamplesTest {

	@Rule
	public OtherTimeZone otherTz = new OtherTimeZone();

	@Test
	public void examplesAnd() {
		assertThat(time(3).seconds().and(2).milliseconds().from(new Date(1000 * t1Second)),
				is(new Date(1003 * t1Second + 2)));
		assertThat(time(3).minutes().and(2).seconds().and(1).millisecond().from(new Date(1000)),
				is(new Date(1000 + 3 * t1Minute + 2 * t1Second + 1)));

		assertThat(date(3).years().and(2).months().and(1).day().from(date("2010-03-10")),
				is(date("2013-05-11")));
	}

	@Test
	public void examplesStartOf() {
		assertThat(startOf(date("2013-03-10 23:58:59.123")),
				is(date("2013-03-10")));
		assertThat(startOf(date("2013-03-10 00:00:00.000")),
				is(date("2013-03-10")));
	}

	@Test
	public void examplesMilliseconds() {
		assertThat(time(234).milliseconds().from(new Date(1000)),
				is(new Date(1234)));
		assertThat(time(1).millisecond().from(new Date(1000)),
				is(new Date(1001)));

		assertThat(time(234).milliseconds().after(new Date(1000)),
				is(new Date(1234)));
		assertThat(time(1).millisecond().after(new Date(1000)),
				is(new Date(1001)));

		assertThat(time(234).milliseconds().before(new Date(1234)),
				is(new Date(1000)));
		assertThat(time(1).millisecond().before(new Date(1001)),
				is(new Date(1000)));
	}

	@Test
	public void examplesNow() {
		assertThat(now(), is(new Date()));
	}

	@Test
	public void examplesAgo() {
		int testDiffMillis = 100;

		assertThat((double) time(7).minutes().ago().getTime(),
				is(closeTo(new Date(new Date().getTime() - 7 * 60 * 1000).getTime(), testDiffMillis)));
	}

	private final long t1Second = 1000;

	@Test
	public void examplesSeconds() {
		assertThat(time(234).seconds().from(new Date(1000 * t1Second)),
				is(new Date(1234 * t1Second)));
		assertThat(time(1).second().from(new Date(1000 * t1Second)),
				is(new Date(1001 * t1Second)));

		assertThat(time(234).seconds().before(new Date(1234 * t1Second)),
				is(new Date(1000 * t1Second)));
		assertThat(time(1).second().before(new Date(1001 * t1Second)),
				is(new Date(1000 * t1Second)));
	}

	private final long t1Minute = 60 * t1Second;

	@Test
	public void examplesMinutes() {
		assertThat(time(234).minutes().from(new Date(1000 * t1Minute)),
				is(new Date(1234 * t1Minute)));
		assertThat(time(1).minute().from(new Date(1000 * t1Minute)),
				is(new Date(1001 * t1Minute)));

		assertThat(time(234).minutes().before(new Date(1234 * t1Minute)),
				is(new Date(1000 * t1Minute)));
		assertThat(time(1).minute().before(new Date(1001 * t1Minute)),
				is(new Date(1000 * t1Minute)));
	}

	private long t1Hour = 60 * t1Minute;

	@Test
	public void examplesHours() {
		assertThat(time(234).hours().from(new Date(1000 * t1Hour)),
				is(new Date(1234 * t1Hour)));
		assertThat(time(1).hour().from(new Date(1000 * t1Hour)),
				is(new Date(1001 * t1Hour)));

		assertThat(time(234).hours().before(new Date(1234 * t1Hour)),
				is(new Date(1000 * t1Hour)));
		assertThat(time(1).hour().before(new Date(1001 * t1Hour)),
				is(new Date(1000 * t1Hour)));
	}

	private long t1Day = 24 * t1Hour;

	@Test
	public void examplesDays() {
		assertThat(date(234).days().from(new Date(1000 * t1Day)),
				is(new Date(1234 * t1Day)));
		assertThat(date(1).day().from(new Date(1000 * t1Day)),
				is(new Date(1001 * t1Day)));

		assertThat(date(234).days().before(new Date(1234 * t1Day)),
				is(new Date(1000 * t1Day)));
		assertThat(date(1).day().before(new Date(1001 * t1Day)),
				is(new Date(1000 * t1Day)));
	}

	@Test
	public void examplesWeeks() {
		assertThat(date(2).weeks().from(date("2013-03-09")),
				is(date("2013-03-23")));
		assertThat(date(1).week().from(date("2013-03-16")),
				is(date("2013-03-23")));

		// end of month
		assertThat(date(1).week().from(date("2013-04-27")),
				is(date("2013-05-04")));

		assertThat(date(2).weeks().before(date("2013-03-23")),
				is(date("2013-03-09")));
		assertThat(date(1).week().before(date("2013-03-16")),
				is(date("2013-03-09")));
	}

	@Test
	public void examplesMonths() {
		assertThat(date(2).months().from(date("2013-03-09")),
				is(date("2013-05-09")));
		assertThat(date(1).month().from(date("2013-12-01")),
				is(date("2014-01-01")));

		// normal Java Date wrapping of month where number of days in month is
		// too big
		assertThat(date(1).month().from(date("2013-01-30")),
				is(date("2013-02-28")));

		assertThat(date(2).months().before(date("2013-05-09")),
				is(date("2013-03-09")));
		assertThat(date(1).month().before(date("2014-01-01")),
				is(date("2013-12-01")));

		// normal Java Date wrapping of month where number of days in month is
		// too big
		assertThat(date(1).month().before(date("2013-02-28")),
				is(date("2013-01-28")));
	}

	@Test
	public void examplesYears() {
		assertThat(date(2).years().from(date("2013-05-09")),
				is(date("2015-05-09")));
		assertThat(date(1).year().from(date("2013-05-09")),
				is(date("2014-05-09")));

		assertThat(date(2).years().before(date("2015-05-09")),
				is(date("2013-05-09")));
		assertThat(date(1).year().before(date("2014-05-09")),
				is(date("2013-05-09")));
	}

}
