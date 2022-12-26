package org.ayeseeem.spectime;

import java.time.Instant;
import java.time.Month;

public class CompatibilityCheck {

	/**
	 * Helper to guard against using Java8+ constructs in the "heritage"
	 * Java 6 builds.
	 */
	public static final void confirmBuildsWithJava8Constructs() {
		// These are Java 8 constructs.
		Instant instant = Instant.now();
		Month month = Month.APRIL;
    }

}
