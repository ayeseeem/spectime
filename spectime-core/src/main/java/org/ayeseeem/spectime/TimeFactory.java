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
	public static final String DATE_WITH_MINUTES_FORMAT = DATE_FORMAT + " HH:mm";

	/**
	 * Format for specifying dates unambiguously, including seconds. Based on <a
	 * href="http://www.iso.org/iso/home/standards/iso8601.htm">ISO 8601</a>
	 * format for date/times. The T between the date and time is omitted (as
	 * permitted by ISO 8601), for clarity.
	 *
	 * @see #date(String)
	 */
	public static final String DATE_WITH_SECONDS_FORMAT = DATE_WITH_MINUTES_FORMAT + ":ss";

	/**
	 * Format for specifying dates unambiguously, including milliseconds. Based
	 * on <a href="http://www.iso.org/iso/home/standards/iso8601.htm">ISO
	 * 8601</a> format for date/times. The T between the date and time is
	 * omitted (as permitted by ISO 8601), for clarity.
	 *
	 * @see #date(String)
	 */
	public static final String DATE_WITH_MILLIS_FORMAT = DATE_WITH_SECONDS_FORMAT + ".SSS";

	/**
	 * Main starting point for expressing relative times. For example:
	 *
	 * <pre>
	 * Date d = time(3).hours().from(someDate);
	 * </pre>
	 *
	 * A synonym for {@link #date(int)}.
	 *
	 * @param n
	 *            number of units relative
	 * @return a {@code DtNumber} containing the number of units, ready for
	 *         specifying the unit of time.
	 */
	public static DtNumber time(int n) {
		return new DtNumber(n);
	}

	/**
	 * Main starting point for expressing relative dates. For example:
	 *
	 * <pre>
	 * Date d = date(3).days().from(someDate);
	 * </pre>
	 *
	 * A synonym for {@link #time(int)}.
	 *
	 * @param n
	 *            number of units relative
	 * @return a {@code DtNumber} containing the number of units, ready for
	 *         specifying the unit of time.
	 */
	public static DtNumber date(int n) {
		return new DtNumber(n);
	}

	/**
	 * Convenience factory method to easily create a date from a simple string.
	 *
	 * @param s
	 *            string in the form {@link #DATE_FORMAT}
	 * @return a new date created by parsing the string
	 */
	public static Date date(String s) {
		try {
			return dateWithMillisFromString(s);
		} catch (ParseException e) {
			// ignore - try a different format
		}

		try {
			return dateWithSecondsFromString(s);
		} catch (ParseException e) {
			// ignore - try a different format
		}

		try {
			return dateWithMinutesFromString(s);
		} catch (ParseException e) {
			// ignore - try a different format
		}

		try {
			return dayFromString(s);
		} catch (ParseException e) {
			// ignore - try a different format
		}

		// no more formats to try - error:
		throw new Error("problem creating date: " + s);
	}

	private static Date dateWithMinutesFromString(String s) throws ParseException {
		DateFormat df = new SimpleDateFormat(DATE_WITH_MINUTES_FORMAT);
		return df.parse(s);
	}

	private static Date dateWithSecondsFromString(String s) throws ParseException {
		DateFormat df = new SimpleDateFormat(DATE_WITH_SECONDS_FORMAT);
		return df.parse(s);
	}

	private static Date dateWithMillisFromString(String s) throws ParseException {
		DateFormat df = new SimpleDateFormat(DATE_WITH_MILLIS_FORMAT);
		return df.parse(s);
	}

	private static Date dayFromString(String s) throws ParseException {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		return df.parse(s);
	}

	/**
	 * Convenience factory method to easily create a date <em>now</em>, instead
	 * of {@code new Date()}.
	 *
	 * @return the current date/time
	 */
	public static Date now() {
		return new DefaultDateSupplier().get();
	}

	/**
	 * Gets the start of the day (midnight) for the given date.
	 *
	 * @param date
	 *            the date to get the start of
	 *
	 * @return a new date at midnight (the beginning) of the given date
	 */
	public static Date startOf(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		zeroTime(cal);
		return cal.getTime();
	}

	static void zeroTime(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}

	public static String stringOf(Date date) {
		DateFormat df = new SimpleDateFormat(DATE_WITH_MILLIS_FORMAT);
		return df.format(date);
	}

}
