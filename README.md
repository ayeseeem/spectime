spectime
========

[![Build Status](https://travis-ci.com/ayeseeem/spectime.svg?branch=java-6-modern)](https://travis-ci.com/github/ayeseeem/spectime)

Expressive dates and times in Java. These grew out of my frustration writing
unit tests that needed times and dates. The ideal solution is to not need such
tests, but it can't always be helped.

**Note** that there is a non-standard branch structure.
The default branch is [`java-6-modern`](https://github.com/ayeseeem/spectime/tree/java-6-modern).
Later changes are in [`java-8`](https://github.com/ayeseeem/spectime/tree/java-8).
See Development below for more details.


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
[`ExamplesTest.java`](https://github.com/ayeseeem/spectime/blob/master/spectime-core/src/test/java/org/ayeseeem/spectime/example/ExamplesTest.java "Examples")


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


### <a id="spectime-test">spectime-test</a>

Contains JUnit extensions, for example `assertDateEquals(Date, Date)`, which
has a better error message (including milliseconds) than normal JUnit
`assertEquals`.


### Alternatives

If you want "simple social date-formatting" or want to parse natural language
strings, perhaps try
[PrettyTime](http://ocpsoft.org/prettytime/ "PrettyTime - simple social date-formatting") - 'Convert Java `Date()` objects in just "a few minutes!"'

[ThreeTen-Backport](https://www.threeten.org/threetenbp/) provides a backport
of the Java SE 8 date-time classes to Java SE 6 and 7.


TODOs
-----

### Date constructor methods

- [ ] `time("13:59")`
- [ ] `time("13:59:59")`
- [ ] `time("13:59:59.123")`
- [ ] Some kind of "definitely later" date constructor. For example, when you
  create a bunch of dates using something like `now()`, it's possible that some
  of them will have exactly the same time, and you might then be surprised that
  they don't sort in the order you expect. In the past, I've done this using a
  small `Thread.sleep()`.
  - possibly use a `tick()` method that can take an optional amount, default
    to 1 ms.


### Internals

- [ ] Check that this all works with other default/system time zones!
- [ ] Sort out `equals` and `hashCode` methods for `DtInterval` and `DtNumber`
      to properly handle the `previous` fields. (How) Do we handle different
      chains of `previous` that have same effect? Could apply them all to a
      known, fixed date to get a result?


### Long-Term Support and Future

- [x] Add to the build mechanism to ensure Java 1.3 - 1.6 code consistency
      (Need to settle on the exact target version, basically this is aimed at
      Java before `Instant` and so on.)
- [ ] Work out how to split into a Java 6 and Java 8+ version. Create a new
      version for Java8+ - how much is still needed, how much can be done with
      Standard Java 8+ date/time?


### Also under consideration

- `toString(Date)` - see
  [`TimeFactory.stringOf(Date)`](spectime-core/src/main/java/org/ayeseeem/spectime/TimeFactory.java),
  which "round trips" to/from the `date(String)` function.
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


Development
-----------

Branch          | Status
--------------- |-------------------------------------------------------------
`java-8`        | [![Build Status](https://travis-ci.com/ayeseeem/spectime.svg?branch=java-8)](https://travis-ci.com/github/ayeseeem/spectime)
`java-6-modern` | [![Build Status](https://travis-ci.com/ayeseeem/spectime.svg?branch=java-6-modern)](https://travis-ci.com/github/ayeseeem/spectime)
`java-6-legacy` | [![Build Status](https://travis-ci.com/ayeseeem/spectime.svg?branch=java-6-legacy)](https://travis-ci.com/github/ayeseeem/spectime)


We are trying to support a Java 6 version and a Java 8+ version.
At the moment, the Java8+ version is still deliberately and explicitly Java 8
only - nothing later.

The reason for this split is that Java 8 is where Java changed the time system,
introducing things like `Instant`.
The Java 6 version is intended to work in the absence of such features: it is
one of the reasons that `spectime` was originally written.

The current (experimental) approach is this: We have two main development
branches - `java-6-modern` and `java-8`.
`java-6-legacy` contains the same code as `java-6-modern`, but builds with
legacy JDK 6.

For development, make universal, Java 6 code modifications to `java-6-modern`
and then merge those changes forward into `java-8` and `java-6-legacy`.
Java-8-specific code changes should be made to `java-8`.
The only changes that should be made directly to `java-6-legacy` should be
related to building the code for legacy JDK 6.

It is important that common changes are made to the Java 6 code base,
so `java-6-modern` is set as the default branch.
This is the one that Pull Requests will be made against, by default.
This should prevent accidentally making changes in `java-8` that should be
in `java-6-modern`.
Conversely, If Java 8 changes are made to this branch, they should fail to
compile, highlighting that the changes are being made to the wrong branch.

- At some point, a `latest` branch might be created, building with/for
  the latest versions of Java.
- A `java-<N>` might appear at some point.

Take particular care with build-related changes: it might be that they should
not be added to the main development branches at all, but to specific
build/release branches (currently, only `java-6-legacy`).
However, bear in mind that the main development branches should be buildable
as they stand, in relatively simple development environments:

- `java-8` should build with any Java JDK 8, but particularly Open JDK.
- `java-6-modern` should build with "recent" JDKs like JDK 11(+?).
  Or see `java-6-modern` for specific build hints.
  - To build with an old Java 6 JDK (Oracle or Open JDK), look at the details
    of `java-6-legacy`.
- Any future `latest` branch should build with the latest Java release.

**Note** that this branch approach is still experimental.
Branches are liable to be rebased/republished, so do not base any long-term
development off them unless you are prepared to have to re-work/rebase your
work at some point.


Coding Standard
---------------

Basic standard is [icm-java-style](https://github.com/ayeseeem/icm-java-style/),
with the following notes:

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


### Rules

- Don't use `public` on interface methods
  [JLS Method Declarations](https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.4)
  says this (our emphasis):
    > Every method declaration in the body of an interface is implicitly
    > public (§6.6). It is permitted, but **discouraged as a matter of style**,
    > to redundantly specify the public modifier for a method declaration in
    > an interface.


### Coding Standard TODOs

- [ ] Look for `final` variables that can be removed by inlining the variable
- [ ] Prefer `subject` to `test` in unit tests
