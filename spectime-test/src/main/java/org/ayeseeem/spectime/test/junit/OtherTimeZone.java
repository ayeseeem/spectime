package org.ayeseeem.spectime.test.junit;

import static org.ayeseeem.spectime.test.TimeZoneFactory.hoursToMillis;

import java.util.TimeZone;

public class OtherTimeZone extends RestoreTimeZone {

	private static int BUFFER_HOURS = 3;

	public OtherTimeZone() {
		TimeZone.setDefault(definitelyNotDefaultTimeZone());
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

		throw new RuntimeException("Failed to determine a time zone that was different to " + zone.getID());
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
