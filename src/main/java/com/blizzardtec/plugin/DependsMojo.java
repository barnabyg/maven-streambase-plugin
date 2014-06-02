/**
 * 
 */
package com.blizzardtec.plugin;

import java.util.List;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.blizzardtec.plugin.helper.ArtifactManager;

/**
 * streambase:depends maven plugin command.
 * This command resolves dependencies for the project
 * and copies necessary files in to the external modules
 * directory. It also updates sbd.sbconf as needed.
 *
 * @author Barnaby Golden
 * @goal depends
 */
public final class DependsMojo extends AbstractMojo {

    /**
     * @parameter expression="${project.dependencies}"
     */
    private transient List<Dependency> dependencies;
    /**
     * Used to look up Artifacts in the remote repository.
     * 
     * @parameter expression=
     *  "${component.org.apache.maven.artifact.factory.ArtifactFactory}"
     * @required
     * @readonly
     */
    private transient ArtifactFactory factory;
    /**
     * @parameter default-value="${localRepository}"
     */
    private transient ArtifactRepository localRepository;
    /**
     * Used to look up Artifacts in the remote repository.
     * 
     * @parameter expression=
     *  "${component.org.apache.maven.artifact.resolver.ArtifactResolver}"
     * @required
     * @readonly
     */
    private transient ArtifactResolver artifactResolver;
    /**
     * @parameter default-value="${project.remoteArtifactRepositories}"
     */
    private transient List<ArtifactRepository> remoteRepositories;

    /**
     * Execute.
     * @throws MojoExecutionException thrown
     * @throws MojoFailureException thrown
     */
    public void execute() throws MojoExecutionException, MojoFailureException {

        // current working directory
        final String workingDir = System.getProperty("user.dir");

        final ArtifactManager manager = new ArtifactManager();

        manager.setArtifactResolver(artifactResolver);
        manager.setFactory(factory);
        manager.setRemoteRepos(remoteRepositories);
        manager.setRepo(localRepository);

        final Depends depends = new Depends();

        try {
            depends.streambaseDependency(workingDir, dependencies, manager);
        } catch (PluginException ple) {
            throw new MojoExecutionException("Plugin: ", ple);
        }
    }
}
