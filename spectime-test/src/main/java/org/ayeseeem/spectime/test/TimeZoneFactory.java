package org.ayeseeem.spectime.test;

import java.util.TimeZone;

public class TimeZoneFactory {

	public static TimeZone findWithDifference(TimeZone zone, int hours) {
		String[] availableIDs = TimeZone.getAvailableIDs();
		for (String id : availableIDs) {
			TimeZone candidate = TimeZone.getTimeZone(id);
			if (rawDifference(zone, candidate) == hoursToMillis(hours)) {
				return candidate;
			}
		}

		// TODO: ICM 2023-02-01: If not found, construct one?
		throw new RuntimeException("Failed to determine a time zone that was exactly different " + hours
				+ " hours different to " + zone.getID());
	}

	static int rawDifference(TimeZone zone, TimeZone candidate) {
		return candidate.getRawOffset() - zone.getRawOffset();
	}

	public static int hoursToMillis(int hours) {
		return hours * 60 * 60 * 1000;
	}

}
