package org.ayeseeem.spectime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DtNumberTest {

	@Test
	public void millisecondSynonym() {
		DtNumber test = new DtNumber(1);
		assertThat(test.millisecond(), is(test.milliseconds()));
	}

	@Test
	public void millisecondSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.millisecond();
	}


	@Test
	public void secondSynonym() {
		DtNumber test = new DtNumber(1);
		assertThat(test.second(), is(test.seconds()));
	}

	@Test
	public void secondSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.second();
	}

	@Test
	public void minuteSynonym() {
		DtNumber test = new DtNumber(1);
		assertThat(test.minute(), is(test.minutes()));
	}

	@Test
	public void minuteSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.minute();
	}

	@Test
	public void hourSynonym() {
		DtNumber test = new DtNumber(1);
		assertThat(test.hour(), is(test.hours()));
	}

	@Test
	public void hourSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.hour();
	}

	@Test
	public void daySynonym() {
		DtNumber test = new DtNumber(1);
		assertThat(test.day(), is(test.days()));
	}

	@Test
	public void daySynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.day();
	}

	@Test
	public void weekSynonym() {
		DtNumber test = new DtNumber(1);
		assertThat(test.week(), is(test.weeks()));
	}

	@Test
	public void weekSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.week();
	}

	@Test
	public void monthSynonym() {
		DtNumber test = new DtNumber(1);
		assertThat(test.month(), is(test.months()));
	}

	@Test
	public void monthSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.month();
	}

	@Test
	public void yearSynonym() {
		DtNumber test = new DtNumber(1);
		assertThat(test.year(), is(test.years()));
	}

	@Test
	public void yearSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.year();
	}

	@Test
	public void testEquals() {
		assertThat(new DtNumber(123).equals(new DtNumber(123)), is(true));
		assertThat(new DtNumber(123).equals(new DtNumber(456)), is(false));
		assertThat(new DtNumber(123).equals(new String("456")), is(false));
	}

	@Test
	public void testEquals_Self() {
		DtNumber test = new DtNumber(123);
		assertThat(test.equals(test), is(true));
	}

	@Test
	public void testEquals_WorksWithJunit() {
		assertEquals(new DtNumber(123), new DtNumber(123));
		assertNotEquals(new DtNumber(123), new DtNumber(456));

		assertThat(new DtNumber(123), is(new DtNumber(123)));
		assertThat(new DtNumber(123), is(not(new DtNumber(456))));
	}

	@Test
	public void testEquals_WithPrevious() {
		DtNumber dtNumber1 = new DtNumber(123);
		dtNumber1.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		DtNumber dtNumber2 = new DtNumber(123);
		dtNumber2.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		assertThat(dtNumber2, is(dtNumber1));
		assertThat(dtNumber1.equals(dtNumber2), is(true));
		assertThat(dtNumber2.equals(dtNumber1), is(true));
	}

	@Test
	public void testEquals_WithPrevious_NotEqual() {
		DtNumber dtNumber1 = new DtNumber(123);
		dtNumber1.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		DtNumber dtNumber2 = new DtNumber(123);
		dtNumber2.setPrevious(new DtInterval(222, Calendar.MILLISECOND));

		assertThat(dtNumber1.equals(dtNumber2), is(false));
		assertThat(dtNumber2.equals(dtNumber1), is(false));
	}

	@Test
	public void testEquals_WithPrevious_NotEqual_NoPrevious() {
		DtNumber dtNumber1 = new DtNumber(123);
		dtNumber1.setPrevious(new DtInterval(111, Calendar.MILLISECOND));

		DtNumber dtNumberWithNoPrevious = new DtNumber(123);

		assertThat(dtNumber1.equals(dtNumberWithNoPrevious), is(false));
	}

	@Test
	public void testCreateWithPrevious_FromIntInt_WhenPreviousIsNull() {
		DtNumber subject = new DtNumber(123);
		subject.setPrevious(null);

		DtInterval result = subject.createWithPrevious(111, Calendar.MILLISECOND);

		assertThat(result.after(new Date(0)).getTime(), is(111L + 0));
		assertThat(result.after(new Date(123456)).getTime(), is(123456L + 111L + 0));
	}

	@Test
	public void testCreateWithPrevious_FromIntInt_WhenPreviousIsNotNull() {
		DtNumber subject = new DtNumber(123);
		subject.setPrevious(new DtInterval(222, Calendar.MILLISECOND));

		DtInterval result = subject.createWithPrevious(111, Calendar.MILLISECOND);

		assertThat(result.after(new Date(0)).getTime(), is(111L + 222L));
		assertThat(result.after(new Date(123456)).getTime(), is(123456L + 111L + 222L));
	}

}
