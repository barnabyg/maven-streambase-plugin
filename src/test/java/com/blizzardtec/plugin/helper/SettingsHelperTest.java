/**
 * 
 */
package com.blizzardtec.plugin.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blizzardtec.helpers.DirectoryHelper;
import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.plugin.PluginException;
import com.blizzardtec.testbase.AbstractTest;

/**
 * @author Barnaby Golden
 *
 */
public final class SettingsHelperTest extends AbstractTest {

    /**
     * Test directory.
     */
    private static final String TEST_DIR = "testdir";
    /**
     * .settings directory.
     */
    private transient File settingsDir;
    /**
     * Streambase preferences file.
     */
    private transient File sbPrefsFile;
    /**
     * Path to test directory.
     */
    private transient String testPath;

    /**
     * Setup.
     */
    @Before
    public void setUp() {

        testPath = getBaseDir() + File.separator + TEST_DIR;

        settingsDir = new File(testPath + File.separator + ".settings");

        sbPrefsFile =
            new File(settingsDir.getPath() + File.separator
                                    + SettingsHelper.STREAMBASE_PREFS);
    }

    /**
     * Test the generation of the .settings directory and files.
     *
     * @throws PluginException thrown
     */
    @Test
    public void generateSettingsTest()
            throws PluginException {
        
        // make sure the directory does not exist already
        assertFalse("Test directory already exists", settingsDir.exists());

        SettingsHelper.generateSettings(testPath);

        assertTrue(
              "The .settings directory was not created", settingsDir.exists());

        SettingsHelper.addModuleSearchPath(sbPrefsFile, "blah/dog/badger");
    }

    /**
     * Cleanup.
     *
     * @throws HelperException thrown
     */
    @After
    public void cleanup() throws HelperException {

        DirectoryHelper.deleteDir(settingsDir);
    }
}
