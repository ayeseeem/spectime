package org.ayeseeem.spectime.test.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.TimeZone;

import org.junit.After;
import org.junit.Test;

public class OtherTimeZoneTest {

	private static final TimeZone initial = TimeZone.getDefault();

	@After
	public void restoreTimeZone_EvenIfFailedTestsDidNotRestoreIt() {
		TimeZone.setDefault(initial);
	}

	@Test
	public void testSetsNonDefaultTimeZone() {
		assertThat(TimeZone.getDefault(), is(initial));

		OtherTimeZone subject = new OtherTimeZone();
		subject.before();

		TimeZone defaultWithinTest = TimeZone.getDefault();
		assertThat(defaultWithinTest, is(not(initial)));
	}

	@Test
	public void testAfter_RestoresDefault() {
		assertThat(TimeZone.getDefault(), is(initial));

		OtherTimeZone subject = new OtherTimeZone();
		subject.before();

		subject.after();
		assertThat(TimeZone.getDefault(), is(initial));
	}

}
