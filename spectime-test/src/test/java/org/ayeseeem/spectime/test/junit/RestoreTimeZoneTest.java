package org.ayeseeem.spectime.test.junit;

import static org.ayeseeem.spectime.test.TimeZoneFactory.definitelyNotDefaultTimeZone;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.TimeZone;

import org.junit.After;
import org.junit.Test;

public class RestoreTimeZoneTest {

    private static final TimeZone initial = TimeZone.getDefault();

    @After
    public void restoreTimeZone_EvenIfFailedTestsDidNotRestoreIt() {
        TimeZone.setDefault(initial);
    }

    @Test
    public void testAfter_RestoresDefault() {
        assertThat(TimeZone.getDefault(), is(initial));

        RestoreTimeZone subject = new RestoreTimeZone();
        assertThat(TimeZone.getDefault(), is(initial));

        subject.before();
        assertThat(TimeZone.getDefault(), is(initial));

        TimeZone.setDefault(definitelyNotDefaultTimeZone());
        assertThat(TimeZone.getDefault(), is(not(initial)));

        subject.after();
        assertThat(TimeZone.getDefault(), is(initial));
    }

}
