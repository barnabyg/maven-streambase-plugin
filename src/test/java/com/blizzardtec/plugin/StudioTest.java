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
public final class StudioTest extends AbstractTest {

    /**
     * Myapp.
     */
    private static final String MYAPP = "myApp";
    /**
     * Test directory for studio command.
     */
    private transient String studioDir;
    /**
     * .settings directory.
     */
    private transient File settingsDir;
    /**
     * .project file.
     */
    private transient File projectFile;
    /**
     * .classpath file.
     */
    private transient File classpathFile;
    /**
     * sbd.sbconf file.
     */
    private transient File sbConfFile;

    /**
     * Setup.
     *
     * @throws HelperException thrown
     */
    @Before
    public void setUp() throws HelperException {

        studioDir = getBaseDir() + File.separator + "studiodir";

        settingsDir = new File(studioDir + File.separator + ".settings");

        projectFile = new File(studioDir + File.separator + ".project");

        classpathFile = new File(studioDir + File.separator + ".classpath");

        sbConfFile = new File(studioDir + File.separator + "sbd.sbconf");
    }
   
    /**
     * Test the Streambase Studio command.
     * @throws PluginException thrown
     */
    @Test
    public void streambaseStudioTest()
                throws PluginException {

        final Studio studio = new Studio();

        studio.streambaseStudio(studioDir, MYAPP);

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
     * After test cleanup.
     *
     * @throws HelperException thrown
     */
    @After
    public void tearDown() throws HelperException {

        DirectoryHelper.deleteDir(settingsDir);

        projectFile.delete();
        classpathFile.delete();

        sbConfFile.delete();
    }
}
