/**
 * 
 */
package com.blizzardtec.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * streambase:deploy command. Deploying a Streambase application.
 *
 * @author Barnaby Golden
 * @goal deploy
 */
public final class DeployMojo extends AbstractMojo {

    /**
     * Directory where pom is located.
     * @parameter expression=${basedir}
     */
    private transient String basedir;
    /**
     * Streambase target installation directory.
     * @parameter expression="${assemble.streambasedir}" default-value="INVALID"
     */
    private transient String streambasedir;
    /**
     * Artifact ID for this project.
     * @parameter expression="${project.artifactId}" default-value="INVALID"
     */
    private transient String artifactId;
    /**
     * Version of this project.
     * @parameter expression="${project.version}" default-value="INVALID"
     */
    private transient String version;
    /**
     * List of extensions to copy over.
     * @parameter
     */
    private transient String[] extensions;
    /**
     * List of directories to copy over.
     * @parameter
     */
    private transient String[] directorys;
    /**
     * Flag to indicate if we should copy over Maven generated jar files.
     * @parameter
     */
    private transient boolean copyjars;

    /* (non-Javadoc)
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    /**
     * Execute.
     * @throws MojoExecutionException thrown
     * @throws MojoFailureException thrown
     */
    public void execute() throws MojoExecutionException, MojoFailureException {

        final Deploy deploy = new Deploy(getLog(), artifactId);
        try {
            deploy.streambaseDeploy(streambasedir,
                                    basedir,
                                    copyjars,
                                    directorys,
                                    extensions,
                                    version);
        } catch (PluginException ple) {
            throw new MojoExecutionException("Plugin error: ", ple);
        }
    }
}
