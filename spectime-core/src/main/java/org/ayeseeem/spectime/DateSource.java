package org.ayeseeem.spectime;

import java.util.Date;

/**
 * Interface for isolating your code from time-specific code - for example
 * {@code new Date()} - to help testing.
 */
public interface DateSource {
	public Date getDate();
}
