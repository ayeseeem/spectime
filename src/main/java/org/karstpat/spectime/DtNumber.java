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

	public DtInterval months() {
		return new DtInterval(n, Calendar.MONTH);
	}

	public DtInterval month() {
		return months();
	}

	public DtInterval years() {
		return new DtInterval(n, Calendar.YEAR);
	}

	public DtInterval year() {
		return years();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + n;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DtNumber other = (DtNumber) obj;
		if (n != other.n) {
			return false;
		}
		return true;
	}

}
