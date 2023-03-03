package org.ayeseeem.spectime.test.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
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

		@SuppressWarnings("resource")
		OtherTimeZone subject = new OtherTimeZone();
		subject.before();

		TimeZone defaultWithinTest = TimeZone.getDefault();
		assertThat(defaultWithinTest, is(not(initial)));
	}

	@Test
	public void testAfter_RestoresDefault() {
		assertThat(TimeZone.getDefault(), is(initial));

		@SuppressWarnings("resource")
		OtherTimeZone subject = new OtherTimeZone();
		subject.before();

		subject.after();
		assertThat(TimeZone.getDefault(), is(initial));
	}

	@Test
	public void testWithTryWithResources() throws Exception {
		assertThat(TimeZone.getDefault(), is(initial));

		try (OtherTimeZone otz = new OtherTimeZone()) {
			assertThat(TimeZone.getDefault(), is(not(initial)));
		}

		assertThat(TimeZone.getDefault(), is(initial));
	}

	@Test
	public void testWithTryWithResources_WithException() {
		assertThat(TimeZone.getDefault(), is(initial));

		Exception thrown = null;

		try (OtherTimeZone otz = new OtherTimeZone()) {
			assertThat(TimeZone.getDefault(), is(not(initial)));

			throw new Exception("Simulated error");
		} catch (Exception expected) {
			thrown = expected;
			assertThat(TimeZone.getDefault(), is(initial));
		} finally {
			assertThat(TimeZone.getDefault(), is(initial));
		}

		assertThat(TimeZone.getDefault(), is(initial));

		// Confirm test ran properly
		assertThat(thrown, is(not(nullValue())));
		assertThat(thrown.getMessage(), is("Simulated error"));
	}

}
