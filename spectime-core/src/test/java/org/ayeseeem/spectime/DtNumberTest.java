package org.ayeseeem.spectime;

import static java.util.Calendar.MILLISECOND;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.ayeseeem.spectime.test.junit.OtherTimeZone;
import org.junit.Rule;
import org.junit.Test;

public class DtNumberTest {

	@Rule
	public OtherTimeZone otherTz = new OtherTimeZone();

	@Test
	public void millisecondSynonym() {
		DtNumber subject = new DtNumber(1);
		assertThat(subject.millisecond(), is(subject.milliseconds()));
	}

	@Test
	public void millisecondSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber subject = new DtNumber(2);
		subject.millisecond();
	}


	@Test
	public void secondSynonym() {
		DtNumber subject = new DtNumber(1);
		assertThat(subject.second(), is(subject.seconds()));
	}

	@Test
	public void secondSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber subject = new DtNumber(2);
		subject.second();
	}

	@Test
	public void minuteSynonym() {
		DtNumber subject = new DtNumber(1);
		assertThat(subject.minute(), is(subject.minutes()));
	}

	@Test
	public void minuteSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber subject = new DtNumber(2);
		subject.minute();
	}

	@Test
	public void hourSynonym() {
		DtNumber subject = new DtNumber(1);
		assertThat(subject.hour(), is(subject.hours()));
	}

	@Test
	public void hourSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber subject = new DtNumber(2);
		subject.hour();
	}

	@Test
	public void daySynonym() {
		DtNumber subject = new DtNumber(1);
		assertThat(subject.day(), is(subject.days()));
	}

	@Test
	public void daySynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber subject = new DtNumber(2);
		subject.day();
	}

	@Test
	public void weekSynonym() {
		DtNumber subject = new DtNumber(1);
		assertThat(subject.week(), is(subject.weeks()));
	}

	@Test
	public void weekSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber subject = new DtNumber(2);
		subject.week();
	}

	@Test
	public void monthSynonym() {
		DtNumber subject = new DtNumber(1);
		assertThat(subject.month(), is(subject.months()));
	}

	@Test
	public void monthSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber subject = new DtNumber(2);
		subject.month();
	}

	@Test
	public void yearSynonym() {
		DtNumber subject = new DtNumber(1);
		assertThat(subject.year(), is(subject.years()));
	}

	@Test
	public void yearSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber subject = new DtNumber(2);
		subject.year();
	}

	@Test
	public void testEquals() {
		assertThat(new DtNumber(123).equals(new DtNumber(123)), is(true));
		assertThat(new DtNumber(123).equals(new DtNumber(456)), is(false));
		assertThat(new DtNumber(123).equals(new String("456")), is(false));
	}

	@Test
	public void testEquals_Self() {
		DtNumber subject = new DtNumber(123);
		assertThat(subject.equals(subject), is(true));
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
		DtNumber dtNumber1 = new DtNumber(123,
				new DtInterval(111, MILLISECOND));
		DtNumber dtNumber2 = new DtNumber(123,
				new DtInterval(111, MILLISECOND));

		assertThat(dtNumber2, is(dtNumber1));
		assertThat(dtNumber1.equals(dtNumber2), is(true));
		assertThat(dtNumber2.equals(dtNumber1), is(true));
	}

	@Test
	public void testEquals_WithPrevious_NotEqual() {
		DtNumber dtNumber1 = new DtNumber(123,
				new DtInterval(111, MILLISECOND));
		DtNumber dtNumber2 = new DtNumber(123,
				new DtInterval(222, MILLISECOND));

		assertThat(dtNumber1.equals(dtNumber2), is(false));
		assertThat(dtNumber2.equals(dtNumber1), is(false));
	}

	@Test
	public void testEquals_WithPrevious_NotEqual_NoPrevious() {
		DtNumber dtNumber1 = new DtNumber(123,
				new DtInterval(111, MILLISECOND));

		DtNumber dtNumberWithNoPrevious = new DtNumber(123);

		assertThat(dtNumber1.equals(dtNumberWithNoPrevious), is(false));
	}

	@Test
	public void testCreateWithPrevious_FromIntInt_WhenPreviousIsNull() {
		DtNumber subject = new DtNumber(123, null);

		DtInterval result = subject.createWithPrevious(111, MILLISECOND);

		assertThat(result.after(new Date(0)).getTime(), is(111L + 0));
		assertThat(result.after(new Date(123456)).getTime(), is(123456L + 111L + 0));
	}

	@Test
	public void testCreateWithPrevious_FromIntInt_WhenPreviousIsNotNull() {
		DtNumber subject = new DtNumber(123,
				new DtInterval(222, MILLISECOND));

		DtInterval result = subject.createWithPrevious(111, MILLISECOND);

		assertThat(result.after(new Date(0)).getTime(), is(111L + 222L));
		assertThat(result.after(new Date(123456)).getTime(), is(123456L + 111L + 222L));
	}

}
