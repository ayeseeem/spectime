package org.ayeseeem.spectime;

import java.util.Date;

/**
 * Default implementation of DateSource.
 */
public class DefaultDateSource implements DateSource {

	/**
	 * Gets the current date by returning {@code new Date()}.
	 */
	@Override
	public Date getDate() {
		return new Date();
	}

}
