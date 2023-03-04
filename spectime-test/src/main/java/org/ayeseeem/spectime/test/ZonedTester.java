package org.ayeseeem.spectime.test;

import static org.ayeseeem.spectime.test.TimeZoneFactory.constructWithDifference;
import static org.ayeseeem.spectime.test.TimeZoneFactory.findWithDifference;
import static org.ayeseeem.spectime.test.TimeZoneFactory.significantlyDifferentTo;

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
     * Gets a new list of of (mostly) mutually exclusive time zones.
     * <p>
     * The list will include the current default time zone, to test that the code
     * works in the current set up. It will include UTC as a special case, although
     * this will not be very different from the default if operating in zones near
     * GMT. It will include at least one zone "significantly different" from the
     * default. That will usually mean at least 3 hours apart. The list will also
     * include zones exactly 12 hours away from these zones (based on raw offset),
     * in case there are issues with 12-hour clocks.
     * <p>
     * The list will favour known IDs such as "Africa/Nairobi" rather than synthetic
     * IDs such as "GMT+03:00" in case tests also check the IDs, and in case there
     * are any locale-related issues of {@link TimeZone#getDisplayName()}. However,
     * it does not itself use "known" IDs as it is not guaranteed that they are
     * recognized. It does use "UTC", but if this is not recognized it will default
     * to "GMT", which will be close enough for most purposes.
     *
     * @return a new list of zones
     */
    public static List<TimeZone> exclusiveZones() {
        List<TimeZone> zones = new ArrayList<TimeZone>();

        // Default zone
        TimeZone defaultZone = TimeZone.getDefault();
        zones.add(defaultZone);
        // 12 hours offset from default
        zones.add(find12HoursDifferent(defaultZone));

        // Different from Default
        TimeZone different = significantlyDifferentTo(defaultZone);
        zones.add(different);
        // 12 hours offset from different
        zones.add(find12HoursDifferent(different));

        // UTC
        TimeZone utc = TimeZone.getTimeZone("UTC");
        zones.add(utc);
        // 12 hours offset from UTC
        zones.add(find12HoursDifferent(utc));

        return zones;
    }

    private static TimeZone find12HoursDifferent(TimeZone zone) {
        try {
            return findWithDifference(zone, 12);
        } catch (RuntimeException e1) {
            // TODO: ICM 2023-02-03: Verify message/use built-in type
            try {
                TimeZone minus12 = findWithDifference(zone, -12);
                return minus12;
            } catch (RuntimeException e2) {
                // TODO: ICM 2023-02-03: Verify message/use built-in type
                return constructWithDifference(zone, 12);
            }
        }
    }

}
