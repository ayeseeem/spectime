package org.ayeseeem.spectime;

import java.util.Calendar;

public class DtNumber {

    private final int n;
    private final DtInterval previous;

    public DtNumber(int n) {
        this(n, null);
    }

    DtNumber(int n, DtInterval previous) {
        this.n = n;
        this.previous = previous;
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

    DtInterval createWithPrevious(int n, int timeUnitId) {
        return new DtInterval(n, timeUnitId, previous);
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
