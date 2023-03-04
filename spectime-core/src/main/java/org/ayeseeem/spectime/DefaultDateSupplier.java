package org.ayeseeem.spectime;

import java.util.Date;

/**
 * Default implementation of {@code DateSupplier}.
 */
public class DefaultDateSupplier implements DateSupplier {

    /**
     * Gets the current date by returning {@code new Date()}.
     */
    @Override
    public Date get() {
        return new Date();
    }

}
