package org.karstpat.spectime;

import java.util.Calendar;
import java.util.Date;

public class DtInterval {

	private final int n;
	private final int timeUnitId;

	public DtInterval(int n, int timeUnitId) {
		this.n = n;
		this.timeUnitId = timeUnitId;
	}

	public Date from(Date date) {
		return createRelative(date, n);
	}

	public Date fromNow() {
		return from(new Date());
	}

	public Date before(Date date) {
		return createRelative(date, -n);	// add negative n to do subtract
	}

	private Date createRelative(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(timeUnitId, amount);
		return cal.getTime();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + n;
		result = prime * result + timeUnitId;
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
		DtInterval other = (DtInterval) obj;
		if (n != other.n) {
			return false;
		}
		if (timeUnitId != other.timeUnitId) {
			return false;
		}
		return true;
	}

}
