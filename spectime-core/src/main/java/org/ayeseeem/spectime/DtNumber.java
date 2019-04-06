package org.ayeseeem.spectime;

import java.util.Calendar;

public class DtNumber {

	private final int n;
	private DtInterval previous;

	public DtNumber(int n) {
		this.n = n;
	}

	public DtInterval milliseconds() {
		return createWithPrevious(n, Calendar.MILLISECOND);
	}

	public DtInterval millisecond() {
		return milliseconds();
	}

	public DtInterval seconds() {
		return createWithPrevious(n, Calendar.SECOND);
	}

	public DtInterval second() {
		return seconds();
	}

	public DtInterval minutes() {
		return createWithPrevious(n, Calendar.MINUTE);
	}

	public DtInterval minute() {
		return minutes();
	}

	public DtInterval hours() {
		return createWithPrevious(n, Calendar.HOUR);
	}

	public DtInterval hour() {
		return hours();
	}

	public DtInterval days() {
		return createWithPrevious(n, Calendar.DAY_OF_YEAR);
	}

	public DtInterval day() {
		return days();
	}

	public DtInterval weeks() {
		return createWithPrevious(n, Calendar.WEEK_OF_YEAR);
	}

	public DtInterval week() {
		return weeks();
	}

	public DtInterval months() {
		return createWithPrevious(n, Calendar.MONTH);
	}

	public DtInterval month() {
		return months();
	}

	public DtInterval years() {
		return createWithPrevious(n, Calendar.YEAR);
	}

	public DtInterval year() {
		return years();
	}

	void setPrevious(DtInterval previous) {
		this.previous = previous;
	}

	private DtInterval createWithPrevious(DtInterval dtInterval) {
		if (previous != null) {
			dtInterval.setPrevious(previous);
		}
		return dtInterval;
	}

	DtInterval createWithPrevious(int n, int timeUnitId) {
		final DtInterval dtInterval = new DtInterval(n, timeUnitId);
		return createWithPrevious(dtInterval);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + n;
		result = prime * result
				+ ((previous == null) ? 0 : previous.hashCode());
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
		if (previous == null) {
			if (other.previous != null) {
				return false;
			}
		} else if (!previous.equals(other.previous)) {
			return false;
		}
		return true;
	}

}
