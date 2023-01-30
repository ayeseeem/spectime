package org.ayeseeem.spectime.test.junit;

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

}
