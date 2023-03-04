package org.ayeseeem.spectime.test.junit;

import static org.ayeseeem.spectime.test.TimeZoneFactory.definitelyNotDefaultTimeZone;

import java.util.TimeZone;

public class OtherTimeZone extends RestoreTimeZone {

    public OtherTimeZone() {
        TimeZone.setDefault(definitelyNotDefaultTimeZone());
    }

}
