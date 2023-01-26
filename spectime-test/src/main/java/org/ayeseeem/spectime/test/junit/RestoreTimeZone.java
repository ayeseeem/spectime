package org.ayeseeem.spectime.test.junit;

import static org.ayeseeem.spectime.test.ZonedTester.exclusiveZones;

import java.util.TimeZone;

import org.junit.rules.ExternalResource;

public class RestoreTimeZone extends ExternalResource {

	private final TimeZone original = TimeZone.getDefault();

	@Override
	protected void before() {
		// does nothing: original is already captured
	}

	@Override
	protected void after() {
		TimeZone.setDefault(original);
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
