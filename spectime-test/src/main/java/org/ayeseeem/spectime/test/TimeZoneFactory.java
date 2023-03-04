package org.ayeseeem.spectime.test;

import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimeZoneFactory {

    private static int BUFFER_HOURS = 3;

    public static TimeZone findWithDifference(TimeZone zone, int hours) {
        String[] availableIDs = TimeZone.getAvailableIDs();
        for (String id : availableIDs) {
            TimeZone candidate = TimeZone.getTimeZone(id);
            if (rawDifference(zone, candidate) == hoursToMillis(hours)) {
                return candidate;
            }
        }

        // TODO: ICM 2023-02-01: If not found, construct one?
        throw new RuntimeException("Failed to determine a time zone that was exactly " + hours
                + " hours different to " + zone.getID() + " (offset: " + zone.getRawOffset() + ")");
    }

    static TimeZone constructWithDifference(TimeZone zone, int hours) {
        int newOffset = zone.getRawOffset() + hoursToMillis(hours);

        String id = "Custom: " + newOffset + " offset from " + zone.getID();
        return new SimpleTimeZone(newOffset, id);
    }

    static int rawDifference(TimeZone zone, TimeZone candidate) {
        return candidate.getRawOffset() - zone.getRawOffset();
    }

    public static int hoursToMillis(int hours) {
        return hours * 60 * 60 * 1000;
    }

    public static TimeZone definitelyNotDefaultTimeZone() {
        TimeZone defaultZone = TimeZone.getDefault();

        return significantlyDifferentTo(defaultZone);
    }

    public static TimeZone significantlyDifferentTo(TimeZone zone) {
        String[] availableIDs = TimeZone.getAvailableIDs();
        for (String id : availableIDs) {
            TimeZone candidate = TimeZone.getTimeZone(id);
            if (areSignificantlyDifferent(zone, candidate)) {
                return candidate;
            }
        }

        throw new RuntimeException("Failed to determine a time zone that was different to " + zone.getID()
                + " (offset: " + zone.getRawOffset() + ")");
    }

    /**
     * Determines whether two time zones are different enough for use in detecting
     * and highlighting date/time-dependent code or tests.
     * <p>
     * The time zones must differ by at least +/- 3 hours (in terms of raw offset),
     * and by less than +/- 9 hours. The 3-hour gap is on the basis that they each
     * might have DST of plus or minus an hour, and a raw difference of 3 hours
     * means they should still have different effective offsets. If you are dealing
     * with two zones with strange DST such that this is not a sufficient margin,
     * then you cannot rely on this function to get you different time zones, and
     * should try another approach. Similarly, the time zones cannot differ by more
     * than +/- 9 hours: this is to protect against time zones that could end up
     * with a twelve-hour difference, which in tests looking at just the 12-hour
     * clock time, might end up looking the same, by coincidence.
     *
     * @param zoneA a time zone to compare
     * @param zoneB a time zone to compare
     * @return true if the time zones are different enough
     *
     */
    public static boolean areSignificantlyDifferent(TimeZone zoneA, TimeZone zoneB) {
        if (zoneA.equals(zoneB)) {
            return false;
        }

        int offsetDifference = zoneA.getRawOffset() - zoneB.getRawOffset();
        int diff = Math.abs(offsetDifference);

        return diff > hoursToMillis(BUFFER_HOURS) && diff < hoursToMillis(12 - BUFFER_HOURS);
    }

}
