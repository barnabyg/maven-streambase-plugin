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
public final class DeployTest extends AbstractTest {

    /**
     * Myapp.
     */
    private static final String MYAPP = "myApp";
    /**
     * Config.
     */
    private static final String CONFIG = "config";
    /**
     * Txt.
     */
    private static final String TXT = "txt";
    /**
     * Snapshot version.
     */
    private static final String SNAPSHOT = "0.0.1-SNAPSHOT";
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
     * Test the deployment of a Streambase application.
     * @throws PluginException thrown
     */
    @Test
    public void streambaseDeployTest()
                    throws PluginException {

        final String streambasedir = scratch.getPath()
                                    + File.separator + "sbapps";

        new File(streambasedir).mkdir();

        final File workingDir = new File(getBaseDir()
                                    + File.separator + "src"
                                    + File.separator + "test"
                                    + File.separator + "resources"
                                    + File.separator + MYAPP);

        // this is the newly created deploy directory
        final File deployDir = new File(streambasedir + File.separator + MYAPP);

        final String[] directorys = new String[] {CONFIG};
        final String[] extensions = new String[] {TXT};

        final DeployMojo asm = new DeployMojo();

        final Deploy deploy = new Deploy(asm.getLog(), MYAPP);

        deploy.streambaseDeploy(streambasedir,
                                workingDir.getPath(),
                                true,
                                directorys,
                                extensions,
                                SNAPSHOT);

        final File libDir =
            new File(deployDir.getPath() + File.separator + "lib");

        assertTrue("lib directory does not exist", libDir.exists());
    }

    /**
     * Test the null value failure conditions.
     *
     */
    @Test
    public void nullValuesTest() {

        final String streambasedir = scratch.getPath()
                                    + File.separator + "sbapps";

        final File workingDir = new File(scratch.getPath()
                + File.separator + MYAPP);

        final String[] directorys = new String[] {CONFIG};
        final String[] extensions = new String[] {TXT};

        final DeployMojo asm = new DeployMojo();

        final Deploy deploy = new Deploy(asm.getLog(), MYAPP);

        boolean result = false;

        try {
            deploy.streambaseDeploy(null,
                    workingDir.getPath(),
                    true,
                    directorys,
                    extensions,
                    SNAPSHOT);
        } catch (PluginException ple) {
            result = true;
        }

        assertTrue("Did not fail on bad SB dir", result);

        result = false;

        try {
            deploy.streambaseDeploy(streambasedir,
                    workingDir.getPath(),
                    true,
                    null,
                    extensions,
                    SNAPSHOT);
        } catch (PluginException ple) {
            result = true;
        }

        assertTrue("Did not fail on null directories", result);

        result = false;

        try {
            deploy.streambaseDeploy(streambasedir,
                    workingDir.getPath(),
                    true,
                    directorys,
                    null,
                    SNAPSHOT);
        } catch (PluginException ple) {
            result = true;
        }

        assertTrue("Did not fail on null extensions", result);
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
