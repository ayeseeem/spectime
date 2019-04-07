package org.ayeseeem.spectime;

import java.util.Calendar;
import java.util.Date;

public class DtInterval {

	private final int n;
	private final int timeUnitId;
	private final DateSupplier dateSupplier;
	private DtInterval previous;

	public DtInterval(int n, int timeUnitId) {
		this(n, timeUnitId, new DefaultDateSupplier());
	}

	public DtInterval(int n, int timeUnitId, DateSupplier dateSupplier) {
		this.n = n;
		this.timeUnitId = timeUnitId;
		this.dateSupplier = dateSupplier;
	}

	static DtInterval createWithPrevious(int n, int timeUnitId,
			DtInterval previous) {
		final DtInterval dtInterval = new DtInterval(n, timeUnitId);
		dtInterval.setPrevious(previous);
		return dtInterval;
	}

	public Date from(Date date) {
		return createRelative(date, n);
	}

	public Date after(Date date) {
		return from(date);
	}

	public Date before(Date date) {
		return createRelative(date, -n);	// add negative n to do subtract
	}

	public Date ago() {
		return before(dateSupplier.get());
	}

	private Date createRelative(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		if (previous != null) {
			cal.setTime(previous.createRelative(date, previous.n));
		} else {
			cal.setTime(date);
		}
		cal.add(timeUnitId, amount);
		return cal.getTime();
	}

	public DtNumber and(int n) {
		DtNumber nextNumber = new DtNumber(n, this);
		return nextNumber;
	}

	public void setPrevious(DtInterval previous) {
		this.previous = previous;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + n;
		result = prime * result
				+ ((previous == null) ? 0 : previous.hashCode());
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
		if (previous == null) {
			if (other.previous != null) {
				return false;
			}
		} else if (!previous.equals(other.previous)) {
			return false;
		}
		if (timeUnitId != other.timeUnitId) {
			return false;
		}
		return true;
	}

}
