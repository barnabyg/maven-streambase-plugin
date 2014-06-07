/**
 *
 */
package com.blizzardtec.plugin;

import java.util.List;

import org.apache.maven.model.Dependency;

import com.blizzardtec.plugin.helper.ArtifactManager;
import com.blizzardtec.plugin.helper.DependencyHelper;

/**
 * Worker class for the DependsMojo.
 * The main use of this class is to provide a convenient
 * entry point for unit tests.
 *
 * @author Barnaby Golden
 *
 */
public final class Depends {

    /**
     * Resolve dependencies, copy them locally
     * and update the sbd.sbconf file as required.
     *
     * @param workingDir current working directory
     * @param dependencies list of dependencies
     * @param manager artifact manager
     * @throws PluginException thrown
     */
    public void streambaseDependency(
            final String workingDir,
            final List<Dependency> dependencies,
            final ArtifactManager manager)
                        throws PluginException {

        // resolve dependencies and copy the contents locally
        // note that this also updates sbd.sbconf with any
        // dependency aliases
        DependencyHelper.dependentProjects(
                    workingDir, dependencies, manager);
    }
}
