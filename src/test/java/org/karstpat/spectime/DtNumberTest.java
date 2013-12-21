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

}
