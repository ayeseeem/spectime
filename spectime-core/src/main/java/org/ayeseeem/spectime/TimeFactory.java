package org.ayeseeem.spectime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFactory {

	/**
	 * Format for specifying dates (days) unambiguously. Based on <a
	 * href="http://www.iso.org/iso/home/standards/iso8601.htm">ISO 8601</a>
	 * format for dates.
	 * 
	 * @see #DATE_WITH_MILLIS_FORMAT
	 * @see #date(String)
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * Format for specifying dates unambiguously, including minutes. Based on <a
	 * href="http://www.iso.org/iso/home/standards/iso8601.htm">ISO 8601</a>
	 * format for date/times. The T between the date and time is omitted (as
	 * permitted by ISO 8601), for clarity.
	 * 
	 * @see #date(String)
	 */
	public static final String DATE_WITH_MINUTES_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * Format for specifying dates unambiguously, including seconds. Based on <a
	 * href="http://www.iso.org/iso/home/standards/iso8601.htm">ISO 8601</a>
	 * format for date/times. The T between the date and time is omitted (as
	 * permitted by ISO 8601), for clarity.
	 * 
	 * @see #date(String)
	 */
	public static final String DATE_WITH_SECONDS_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Format for specifying dates unambiguously, including milliseconds. Based
	 * on <a href="http://www.iso.org/iso/home/standards/iso8601.htm">ISO
	 * 8601</a> format for date/times. The T between the date and time is
	 * omitted (as permitted by ISO 8601), for clarity.
	 * 
	 * @see #date(String)
	 */
	public static final String DATE_WITH_MILLIS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * Main starting point for expressing relative times. For example:
	 * 
	 * <pre>
	 * <code>
	 * Date d = time(3).hours().from(someDate);
	 * </code>
	 * </pre>
	 * 
	 * A synonym for {@link #date(int)}.
	 * 
	 * @param n
	 *            number of units relative
	 * @return a <code>DtNumber</code> containing the number of units, ready for
	 *         specifying the unit of time.
	 */
	public static DtNumber time(int n) {
		return new DtNumber(n);
	}

	/**
	 * Main starting point for expressing relative dates. For example:
	 * 
	 * <pre>
	 * <code>
	 * Date d = date(3).days().from(someDate);
	 * </code>
	 * </pre>
	 * 
	 * A synonym for {@link #time(int)}.
	 * 
	 * @param n
	 *            number of units relative
	 * @return a <code>DtNumber</code> containing the number of units, ready for
	 *         specifying the unit of time.
	 */
	public static DtNumber date(int n) {
		return new DtNumber(n);
	}

	/**
	 * Convenience factory method to easily create a date from a simple string.
	 * 
	 * @param s string in the form {@link #DATE_FORMAT}
	 */
	public static Date date(String s) {
		try {
			final Date d = dateWithMillisFromString(s);
			return d;
		} catch (ParseException e) {
			// ignore - try a different format
		}

		try {
			final Date d = dateWithSecondsFromString(s);
			return d;
		} catch (ParseException e) {
			// ignore - try a different format
		}

		try {
			final Date d = dateWithMinutesFromString(s);
			return d;
		} catch (ParseException e) {
			// ignore - try a different format
		}

		try {
			final Date d = dayFromString(s);
			return d;
		} catch (ParseException e) {
			// ignore - try a different format
		}

		// no more formats to try - error:
		throw new Error("problem creating date: " + s);
	}

	private static Date dateWithMinutesFromString(String s) throws ParseException {
		final DateFormat df = new SimpleDateFormat(DATE_WITH_MINUTES_FORMAT);
		Date d = df.parse(s);
		return d;
	}

	private static Date dateWithSecondsFromString(String s) throws ParseException {
		final DateFormat df = new SimpleDateFormat(DATE_WITH_SECONDS_FORMAT);
		Date d = df.parse(s);
		return d;
	}

	private static Date dateWithMillisFromString(String s) throws ParseException {
		final DateFormat df = new SimpleDateFormat(DATE_WITH_MILLIS_FORMAT);
		Date d = df.parse(s);
		return d;
	}

	private static Date dayFromString(String s) throws ParseException {
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		Date d = df.parse(s);
		return d;
	}

	/**
	 * Convenience factory method to easily create a date <em>now</em>, instead
	 * of <code>new Date()</code>.
	 */
	public static Date now() {
		return new DefaultDateSource().getDate();
	}

	/**
	 * Gets the start of the day (midnight) for the given date.
	 */
	public static Date startOf(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// zero the time fields
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

}
