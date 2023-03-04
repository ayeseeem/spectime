package org.ayeseeem.spectime.test;

import static java.util.TimeZone.getTimeZone;
import static org.ayeseeem.spectime.test.TimeZoneFactory.areSignificantlyDifferent;
import static org.ayeseeem.spectime.test.TimeZoneFactory.constructWithDifference;
import static org.ayeseeem.spectime.test.TimeZoneFactory.definitelyNotDefaultTimeZone;
import static org.ayeseeem.spectime.test.TimeZoneFactory.significantlyDifferentTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.TimeZone;

import org.junit.Test;

public class TimeZoneFactoryTest {

    private static final TimeZone initial = TimeZone.getDefault();

    @Test
    public void testDefinitelyNotDefaultTimeZone() {
        TimeZone other = definitelyNotDefaultTimeZone();

        assertThat(other, is(not(initial)));
        assertThat(areSignificantlyDifferent(initial, other), is(true));
    }

    @Test
    public void testAreSignificantlyDifferentTo_AlwaysFindsAnAlternative() {
        String[] zoneIDs = TimeZone.getAvailableIDs();
        for (String id : zoneIDs) {
            TimeZone possibleDefault = TimeZone.getTimeZone(id);
            TimeZone other = significantlyDifferentTo(possibleDefault);

            assertThat(other, is(not(possibleDefault)));
            assertThat(areSignificantlyDifferent(possibleDefault, other), is(true));
        }
    }

    @Test
    public void testAreSignificantlyDifferent_DifferentZones() {
        TimeZone london = TimeZone.getTimeZone("Europe/London");
        TimeZone perth = TimeZone.getTimeZone("Australia/Perth");

        assertThat(areSignificantlyDifferent(london, perth), is(true));
        assertThat(areSignificantlyDifferent(london, london), is(false));
        assertThat(areSignificantlyDifferent(perth, perth), is(false));
    }

    @Test
    public void testAreSignificantlyDifferent_DifferentZones_WithSmallOffset_AreNotSignificantlyDifferent() {
        TimeZone custom = TimeZone.getTimeZone("GMT+05:00");
        TimeZone customClose = TimeZone.getTimeZone("GMT+05:01");
        assertThat(custom, is(not(customClose)));
        assertThat(custom.equals(customClose), is(false));

        assertThat(areSignificantlyDifferent(custom, customClose), is(false));
    }

    @Test
    public void testAreSignificantlyDifferent_Requires3HourDifference() {
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+05:00"), getTimeZone("GMT+01:59")), is(true));
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+05:00"), getTimeZone("GMT+02:00")), is(false));
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+05:00"), getTimeZone("GMT+02:01")), is(false));

        assertThat(areSignificantlyDifferent(getTimeZone("GMT+05:00"), getTimeZone("GMT+07:59")), is(false));
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+05:00"), getTimeZone("GMT+08:00")), is(false));
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+05:00"), getTimeZone("GMT+08:01")), is(true));
    }

    @Test
    public void testAreSignificantlyDifferent_RequiresDifferenceLessThan9Hours() {
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+00:00"), getTimeZone("GMT-08:59")), is(true));
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+00:00"), getTimeZone("GMT-09:00")), is(false));
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+00:00"), getTimeZone("GMT-09:01")), is(false));

        assertThat(areSignificantlyDifferent(getTimeZone("GMT+00:00"), getTimeZone("GMT+08:59")), is(true));
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+00:00"), getTimeZone("GMT+09:00")), is(false));
        assertThat(areSignificantlyDifferent(getTimeZone("GMT+00:00"), getTimeZone("GMT+09:01")), is(false));
    }

    @Test
    public void testConstructWithDifference() {
        TimeZone original = TimeZone.getDefault();

        TimeZone result = constructWithDifference(original, 5);

        int expectedOffset = original.getRawOffset() + 5 * 60 * 60 * 1000;
        assertThat(result.getRawOffset(), is(expectedOffset));
        assertThat(result.getID(), is("Custom: " + expectedOffset + " offset from " + original.getID()));
    }

    @Test
    public void testRawDifference() {
        TimeZone zone = TimeZone.getTimeZone("GMT+05:00");
        TimeZone other = TimeZone.getTimeZone("GMT+08:00");

        assertThat(TimeZoneFactory.rawDifference(zone, other), is((8 - 5) * 60 * 60 * 1000));
    }

}
