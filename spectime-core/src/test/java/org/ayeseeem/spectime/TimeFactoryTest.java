package org.ayeseeem.spectime;

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

		assertThat(cal.get(Calendar.YEAR), is(2013));
		assertThat(cal.get(Calendar.MONTH), is(Calendar.MARCH));
		assertThat(cal.get(Calendar.DAY_OF_MONTH), is(9));
	}

	@Test
	public void testDate_FromString_ZeroesTimeFields() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09"));

		assertThat(cal.get(Calendar.HOUR_OF_DAY), is(0));
		assertThat(cal.get(Calendar.MINUTE), is(0));
		assertThat(cal.get(Calendar.SECOND), is(0));
		assertThat(cal.get(Calendar.MILLISECOND), is(0));
	}

	@Test
	public void testDate_FromString_WithMinutes() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09 23:59"));

		assertThat(cal.get(Calendar.YEAR), is(2013));
		assertThat(cal.get(Calendar.MONTH), is(Calendar.MARCH));
		assertThat(cal.get(Calendar.DAY_OF_MONTH), is(9));

		assertThat(cal.get(Calendar.HOUR_OF_DAY), is(23));
		assertThat(cal.get(Calendar.MINUTE), is(59));

		assertThat(cal.get(Calendar.SECOND), is(0));
		assertThat(cal.get(Calendar.MILLISECOND), is(0));
	}

	@Test
	public void testDate_FromString_WithSeconds() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09 23:59:58"));

		assertThat(cal.get(Calendar.YEAR), is(2013));
		assertThat(cal.get(Calendar.MONTH), is(Calendar.MARCH));
		assertThat(cal.get(Calendar.DAY_OF_MONTH), is(9));

		assertThat(cal.get(Calendar.HOUR_OF_DAY), is(23));
		assertThat(cal.get(Calendar.MINUTE), is(59));
		assertThat(cal.get(Calendar.SECOND), is(58));

		assertThat(cal.get(Calendar.MILLISECOND), is(0));
	}

	@Test
	public void testDate_FromString_WithMillis() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TimeFactory.date("2013-03-09 23:59:58.123"));

		assertThat(cal.get(Calendar.YEAR), is(2013));
		assertThat(cal.get(Calendar.MONTH), is(Calendar.MARCH));
		assertThat(cal.get(Calendar.DAY_OF_MONTH), is(9));

		assertThat(cal.get(Calendar.HOUR_OF_DAY), is(23));
		assertThat(cal.get(Calendar.MINUTE), is(59));
		assertThat(cal.get(Calendar.SECOND), is(58));

		assertThat(cal.get(Calendar.MILLISECOND), is(123));
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
		assertThat(cal.get(Calendar.YEAR), is(2013));
		assertThat(cal.get(Calendar.MONTH), is(Calendar.MARCH));
		assertThat(cal.get(Calendar.DAY_OF_MONTH), is(9));

		// Time fields are zeroed
		assertThat(cal.get(Calendar.HOUR_OF_DAY), is(0));
		assertThat(cal.get(Calendar.MINUTE), is(0));
		assertThat(cal.get(Calendar.SECOND), is(0));
		assertThat(cal.get(Calendar.MILLISECOND), is(0));
	}

	@Test
	public void testZeroTime_ZeroesTheTimeFields() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 58);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 123);

		TimeFactory.zeroTime(cal);

		assertThat(cal.get(Calendar.HOUR_OF_DAY), is(0));
		assertThat(cal.get(Calendar.MINUTE), is(0));
		assertThat(cal.get(Calendar.SECOND), is(0));
		assertThat(cal.get(Calendar.MILLISECOND), is(0));
	}

	@Test
	public void testZeroTime_DateDayInfoIsUnchanged() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2013);
		cal.set(Calendar.MONTH, Calendar.MARCH);
		cal.set(Calendar.DAY_OF_MONTH, 9);

		TimeFactory.zeroTime(cal);

		assertThat(cal.get(Calendar.YEAR), is(2013));
		assertThat(cal.get(Calendar.MONTH), is(Calendar.MARCH));
		assertThat(cal.get(Calendar.DAY_OF_MONTH), is(9));
	}

}
