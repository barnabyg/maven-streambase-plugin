/**
 * 
 */
package com.blizzardtec.plugin;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blizzardtec.testbase.AbstractTest;

/**
 * @author Barnaby Golden
 *
 */
public final class InstallTest extends AbstractTest {

    /**
     * Myapp.
     */
    private static final String MYAPP = "myApp";
    /**
     * Working folder for testing.
     */
    private static File scratch;

    /**
     * Setup.
     */
    @BeforeClass
    public static void setUp() {

        // make the scratch working directory
        scratch = new File(
                    getBaseDir() + File.separator + "scratch");

        scratch.mkdir();
    }

    /**
     * Test the Streambase install Mojo worker class.
     *
     * @throws PluginException thrown
     */
    @Test
    public void streambaseInstallTest()
            throws PluginException {

        final String workingDir = getBaseDir()
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources"
                + File.separator + MYAPP;

        final File sbDir = new File(workingDir + File.separator + "src"
                + File.separator + "main" + File.separator
                + "resources" + File.separator + Install.SB_DIR);

        final Install install = new Install();

        final String[] directorys = {"config", "target"};
        final String[] extensions = {"txt"};

        install.streambaseInstall(workingDir, directorys, extensions);

        assertTrue("No streambase directory found", sbDir.exists());
    }

    /**
     * Cleanup.
     *
     * @throws IOException thrown
     */
    @AfterClass
    public static void tearDown() throws IOException {

        FileUtils.deleteDirectory(
                new File(getBaseDir() + File.separator + "scratch"));

    }
}
