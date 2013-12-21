package org.karstpat.spec_time;

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
		new DtNumber(2);
	}

}
