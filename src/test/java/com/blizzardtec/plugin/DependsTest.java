/**
 * 
 */
package com.blizzardtec.plugin;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.model.Dependency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blizzardtec.testbase.AbstractTest;
import com.blizzardtec.helpers.DirectoryHelper;
import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.plugin.helper.ArtifactManager;
import com.blizzardtec.plugin.helper.DependencyHelper;

/**
 * Tests the dependency resolution mechanism.
 * NOTE: this test method currently does very little. There is no
 * easy mechanism to get the Mojo artifact classes outside of a plugin.
 * Without the artifact classes, little testing can be done.
 *
 * @author Barnaby Golden
 *
 */
public final class DependsTest extends AbstractTest {

    /**
     * Base directory to perform testing in.
     */
    private transient String dependsDir;
    /**
     * External modules directory.
     */
    private transient File modDir;

    /**
     * Setup.
     *
     * @throws HelperException thrown
     */
    @Before
    public void setUp() throws HelperException {
        dependsDir = getBaseDir() + File.separator + "dependsdir";

        modDir = new File(dependsDir + File.separator
                + DependencyHelper.EXTERNAL_MODULES);
    }

    /**
     * Test the depends class.
     *
     * @throws PluginException thrown
     */
    @Test
    public void streambaseDependsTest() throws PluginException {

        final Depends depends = new Depends();
       
        final ArrayList<Dependency> list =
            new ArrayList<Dependency>();

        final Dependency dependency = new Dependency();

        list.add(dependency);

        final ArtifactManager manager = new ArtifactManager();

        final List<ArtifactRepository> arList =
                    new ArrayList<ArtifactRepository>();

        manager.setRemoteRepos(arList);

        depends.streambaseDependency(dependsDir, list, manager);

        assertNotNull("Depends was null", depends);
    }

    /**
     * After test cleanup.
     *
     * @throws HelperException thrown
     */
    @After
    public void tearDown() throws HelperException {

        DirectoryHelper.deleteDir(modDir);
    }
}
