package org.ayeseeem.spectime.test.junit;

import static org.ayeseeem.spectime.test.ZonedTester.exclusiveZones;

import java.util.TimeZone;

public class OtherTimeZone extends RestoreTimeZone {

	public OtherTimeZone() {
		TimeZone.setDefault(definitelyNotDefaultTimeZone());
	}

	public static TimeZone definitelyNotDefaultTimeZone() {
		TimeZone defaultZone = TimeZone.getDefault();

		for (TimeZone zone : exclusiveZones()) {
			if (!zone.equals(defaultZone)) {
				return zone;
			}
		}

		throw new RuntimeException("Failed to determine a time zone that was different from the default");
	}

}
