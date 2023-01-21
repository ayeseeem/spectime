package org.ayeseeem.spectime.test.junit;

import static org.ayeseeem.spectime.test.junit.Assert.assertDateEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Date;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class AssertTest {

	@Test
	public void constants() {
		assertThat(Assert.COMPARABLE_DATE_FORMAT, is("yyyy-MM-dd HH:mm:ss.SSS"));
	}

	//@Characterization
	@Test(expected = AssertionError.class)
	public void testAssertEquals_Junit_DatesNotEqual() {
		assertEquals(new Date(0), new Date(1));
	}

	@Test(expected = AssertionError.class)
	public void testAssertDateEquals_DatesNotEqual() {
		assertDateEquals(new Date(0), new Date(1));
	}

	@Test
	public void testAssertDateEquals_DatesNotEqual_Message() {
		Error error = assertThrows(AssertionError.class, new ThrowingRunnable() {
			Date d1 = new Date(1388534398123L);	// "2013-12-31 23:59:58.123"
			Date d2 = new Date(944002737099L);	// "1999-11-30 22:58:57.099"

			@Override
			public void run() throws Throwable {
			assertDateEquals(d1, d2);
			}
		});
		assertThat(error.getMessage(),
					is("expected:<[2013-12-31 23:59:58.123]> but was:<[1999-11-30 22:58:57.099]>"));
	}

	@Test
	public void testAssertDateEquals_DatesNotEqual_MessageContainsMilliseconds() {
		Error error = assertThrows(AssertionError.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				assertDateEquals(new Date(123), new Date(246));
			}
		});
		assertThat(error.getMessage(), containsString("123"));
		assertThat(error.getMessage(), containsString("246"));
	}

}
