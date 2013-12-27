spectime
========

Expressive dates and times in Java. These grew out of my frustration writing unit tests that needed times and dates. The ideal solution is to not need such tests, but it can't always be helped. 


Quick Start
-----------

`spectime` makes it easy to create relative dates and times in java. If you can read the following snippets, you already know what it is trying to do:

```java
Date earlier
    = date(3).days().before(someDate);

Date later
    = time(5).seconds().after(someDate);

Date future
    = time(5).hours().from(now());

Date past
    = time(5).seconds().ago();

```
```java
Date anotherDate = date("2013-12-24");
```
* `date()` and `time()` are synonyms - use whichever is clearer
* specify relative time/date using:
    * `milliseconds()`
    * `seconds()`
    * `minutes()`
    * `hours()`
    * `days()`
    * `weeks()`
    * `months()`
    * `years()`
* singular versions of each are available - for example `hour()` for `hours()`

To get access to these methods and allow clean code, use static import:
```java
import static org.karstpat.spectime.TimeFactory.date;
```
If you use Eclipse, add the `org.karstpat.spectime.TimeFactory` type to the Content Assist Favorites, so Eclipse will suggest the methods when the import is missing.

To see more examples, see [ExamplesTest.java](https://github.com/ayeseeem/spectime/blob/master/src/test/java/org/karstpat/spectime/example/ExamplesTest.java "Examples")

### Alternatives

If you want "simple social date-formatting" or want to parse natural language strings, perhaps try [PrettyTime] (http://ocpsoft.org/prettytime/ "PrettyTime - simple social date-formatting") - 'Convert Java Date() objects in just “a few minutes!”'


TODOs
-----

Relative
- [x] `after()` as synonym for `from()`
- [x] `ago()` - synonym for `before(now)`
- [x] Drop `fromNow()`when helper `now()` is created - use `from(now())`

Time units
- [x] `weeks()` (and `week()`)
- [ ] `and()` to allow chaining: `time(3).hours().and(5).minutes().ago()`

Date constructor methods
- [x] `now()` convenience method
- [ ] `time("13:59")`
- [ ] `time("13:59:59")`
- [ ] `time("13:59:59.123")`
- [ ] `date("2013-12-24 13:59")`
- [ ] `date("2013-12-24 13:59:59")`
- [ ] `date("2013-12-24 13:59:59.123`

Also under consideration:
- `next(Wednesday)`
- `last(Thursday)`
- `thisTime(date)` creates a date/time with the current time on the given date, e.g. `thisTime(next(Wednesday))`
- `tomorrow()`, `yesterday()`
- `since(date)` and `until(date)` to calculate time since/until date
- `year(2013)`
- `next(March)`, `next(week)`, `next(year)`
- `startOf(date)` to get midnight start of a date
- test helpers like
    - assertDateEquals - like assertEquals(Date, Date) but with a better error
      message, including milliseconds
    - assertDateEquals with milliseconds tolerance
    - assertDateEquals with tolerance in other units
    - assertDayEquals (ignores time, just checks date)
    - assertTimeEquals (ignores date, just checks time)
    - assertDateEquals("2013-04-01 10:12", aDate)? Or use 
      assertDateEquals(date("2013-04-10 10:12"), aDate)?


