package org.karstpat.spectime;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DtNumberTest {

	@Test
	public void millisecondSynonym() {
		DtNumber test = new DtNumber(1);
		assertEquals(test.milliseconds(), test.millisecond());
	}

	@Test
	public void millisecondSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.millisecond();
	}


	@Test
	public void secondSynonym() {
		DtNumber test = new DtNumber(1);
		assertEquals(test.seconds(), test.second());
	}

	@Test
	public void secondSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.second();
	}

	@Test
	public void minuteSynonym() {
		DtNumber test = new DtNumber(1);
		assertEquals(test.minutes(), test.minute());
	}

	@Test
	public void minuteSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.minute();
	}

	@Test
	public void hourSynonym() {
		DtNumber test = new DtNumber(1);
		assertEquals(test.hours(), test.hour());
	}

	@Test
	public void hourSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.hour();
	}

	@Test
	public void daySynonym() {
		DtNumber test = new DtNumber(1);
		assertEquals(test.days(), test.day());
	}

	@Test
	public void daySynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.day();
	}

	@Test
	public void monthSynonym() {
		DtNumber test = new DtNumber(1);
		assertEquals(test.months(), test.month());
	}

	@Test
	public void monthSynonymDoesNotCareIfUnitIsNotOne() {
		DtNumber test = new DtNumber(2);
		test.month();
	}

}