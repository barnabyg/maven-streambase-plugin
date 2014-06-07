/**
 *
 */
package com.blizzardtec.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * streambase:install Maven plugin command.
 *
 * @author Barnaby Golden
 * @goal install
 */
public final class InstallMojo extends AbstractMojo {

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

    /* (non-Javadoc)
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        // current working directory
        final String basedir = System.getProperty("user.dir");

        final Install install = new Install();

        try {
            install.streambaseInstall(basedir, directorys, extensions);
        } catch (PluginException ple) {
            throw new MojoExecutionException("Plugin error: ", ple);
        }
    }
}
