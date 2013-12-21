package org.karstpat.spectime;

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

	public DtInterval seconds() {
		return new DtInterval(n, Calendar.SECOND);
	}

	public DtInterval second() {
		return seconds();
	}

	public DtInterval minutes() {
		return new DtInterval(n, Calendar.MINUTE);
	}

	public DtInterval minute() {
		return minutes();
	}

	public DtInterval hours() {
		return new DtInterval(n, Calendar.HOUR);
	}

	public DtInterval hour() {
		return hours();
	}

	public DtInterval days() {
		return new DtInterval(n, Calendar.DAY_OF_YEAR);
	}

	public DtInterval day() {
		return days();
	}

}
