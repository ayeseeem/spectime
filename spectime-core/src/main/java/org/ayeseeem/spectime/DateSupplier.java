package org.ayeseeem.spectime;

import java.util.Date;

/**
 * Interface for isolating your code from time-specific code - for example
 * {@code new Date()} - to help testing. Named to match Java 8's functional
 * interface {@code Supplier<Date>}, even though we don't use Java 8.
 */
public interface DateSupplier {

	/**
	 * Gets a {@code Date}. Named to match Java 8's
	 * {@code Supplier<Date>.get()}, even though we don't use Java 8.
	 *
	 * @return a {@code Date}
	 */
	Date get();

}
