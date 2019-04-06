spectime
========

[![Build Status](https://travis-ci.org/ayeseeem/spectime.svg?branch=master)](https://travis-ci.org/ayeseeem/spectime)

Expressive dates and times in Java. These grew out of my frustration writing
unit tests that needed times and dates. The ideal solution is to not need such
tests, but it can't always be helped.


Quick Start
-----------

`spectime` makes it easy to create relative dates and times in Java. If you can
read the following snippets, you already know what it is trying to do:

```java
Date earlier
    = date(3).days().before(someDate);
Date later
    = time(5).seconds().after(someDate);
Date future
    = time(5).hours().from(now());
Date past
    = time(5).seconds().ago();

Date someTime
    = date(5).days().and(2).seconds().and(3).milliseconds().ago();
```

```java
Date d1 = date("2013-12-24");
Date d2 = date("2013-12-24 23:59");
Date d3 = date("2013-12-24 23:59:59");
Date d4 = date("2013-12-24 23:59:59.123");
```

- `date()` and `time()` are synonyms - use whichever is clearer
- Specify relative time/date using:
    - `milliseconds()`
    - `seconds()`
    - `minutes()`
    - `hours()`
    - `days()`
    - `weeks()`
    - `months()`
    - `years()`
- Singular versions of each are available - for example `hour()` for `hours()`
- `startOf(date)` gets the start of the day (midnight) for the given date

To get access to these methods and allow clean code, use static import:

```java
import static org.ayeseeem.spectime.TimeFactory.date;
```

If you use Eclipse, add the `org.ayeseeem.spectime.TimeFactory` type to the
Content Assist Favorites, so Eclipse will suggest the methods when the import
is missing.

To see more examples, see
[ExamplesTest.java](https://github.com/ayeseeem/spectime/blob/master/spectime-core/src/test/java/org/ayeseeem/spectime/example/ExamplesTest.java "Examples")


### Tips for Unit Testing

See [`spectime-test`](#spectime-test), below.

Or - when `toString()` is implemented (see TODOs) - do this:

```java
assertEquals(d1.getTime(), d2.getTime());
```

Or, to test times with a tolerance, do something like this:

```java
assertEquals(d1.getTime(), d2.getTime(), 3);
```

JUnit 4 allows timing of test like this:

```java
@Test(timeout = 1000)
public void someTest() {
    potentiallyLongMethod();
}
```

This will fail if the timeout is exceeded.


### <a id="spectime-test">spectime-test</a> ###

Contains JUnit extensions, for example `assertDateEquals(Date, Date)`, which
has a better error message (including milliseconds) than normal JUnit
`assertEquals`.


### Alternatives

If you want "simple social date-formatting" or want to parse natural language
strings, perhaps try
[PrettyTime](http://ocpsoft.org/prettytime/ "PrettyTime - simple social date-formatting") - 'Convert Java `Date()` objects in just "a few minutes!"'


TODOs
-----

### Date constructor methods

- [ ] `time("13:59")`
- [ ] `time("13:59:59")`
- [ ] `time("13:59:59.123")`
- Some kind of "definitely later" date constructor. For example, when you
  create a bunch of dates using something like `now()`, it's possible that some
  of them will have exactly the same time, and you might then be surprised that
  they don't sort in the order you expect. In the past, I've done this using a
  small `Thread.sleep()`.
    - possibly use a `tick()` method that can take an optional amount, default
      to 1 ms.

### Internals

- [ ] Sort out `equals` and `hashCode` methods for `DtInterval` and `DtNumber`
      to properly handle the `previous` fields. (How) Do we handle different
      chains of `previous` that have same effect? Could apply them all to a
      known, fixed date to get a result?

### Also under consideration

- `toString(Date)`
- Tips on unit testing like `assertEquals(d1.getTime(), d2.getTime(), 3);` and
  `assertEquals(toString(d1), toString(d2));` instead of `spectime-test`
- `next(Wednesday)`
- `last(Thursday)`
- `thisTime(date)` creates a date/time with the current time on the given date,
    e.g. `thisTime(next(Wednesday))`
- `tomorrow()`, `yesterday()`
- `since(date)` and `until(date)` to calculate time since/until date
- `year(2013)`
- `next(March)`, `next(week)`, `next(year)`
- Test helpers like
    - `assertDateEquals` with milliseconds tolerance
    - `assertDateEquals` with tolerance in other units
    - `assertDayEquals` (ignores time, just checks date)
    - `assertTimeEquals` (ignores date, just checks time)
    - `assertDateEquals("2013-04-01 10:12", aDate)`? Or use
      `assertDateEquals(date("2013-04-10 10:12"), aDate)`?

Coding Standard
---------------

Currently uses the "Eclipse [Built-in]" settings in Eclipse/Spring STS, for
Java > Code Style's Clean Up and Formatter.

- This includes using (4-space) tabs for indentation.
- **But** the line length for code is limited to 80 characters
    - The limit for comments is also 80 characters, but
      "from comment's starting position" (the default settings).
      The starting position bit is nice because it means that comment blocks
      don't need to be reformatted when the commented code's indentation level
      changes, for example when it is refactored to move it into or out of nested
      classes.

Don't (usually) modify method parameters.
But don't (usually) use `final` on (every) method parameter, to prevent
it - we think it makes the code unhelpfully verbose.
Instead, turn on an IDE rule or code linter to warn on reassigned parameters.
Only use `final` on method parameters in special cases - for example if the
method is so long that it is hard to see at a glance that the parameters are
not changed (although, avoid such long methods!). Or where some of the
parameters _are_ reassigned - use `final` to mark the ones that are not.
