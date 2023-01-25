package org.ayeseeem.spectime.test;

import static org.ayeseeem.spectime.test.ZonedTester.anyZone;
import static org.ayeseeem.spectime.test.ZonedTester.exclusiveZones;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class ZonedTesterTest {

	//@Characterization
	@Test
	public void exampleAnyZone_CodeThatWorks() throws Throwable {
		anyZone(new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				// Java 8: Date.from(Instant.parse("2023-12-31T23:58:59.789Z"))
				{
					Calendar local = Calendar.getInstance();
					local.setTimeInMillis(1704067139789L);

					// Time-zone-invariant tests
					assertThat(local.getTimeInMillis(), is(1704067139789L));
					assertThat(local.getTimeZone(), is(TimeZone.getDefault()));
				}
			}
		});
	}

	//@Characterization
	@Test
	public void exampleAnyZone_CodeThatFails() {
		Error expected = assertThrows(AssertionError.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				anyZone(new ThrowingRunnable() {
					@Override
					public void run() throws Throwable {
						// Java 8: Date.from(Instant.parse("2023-12-31T23:58:59.789Z"))
						{
							Calendar local = Calendar.getInstance();
							local.setTimeInMillis(1704067139789L);

							// Time-zone-dependent tests
							assertThat("Hour check", local.get(Calendar.HOUR_OF_DAY), is(23));
						}
					}
				});
			}
		});
		assertThat(expected.getMessage(), containsString("Hour check"));
	}

	@Test
	public void testTestInMultipleZones_ExecutesTestMoreThanOnce() throws Throwable {
		final List<String> testLog = new ArrayList<String>();

		ThrowingRunnable test = new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				testLog.add("the test");
			}
		};
		ZonedTester subject = new ZonedTester(test);

		subject.testInMultipleZones();

		assertThat(testLog.size() > 1, is(true));
	}

	@Test
	public void testTestInMultipleZones_UsesMultipleTimeZones() throws Throwable {
		final List<TimeZone> zonesUsed = new ArrayList<TimeZone>();

		ThrowingRunnable test = new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				zonesUsed.add(TimeZone.getDefault());
			}
		};
		ZonedTester subject = new ZonedTester(test);

		subject.testInMultipleZones();

		assertThat(zonesUsed.size() > 1, is(true));
	}

	//@Characterization
	@Test
	public void testTestInMultipleZones_WhichZones() throws Throwable {
		final List<TimeZone> zonesUsed = new ArrayList<TimeZone>();

		ThrowingRunnable test = new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				zonesUsed.add(TimeZone.getDefault());
			}
		};
		ZonedTester subject = new ZonedTester(test);
		subject.testInMultipleZones();

		assertThat(zonesUsed.size(), is(4));
		assertThat(zonesUsed, hasItem(TimeZone.getTimeZone("UTC")));
		assertThat(zonesUsed, hasItem(TimeZone.getTimeZone("Europe/London")));
		assertThat(zonesUsed, hasItem(TimeZone.getTimeZone("US/Eastern")));
		assertThat(zonesUsed, hasItem(TimeZone.getTimeZone("Australia/Perth")));
	}

	@Test
	public void testTestInMultipleZones_CapturesCodeErrors() {
		final ZonedTester subject = new ZonedTester(new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				throw new RuntimeException("Faked an error while executing");
			}
		});

		Exception expected = assertThrows(RuntimeException.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				subject.testInMultipleZones();
			}
		});
		assertThat(expected.getMessage(), is("Faked an error while executing"));
	}

	@Test
	public void testTestInMultipleZones_PropagatesTestFailure() {
		final ZonedTester subject = new ZonedTester(new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				fail("Simulated test failure");
			}
		});

		Error expected = assertThrows(AssertionError.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				subject.testInMultipleZones();
			}
		});
		assertThat(expected.getMessage(), is("Simulated test failure"));
	}

	@Test
	public void testTestInMultipleZones_RestoresDefault() {
		final TimeZone original = TimeZone.getDefault();

		final List<TimeZone> zonesUsed = new ArrayList<TimeZone>();

		final ZonedTester subject = new ZonedTester(new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				TimeZone currentDefault = TimeZone.getDefault();

				// Ensure not using original time zone
				if (!currentDefault.equals(original)) {
					zonesUsed.add(currentDefault);
					fail("Simulated test failure while time zone was different from original system");
				}
			}
		});

		Error expected = assertThrows(AssertionError.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				subject.testInMultipleZones();
			}
		});
		assertThat(expected.getMessage(), is("Simulated test failure while time zone was different from original system"));

		// Verify the test used a different time zone
		assertThat(zonesUsed.size(), is(1));
		assertThat(zonesUsed.get(0).equals(original), is(false));

		// Verify original was restored
		assertThat(TimeZone.getDefault(), is(original));
	}

	@AfterClass
	public static void doubleCheckNothingBroken() {
		TimeZone current = TimeZone.getDefault();
		assertThat(current, is(ZonedTester.initial));
	}

	//@Characterization
	@Test
	public void testExclusiveZones() {
		List<TimeZone> zones = exclusiveZones();
		assertThat(zones.size() > 1, is(true));
		assertThat(zones.size(), is(4));
	}

	@Test
	public void testExclusiveZones_AreAllDifferent() {
		List<TimeZone> zones = exclusiveZones();

		Set<TimeZone> setOfZones = new HashSet<TimeZone>(zones);
		assertThat(zones.size(), is(setOfZones.size()));
	}

	@Test
	public void testExclusiveZones_OffsetsAreNotNecessarillyAllDifferent() {
		List<TimeZone> zones = exclusiveZones();

		List<Integer> offsets = new ArrayList<Integer>();
		for (TimeZone tz : zones) {
			offsets.add(tz.getRawOffset());
		}
		assertThat(offsets.size() > 1, is(true));

		Set<Integer> setOfOffsets = new HashSet<Integer>(offsets);
		assertThat(setOfOffsets.size() > 1, is(true));

		assertThat(offsets.size() >= setOfOffsets.size(), is(true));
	}

	//@Characterization
	@Test
	public void testExclusiveZones_ListIsModifiable() {
		List<TimeZone> zones = exclusiveZones();
		int originalSize = zones.size();

		zones.remove(0);
		assertThat(zones.size(), is(originalSize - 1));
	}

	@Test
	public void testExclusiveZones_CreateSameListEachTime() {
		List<TimeZone> zonesA = exclusiveZones();
		List<TimeZone> zonesB = exclusiveZones();
		assertThat(zonesA, is(zonesB));
	}

	@Test
	public void testExclusiveZones_CreateNewList() {
		// Modifying one list does not affect the next
		List<TimeZone> zones = exclusiveZones();
		zones.remove(0);

		assertThat(zones.size(), is(exclusiveZones().size() - 1));
	}

}
