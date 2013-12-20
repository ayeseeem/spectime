package org.ayeseeem.spec_time;

import java.util.Calendar;

public class DtNumber {

	private final int n;

	public DtNumber(int n) {
		this.n = n;
	}

	public DtInterval milliseconds() {
		return new DtInterval(n, Calendar.MILLISECOND);
	}

	public DtInterval millisecond() {
		return milliseconds();
	}

}
