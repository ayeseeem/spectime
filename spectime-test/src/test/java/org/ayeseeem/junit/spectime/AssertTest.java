package org.ayeseeem.junit.spectime;

import static org.ayeseeem.junit.spectime.Assert.assertDateEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;

public class AssertTest {

	@Test
	public void constants() {
		assertThat(Assert.COMPARABLE_DATE_FORMAT, is("yyyy-MM-dd HH:mm:ss.SSS"));
	}

	@Test(expected = AssertionError.class)
	public void datesNotEqual_Junit() {
		assertEquals(new Date(0), new Date(1));
	}

	@Test(expected = AssertionError.class)
	public void datesNotEqual() {
		assertDateEquals(new Date(0), new Date(1));
	}

	@Test
	public void datesNotEqualMessage() {
		Date d1 = new Date(1388534398123L);	// "2013-12-31 23:59:58.123"
		Date d2 = new Date(944002737099L);	// "1999-11-30 22:58:57.099"
		try {
			assertDateEquals(d1, d2);
			fail("should have thrown");
		} catch (AssertionError error) {
			assertThat(error.getMessage(),
					is("expected:<[2013-12-31 23:59:58.123]> but was:<[1999-11-30 22:58:57.099]>"));
		}
	}

	@Test
	public void datesNotEqualMessage_ContainsMilliseconds() {
		try {
			assertDateEquals(new Date(123), new Date(246));
			fail("should have thrown");
		} catch (AssertionError error) {
			assertThat(error.getMessage(), containsString("123"));
			assertThat(error.getMessage(), containsString("246"));
		}
	}

}
