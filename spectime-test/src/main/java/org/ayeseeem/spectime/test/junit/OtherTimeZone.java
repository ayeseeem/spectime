package org.ayeseeem.spectime.test.junit;

import java.util.TimeZone;

public class OtherTimeZone extends RestoreTimeZone {

	public OtherTimeZone() {
		TimeZone.setDefault(definitelyNotDefaultTimeZone());
	}

}
