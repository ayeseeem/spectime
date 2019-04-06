package org.ayeseeem.spectime;

import java.util.Date;

/**
 * Interface for isolating your code from time-specific code - for example
 * {@code new Date()} - to help testing.
 */
public interface DateSource {

	/**
	 * Gets a {@code Date}. Named to match Java 8's
	 * {@code Supplier<Date>.get()}, even though we don't use Java 8.
	 *
	 * @return a {@code Date}
	 */
	public Date get();

}
