package org.ayeseeem.spectime.test.junit;

import static org.ayeseeem.spectime.test.TimeZoneFactory.definitelyNotDefaultTimeZone;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class RestoreTimeZoneExampleTest {

    private static final TimeZone initial = TimeZone.getDefault();

    @After
    public void restoreTimeZone_EvenIfFailedTestsDidNotRestoreIt() {
        TimeZone.setDefault(initial);
    }

    @Ignore // Only test via our special runner
    public static class ExampleTestChangingTimeZone {

        @Before
        public void ensureStartingTestWithZimeZoneIsDefault() {
            assertThat(TimeZone.getDefault(), is(initial));
        }

        @Rule
        public final TestRule restoreTimeZone = new RestoreTimeZone();

        @Test
        public void testChangingTheTimeZone() {
            // Modify the time zone
            TimeZone.setDefault(definitelyNotDefaultTimeZone());
            assertThat(TimeZone.getDefault(), is(not(initial)));
        }
    }

    @Ignore // Only test via our special runner
    public static class ExampleTestChangingTimeZone_AndCrashing {

        @Before
        public void ensureStartingTestWithZimeZoneIsDefault() {
            assertThat(TimeZone.getDefault(), is(initial));
        }

        @Rule
        public final TestRule restoreTimeZone = new RestoreTimeZone();

        @Test
        public void testChangingTheTimeZone() {
            // Modify the time zone
            TimeZone.setDefault(definitelyNotDefaultTimeZone());
            assertThat(TimeZone.getDefault(), is(not(initial)));

            fail("force failure after changing time zone");
        }
    }

    public static class LocalRunner extends BlockJUnit4ClassRunner {

        public LocalRunner(Class<?> testClass) throws InitializationError {
            super(testClass);
        }

    }

    @Test
    public void testTimeZoneIsRestored_AfterPassingTest_UsingRunnerToEnsureRuleIsExercised() throws Exception {

        RunNotifier notifier = new RunNotifier() {
            @Override
            public void fireTestFailure(Failure failure) {
                throw new RuntimeException(failure.getException());
            }
        };

        // Run "expected to pass" tests
        LocalRunner runner = new LocalRunner(ExampleTestChangingTimeZone.class);
        runner.run(notifier);

        assertThat(TimeZone.getDefault(), is(initial));
    }

    @Test
    public void testTimeZoneIsRestored_AfterFailingTest_UsingRunnerToEnsureRuleIsExercised() throws Exception {

        final List<Failure> failures = new ArrayList<Failure>();
        RunNotifier notifier = new RunNotifier() {
            @Override
            public void fireTestFailure(Failure failure) {
                failures.add(failure);
            }
        };

        // Run "expected to fail" tests
        LocalRunner runner = new LocalRunner(ExampleTestChangingTimeZone_AndCrashing.class);
        runner.run(notifier);

        assertThat(TimeZone.getDefault(), is(initial));
        assertThat(failures.get(0).getMessage(), is("force failure after changing time zone"));
    }

}
