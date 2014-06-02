/**
 * 
 */
package com.blizzardtec.plugin;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blizzardtec.helpers.DirectoryHelper;
import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.testbase.AbstractTest;

/**
 * @author Barnaby Golden
 *
 */
public final class ArchtypeTest extends AbstractTest {

    /**
     * Archtype directory.
     */
    private transient File archtypeDir;

    /**
     * Setup.
     */
    @Before
    public void setUp() {

        // create a directory to run the test in
        archtypeDir =
            new File(getBaseDir() + File.separator + "archtypedir");

        archtypeDir.mkdir();
    }

    /**
     * Test the Streambase archtype command.
     * @throws PluginException thrown
     */
    @Test
    public void streambaseArchtypeTest()
            throws PluginException {

        final Archtype archtype =
            new Archtype();

        archtype.streambaseArchtype(
                archtypeDir.getPath(), "myGroup", "myProject");

        final File srcDir =
            new File(archtypeDir.getPath() + File.separator + "src");

        assertTrue("src directory does not exist", srcDir.exists());
    }

    /**
     * Cleanup.
     *
     * @throws HelperException thrown
     */
    @After
    public void cleanup() throws HelperException {

        DirectoryHelper.deleteDir(archtypeDir);
    }
}
