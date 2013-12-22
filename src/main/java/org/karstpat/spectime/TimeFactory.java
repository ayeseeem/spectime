package org.karstpat.spectime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFactory {

	/**
	 * Format for specifying dates unambiguously
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * Main starting point for expressing relative times. For example:
	 * 
	 * <pre>
	 * <code>
	 * Date d = time(3).hours().fromNow();
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
	 * Date d = date(3).days().fromNow();
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
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		try {
			Date d = df.parse(s);
			return d;
		} catch (ParseException e) {
			throw new Error("problem creating date", e);
		}
	}

}
