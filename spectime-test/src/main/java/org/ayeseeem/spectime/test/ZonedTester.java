package org.ayeseeem.spectime.test;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.junit.function.ThrowingRunnable;

// TODO: ICM 2023-01-16: Allow selection and variation of time zones
// TODO: ICM 2023-01-16: (Optionally) Include default/system time zone
// TODO: ICM 2023-01-14: Should we pass the time zone in to allow it to be used in tests?
// TODO: ICM 2023-01-17: Make save/restore thread-safe?

/**
 * Class for exercising unit tests (and other code) in various time zone
 * configurations, to confirm that the test itself (and the executed code) is
 * not limited to a single time zone. The intended use of this is to avoid the
 * problem of the developer writing tests that only pass in their own (current)
 * time zone.
 */
public class ZonedTester {

	/**
	 * Capture the original time zone in case you want to double check that it has
	 * been properly restored at the end of use.
	 */
	public static final TimeZone initial = TimeZone.getDefault();

	private final ThrowingRunnable test;

	/**
	 * Convenience factory to verify that the {@code test} code is not dependent on
	 * a particular time zone, by executing it in various time zones.
	 *
	 * @param test code to be executed
	 * @throws Throwable any throwable from the executed test, including from test
	 *                   assertion failures.
	 */
	public static void anyZone(ThrowingRunnable test) throws Throwable {
		(new ZonedTester(test)).testInMultipleZones();
	}

	public ZonedTester(ThrowingRunnable test) {
		this.test = test;
	}

	/**
	 * Executes the {@code test} code in a selection of time zones.
	 *
	 * @throws Throwable any throwable from the executed test, including from test
	 *                   assertion failures.
	 */
	public void testInMultipleZones() throws Throwable {
		List<TimeZone> zones = exclusiveZones();

		for (TimeZone zone : zones) {
			final TimeZone system = TimeZone.getDefault();
			TimeZone.setDefault(zone);
			try {
				test.run();
			} finally {
				TimeZone.setDefault(system);
			}
		}
	}

	/**
	 * Gets a new list of of mutually exclusive time zones.
	 * @return a new list of zones
	 */
	public static List<TimeZone> exclusiveZones() {
		List<TimeZone> zones = new ArrayList<TimeZone>();
		zones.add(TimeZone.getTimeZone("UTC"));
		zones.add(TimeZone.getTimeZone("Europe/London"));
		zones.add(TimeZone.getTimeZone("US/Eastern"));
		zones.add(TimeZone.getTimeZone("Australia/Perth"));
		return zones;
	}

}
