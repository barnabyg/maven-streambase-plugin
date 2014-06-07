/**
 *
 */
package com.blizzardtec.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * streambase:studio maven plugin command.
 * This command generates the Eclipse files
 * needed for a Streambase project to be used in Eclipse Studio.
 *
 * @author Barnaby Golden
 * @goal studio
 */
public final class StudioMojo extends AbstractMojo {

    /**
     * Artifact ID for this project.
     * @parameter expression="${project.artifactId}" default-value="INVALID"
     */
    private transient String artifactId;

    /**
     * Execute.
     * @throws MojoExecutionException thrown
     * @throws MojoFailureException thrown
     */
    public void execute() throws MojoExecutionException, MojoFailureException {

        // current working directory
        final String workingDir = System.getProperty("user.dir");

        final Studio studio = new Studio();

        try {
            studio.streambaseStudio(workingDir, artifactId);
        } catch (PluginException ple) {
            throw new MojoExecutionException("Plugin: ", ple);
        }
    }
}
