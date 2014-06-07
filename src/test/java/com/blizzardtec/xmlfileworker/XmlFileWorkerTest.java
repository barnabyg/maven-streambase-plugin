/**
 *
 */
package com.blizzardtec.xmlfileworker;

import org.junit.Test;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.streambase.ConfModifier;

/**
 * @author Barnaby Golden
 *
 */
public final class XmlFileWorkerTest {

    /**
     * Test the functionality of the xmlFileWorker classes.
     * @throws HelperException thrown
     */
    @Test(expected = HelperException.class)
    public void xmlFileWorkerTest() throws HelperException {
        final ConfModifier modifier = new ConfModifier();
        modifier.save();
    }
}
