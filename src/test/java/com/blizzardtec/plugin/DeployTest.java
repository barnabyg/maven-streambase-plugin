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
     * Streambase directory.
     */
    private transient String streambasedir;
    /**
     * Working directory.
     */
    private transient File workingDir;
    /**
     * Deploy directory.
     */
    private transient File deployDir;

    /**
     * Setup.
     */
    @Before
    public void setUp() {

        streambasedir = getBaseDir() + File.separator + "sbapps";

        workingDir = new File(getBaseDir() + File.separator + MYAPP);

        // this is the newly created deploy directory
        deployDir = new File(streambasedir + File.separator + MYAPP);
    }

    /**
     * Test the deployment of a Streambase application.
     * @throws PluginException thrown
     */
    @Test
    public void streambaseDeployTest() 
                    throws PluginException {

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
     * @throws HelperException thrown
     */
    @After
    public void cleanup() throws HelperException {
        DirectoryHelper.deleteDir(deployDir);        
    }
}
