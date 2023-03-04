package org.ayeseeem.spectime;

import static java.util.Calendar.HOUR;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.SECOND;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.ayeseeem.spectime.test.junit.OtherTimeZone;
import org.junit.Rule;
import org.junit.Test;

public class DtIntervalTest {

    @Rule
    public OtherTimeZone otherTz = new OtherTimeZone();

    @Test
    public void testFrom() {
        DtInterval subject = new DtInterval(234, MILLISECOND);
        assertThat(subject.from(new Date(1000)), is(new Date(1234)));
    }

    @Test
    public void testAfter() {
        DtInterval subject = new DtInterval(234, MILLISECOND);
        assertThat(subject.after(new Date(1000)), is(new Date(1234)));
    }

    @Test
    public void testAfterIsSynonymForFrom() {
        DtInterval subject = new DtInterval(123, MILLISECOND);
        assertThat(subject.after(new Date(1000)), is(subject.from(new Date(1000))));
    }

    @Test
    public void testBefore() {
        DtInterval subject = new DtInterval(234, MILLISECOND);
        assertThat(subject.before(new Date(1234)), is(new Date(1000)));
    }

    @Test
    public void testAgo() {
        Date start = new Date();
        DtInterval subject = new DtInterval(0, MILLISECOND);
        Date result = subject.ago();
        Date end = new Date();

        assertTrue(start.getTime() <= result.getTime());
        assertTrue(result.getTime() <= end.getTime());
    }

    @Test
    public void testAgoUsesInterval() {
        Date start = new Date();
        DtInterval subject = new DtInterval(123, MILLISECOND);
        Date result = subject.ago();
        Date end = new Date();

        assertTrue(start.getTime() <= result.getTime() + 123);
        assertTrue(result.getTime() + 123 <= end.getTime());
    }

    @Test
    public void testAgo_WithDateSupplier() {
        DtInterval subject = new DtInterval(123, MILLISECOND);
        assertThat(subject.ago(constDateSupplier), is(new Date(constNow.getTime() - 123)));
    }

    @Test
    public void testAgoIsSynonymForBeforeNow() {
        DtInterval subject = new DtInterval(123, MILLISECOND);
        assertThat(subject.ago(constDateSupplier), is(subject.before(constNow)));
    }

    @Test
    public void testAnd() {
        DtInterval subject = new DtInterval(7, SECOND);
        subject = subject.and(123).milliseconds();

        assertThat(subject.after(new Date(1000)), is(new Date(8123)));
    }

    @Test
    public void testAnd_MultipleChains() {
        DtInterval subject = new DtInterval(1, MILLISECOND);
        subject = subject.and(2).milliseconds();
        subject = subject.and(3).milliseconds();

        assertThat(subject.after(new Date(1000)), is(new Date(1006)));
    }

    @Test
    public void testAnd_MultipleChains_DifferentUnits() {
        DtInterval subject = new DtInterval(1, MILLISECOND);
        subject = subject.and(2).seconds();
        subject = subject.and(3).minutes();

        assertThat(subject.after(new Date(1000)),
                is(new Date(1000
                        + 1
                        + 2 * 1000
                        + 3 * (60 * 1000))));
    }

    @Test
    public void testEquals() {
        assertThat(new DtInterval(123, MILLISECOND).equals(new DtInterval(123, MILLISECOND)),
                is(true));

        assertThat(new DtInterval(123, MILLISECOND).equals(new DtInterval(456, MILLISECOND)),
                is(false));
        assertThat(new DtInterval(123, MILLISECOND).equals(new DtInterval(123, HOUR)),
                is(false));

        assertThat(new DtInterval(123, MILLISECOND).equals(new String("456")),
                is(false));
    }

    @Test
    public void testEquals_Self() {
        DtInterval subject = new DtInterval(123, MILLISECOND);
        assertThat(subject.equals(subject), is(true));
    }

    @Test
    public void testEquals_WorksWithJunit() {
        assertEquals(new DtInterval(123, MILLISECOND), new DtInterval(123, MILLISECOND));
        assertNotEquals(new DtInterval(123, MILLISECOND), new DtInterval(456, MILLISECOND));

        assertThat(new DtInterval(123, MILLISECOND), is(new DtInterval(123, MILLISECOND)));
        assertThat(new DtInterval(123, MILLISECOND), is(not(new DtInterval(456, MILLISECOND))));
    }

    @Test
    public void testEquals_WithPrevious() {
        DtInterval dtInterval1 = new DtInterval(123, MILLISECOND,
                new DtInterval(111, MILLISECOND));
        DtInterval dtInterval2 = new DtInterval(123, MILLISECOND,
                new DtInterval(111, MILLISECOND));

        assertThat(dtInterval2, is(dtInterval1));
        assertThat(dtInterval2.equals(dtInterval1), is(true));
        assertThat(dtInterval1.equals(dtInterval2), is(true));
    }

    @Test
    public void testEquals_WithPrevious_NotEqual() {
        DtInterval dtInterval1 = new DtInterval(123, MILLISECOND,
                new DtInterval(111, MILLISECOND));
        DtInterval dtInterval2 = new DtInterval(123, MILLISECOND,
                new DtInterval(222, MILLISECOND));

        assertThat(dtInterval1.equals(dtInterval2), is(false));
        assertThat(dtInterval2.equals(dtInterval1), is(false));
    }

    @Test
    public void testEquals_WithPrevious_NotEqual_NoPrevious() {
        DtInterval dtInterval1 = new DtInterval(123, MILLISECOND,
                new DtInterval(111, MILLISECOND));
        DtInterval dtIntervalWithNoPrevious = new DtInterval(123, MILLISECOND);

        assertThat(dtInterval1.equals(dtIntervalWithNoPrevious), is(false));
        assertThat(dtIntervalWithNoPrevious.equals(dtInterval1), is(false));
    }

    @Test
    public void testEquals_WithPrevious_EquivalentChainedAnds() {
        DtInterval dtInterval1 = new DtInterval(1, MILLISECOND);
        dtInterval1 = dtInterval1.and(2).milliseconds();
        dtInterval1 = dtInterval1.and(3).milliseconds();

        DtInterval dtInterval2 = new DtInterval(3, MILLISECOND);
        dtInterval2 = dtInterval2.and(1).milliseconds();
        dtInterval2 = dtInterval2.and(2).milliseconds();

        // HACK: current way to test equivalent chains is to apply them
        assertThat(dtInterval2.after(new Date(0)), is(dtInterval1.after(new Date(0))));
        // TODO: make this work explicitly, by applying chains internally in equals/hashcode
        //assertThat(dtInterval2, is(dtInterval1));
    }

    @Test
    public void testConstructWithPrevious() {
        DtInterval previous = new DtInterval(222, MILLISECOND);
        DtInterval subject = new DtInterval(111, MILLISECOND, previous);

        assertThat(subject.after(new Date(0)).getTime(), is(111L + 222L));
        assertThat(subject.after(new Date(123456)).getTime(), is(123456L + 111L + 222L));
    }

    @Test
    public void testConstructWithPrevious_WhenPreviousIsNull() {
        DtInterval previous = null;
        DtInterval subject = new DtInterval(111, MILLISECOND, previous);

        assertThat(subject.after(new Date(0)).getTime(), is(111L + 0));
        assertThat(subject.after(new Date(123456)).getTime(), is(123456L + 111L + 0));
    }

    private final Date constNow = new Date();

    private final DateSupplier constDateSupplier = new DateSupplier() {
        @Override
        public Date get() {
            return constNow;
        }
    };

}
