package org.ayeseeem.spec_time;

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
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(timeUnitId, n);
		return cal.getTime();
	}

}
