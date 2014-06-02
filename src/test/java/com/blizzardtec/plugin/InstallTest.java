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
public final class InstallTest extends AbstractTest {

    /**
     * Working directory.
     */
    private transient String workingDir;
    /**
     * Streambase install directory.
     */
    private transient File sbDir;

    /**
     * Setup.
     */
    @Before
    public void setUp() {

        workingDir = getBaseDir() + File.separator + "myApp";

        sbDir = new File(workingDir + File.separator + "src"
                + File.separator + "main" + File.separator
                + "resources" + File.separator + Install.SB_DIR);
    }

    /**
     * Test the Streambase install Mojo worker class.
     *
     * @throws PluginException thrown
     */
    @Test
    public void streambaseInstallTest()
            throws PluginException {

        final Install install = new Install();

        final String[] directorys = {"config", "target"};
        final String[] extensions = {"txt"};

        install.streambaseInstall(workingDir, directorys, extensions);

        assertTrue("", sbDir.exists());
    }

    /**
     * Cleanup.
     *
     * @throws HelperException thrown
     */
    @After
    public void cleanup() throws HelperException {

        DirectoryHelper.deleteDir(sbDir);        
    }
}
