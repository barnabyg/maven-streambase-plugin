/**
 * 
 */
package com.blizzardtec.plugin.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blizzardtec.plugin.PluginException;
import com.blizzardtec.testbase.AbstractTest;

/**
 * @author Barnaby Golden
 *
 */
public final class SettingsHelperTest extends AbstractTest {

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
     * Test the generation of the .settings directory and files.
     *
     * @throws PluginException thrown
     */
    @Test
    public void generateSettingsTest()
            throws PluginException {

        final File settingsDir =
                new File(scratch + File.separator + ".settings");

        // make sure the directory does not exist already
        assertFalse("Test directory already exists", settingsDir.exists());

        SettingsHelper.generateSettings(scratch.getPath());

        assertTrue(
              "The .settings directory was not created", settingsDir.exists());

        final File sbPrefsFile =
                new File(settingsDir.getPath() + File.separator
                                        + SettingsHelper.STREAMBASE_PREFS);

        SettingsHelper.addModuleSearchPath(sbPrefsFile, "blah/dog/badger");
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
