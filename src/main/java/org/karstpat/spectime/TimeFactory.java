package org.karstpat.spectime;

public class TimeFactory {

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

}
