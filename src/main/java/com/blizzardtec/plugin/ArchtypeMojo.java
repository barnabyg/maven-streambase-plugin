/**
 * 
 */
package com.blizzardtec.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * streambase:archtype maven plugin command.
 *
 * @author Barnaby Golden
 * @goal archtype
 * @requiresDirectInvocation true
 * @requiresProject false
 */
public final class ArchtypeMojo extends AbstractMojo {

    /**
     * Artifact ID for this new project.
     * @parameter expression="${artifactId}"
     */
    private transient String artifactId;

    /**
     * GroupId for this new project.
     * @parameter expression="${groupId}"
     */
    private transient String groupId;

    /**
     * Execute.
     * @throws MojoExecutionException thrown
     * @throws MojoFailureException thrown
     */
    public void execute() throws MojoExecutionException, MojoFailureException {

        // current working directory
        final String workingDir = System.getProperty("user.dir");

        final Archtype archtype = new Archtype();
        try {
            archtype.streambaseArchtype(workingDir, groupId, artifactId);
        } catch (PluginException ple) {
            throw new MojoExecutionException("Plugin problem: ", ple);
        }
    }
}
