package org.ayeseeem.spectime;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TimeFactoryTest {

	@Test
	public void testTime() {
		assertThat(TimeFactory.time(1), is(new DtNumber(1)));
		assertThat(TimeFactory.time(2), is(new DtNumber(2)));
	}

	@Test
	public void testDate() {
		assertThat(TimeFactory.date(1), is(new DtNumber(1)));
		assertThat(TimeFactory.date(2), is(new DtNumber(2)));
	}

	@Test
	public void testTimeAndDateAreSynonyms() {
		assertThat(TimeFactory.time(123), is(TimeFactory.date(123)));
	}

	@Test
	public void dateStrings() {
		assertThat(TimeFactory.DATE_FORMAT, is("yyyy-MM-dd"));
		assertThat(TimeFactory.DATE_WITH_MINUTES_FORMAT, is("yyyy-MM-dd HH:mm"));
		assertThat(TimeFactory.DATE_WITH_SECONDS_FORMAT, is("yyyy-MM-dd HH:mm:ss"));
		assertThat(TimeFactory.DATE_WITH_MILLIS_FORMAT, is("yyyy-MM-dd HH:mm:ss.SSS"));
	}

	@Test
	public void testDate_FromString_DayOnly() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09"));

		assertThat(cal.get(YEAR), is(2013));
		assertThat(cal.get(MONTH), is(MARCH));
		assertThat(cal.get(DAY_OF_MONTH), is(9));
	}

	@Test
	public void testDate_FromString_ZeroesTimeFields() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09"));

		assertThat(cal.get(HOUR_OF_DAY), is(0));
		assertThat(cal.get(MINUTE), is(0));
		assertThat(cal.get(SECOND), is(0));
		assertThat(cal.get(MILLISECOND), is(0));
	}

	@Test
	public void testDate_FromString_WithMinutes() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09 23:59"));

		assertThat(cal.get(YEAR), is(2013));
		assertThat(cal.get(MONTH), is(MARCH));
		assertThat(cal.get(DAY_OF_MONTH), is(9));

		assertThat(cal.get(HOUR_OF_DAY), is(23));
		assertThat(cal.get(MINUTE), is(59));

		assertThat(cal.get(SECOND), is(0));
		assertThat(cal.get(MILLISECOND), is(0));
	}

	@Test
	public void testDate_FromString_WithSeconds() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09 23:59:58"));

		assertThat(cal.get(YEAR), is(2013));
		assertThat(cal.get(MONTH), is(MARCH));
		assertThat(cal.get(DAY_OF_MONTH), is(9));

		assertThat(cal.get(HOUR_OF_DAY), is(23));
		assertThat(cal.get(MINUTE), is(59));
		assertThat(cal.get(SECOND), is(58));

		assertThat(cal.get(MILLISECOND), is(0));
	}

	@Test
	public void testDate_FromString_WithMillis() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09 23:59:58.123"));

		assertThat(cal.get(YEAR), is(2013));
		assertThat(cal.get(MONTH), is(MARCH));
		assertThat(cal.get(DAY_OF_MONTH), is(9));

		assertThat(cal.get(HOUR_OF_DAY), is(23));
		assertThat(cal.get(MINUTE), is(59));
		assertThat(cal.get(SECOND), is(58));

		assertThat(cal.get(MILLISECOND), is(123));
	}

	@Test
	public void testNow() {
		final int timeDiffMillis = 100;
		assertThat((double) TimeFactory.now().getTime(), is(closeTo(new Date().getTime(), timeDiffMillis)));
	}

	@Test
	public void testStartOf() {
		Calendar cal = Calendar.getInstance();
		Date original = TimeFactory.date("2013-03-09 23:59:58.123");
		Date start = TimeFactory.startOf(original);
		cal.setTime(start);

		// Date (day) info is unchanged
		assertThat(cal.get(YEAR), is(2013));
		assertThat(cal.get(MONTH), is(MARCH));
		assertThat(cal.get(DAY_OF_MONTH), is(9));

		// Time fields are zeroed
		assertThat(cal.get(HOUR_OF_DAY), is(0));
		assertThat(cal.get(MINUTE), is(0));
		assertThat(cal.get(SECOND), is(0));
		assertThat(cal.get(MILLISECOND), is(0));
	}

	@Test
	public void testZeroTime_ZeroesTheTimeFields() {
		Calendar cal = Calendar.getInstance();

		cal.set(HOUR_OF_DAY, 23);
		cal.set(MINUTE, 58);
		cal.set(SECOND, 59);
		cal.set(MILLISECOND, 123);

		TimeFactory.zeroTime(cal);

		assertThat(cal.get(HOUR_OF_DAY), is(0));
		assertThat(cal.get(MINUTE), is(0));
		assertThat(cal.get(SECOND), is(0));
		assertThat(cal.get(MILLISECOND), is(0));
	}

	@Test
	public void testZeroTime_DateDayInfoIsUnchanged() {
		Calendar cal = Calendar.getInstance();

		cal.set(YEAR, 2013);
		cal.set(MONTH, MARCH);
		cal.set(DAY_OF_MONTH, 9);

		TimeFactory.zeroTime(cal);

		assertThat(cal.get(YEAR), is(2013));
		assertThat(cal.get(MONTH), is(MARCH));
		assertThat(cal.get(DAY_OF_MONTH), is(9));
	}

}
