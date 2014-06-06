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

import com.blizzardtec.helpers.FileHelper;
import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.testbase.AbstractTest;

/**
 * @author Barnaby Golden
 *
 */
public final class StudioTest extends AbstractTest {

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
     *
     * @throws HelperException thrown
     */
    @BeforeClass
    public static void setUp() throws HelperException {


        // make the scratch working directory
        scratch = new File(
                    getBaseDir() + File.separator + "scratch");

        scratch.mkdir();
    }
   
    /**
     * Test the Streambase Studio command.
     * @throws PluginException thrown
     */
    @Test
    public void streambaseStudioTest()
                throws PluginException {

        final String studioDir = scratch.getPath() + File.separator + "studiodir";

        new File(studioDir).mkdir();

        final File sbdLocalFile = new File(getBaseDir()
                                    + File.separator + "src"
                                    + File.separator + "test"
                                    + File.separator + "resources"
                                    + File.separator + "sbd.local");

        try {

            FileHelper.copyFile(
                    sbdLocalFile, new File(
                                studioDir + File.separator + "sbd.local"));

        } catch (HelperException he) {
            throw new PluginException(he);
        }

        final Studio studio = new Studio();

        studio.streambaseStudio(studioDir, MYAPP);

        // test successful operation
        final File settingsDir = new File(studioDir + File.separator + ".settings");

        final File projectFile = new File(studioDir + File.separator + ".project");

        final File classpathFile = new File(studioDir + File.separator + ".classpath");

        final File sbConfFile = new File(studioDir + File.separator + "sbd.sbconf");

        assertTrue(
                ".project file does not exist", projectFile.exists());
        assertTrue(
                ".classpath file does not exist", classpathFile.exists());
        assertTrue(
                ".settings directory does not exist", settingsDir.exists());
        assertTrue(
                "sbd.sbconf file was not generated", sbConfFile.exists());

        // run the test again to make sure it deals with existing files
        studio.streambaseStudio(studioDir, MYAPP);
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
