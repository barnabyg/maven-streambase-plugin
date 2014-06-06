/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.testbase.AbstractTest;

/**
 * @author Barnaby Golden
 *
 */
public final class ConfReaderTest extends AbstractTest {

    /**
     * Get configuration lists test.
     *
     * @throws HelperException thrown
     */
    @Test
    public void getConfigurationListsTest() throws HelperException {

        final File confFile =
            new File(getResourceDir() + File.separator + "sbd.sbconf");

        final ConfReader confReader = new ConfReader();

        confReader.load(confFile);

        final ConfList list = confReader.getConfigurationLists();

        assertNotNull("Configuration list was null", list);
    }
}
