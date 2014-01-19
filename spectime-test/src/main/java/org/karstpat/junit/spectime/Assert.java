package org.karstpat.junit.spectime;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Assert {

	/**
	 * Format for use in comparing dates in a clearer form than the standard
	 * {@link org.junit.Assert#assertEquals()}. Specifies a <a
	 * href="http://www.iso.org/iso/home/standards/iso8601.htm">ISO
	 * 8601</a>-like date/time in a more readable form. The T between the date
	 * and time is omitted (as permitted by ISO 8601), for clarity. The timezone
	 * is not used, because the dates should always be UTC.
	 */
	public static final String COMPARABLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * Asserts that dates are equal, but with a clearer error message than the
	 * standard {@link org.junit.Assert#assertEquals()}.
	 */
	public static void assertDateEquals(Date date1, Date date2) {
		assertEquals(toComparableDateString(date1), toComparableDateString(date2));
	}

	/**
	 * Converts a Date to a string that can be used to simplify comparisons of
	 * dates. Intended for use by {@link #assertDateEquals(Date, Date)}, to give
	 * clearer error messages.
	 * 
	 * @see #assertDateEquals(Date, Date)
	 * @see #COMPARABLE_DATE_FORMAT
	 */
	private static String toComparableDateString(Date d) {
		final DateFormat df = new SimpleDateFormat(COMPARABLE_DATE_FORMAT);
		return df.format(d);
	}

}