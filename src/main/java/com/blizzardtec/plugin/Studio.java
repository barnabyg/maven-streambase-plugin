/**
 *
 */
package com.blizzardtec.plugin;

import java.io.File;

import com.blizzardtec.helpers.FileHelper;
import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.plugin.helper.SettingsHelper;
import com.blizzardtec.xmlfileworker.eclipse.ClasspathBuilder;
import com.blizzardtec.xmlfileworker.eclipse.ProjectBuilder;

/**
 * Worker class for the streambase:studio plugin command.
 *
 * Creates the necessary files to load and work on a Streambase
 * application in Streambase Studio.
 * This includes .project, .classpath and the files in the
 * .settings directory.
 * It also copies the sbd.local file to sbd.sbconf.
 *
 * @author Barnaby Golden
 *
 */
//@SuppressWarnings("PMD")
public final class Studio {

    /**
     * Main worker method, performs actions needed
     * to create a new Streambase project.
     *
     * @param workingDir working directory
     * @param artifactId artifact id
     * @throws PluginException thrown
     */
    public void streambaseStudio(
                      final String workingDir,
                      final String artifactId)
                throws PluginException {

        // create the .project and .classpath files
        buildEclipseProjectFile(workingDir, artifactId);
        buildEclipseClasspathFile(workingDir);
        SettingsHelper.generateSettings(workingDir);

        // copy the sbd.local file to sbd.conf
        copyLocalFile(workingDir);
    }

    /**
     * Copy the template sbd.local file to sbd.sbconf.
     *
     * @param workingDir current working directory
     * @throws PluginException thrown
     */
    private void copyLocalFile(final String workingDir)
                                        throws PluginException {

        // copy the sbd.local file to sbd.conf
        final String localFilePath =
            workingDir + File.separator + Archtype.SBD_LOCAL;
        final String sbdFilePath =
            workingDir + File.separator + "sbd.sbconf";

        try {
            FileHelper.copyAndRenameFile(localFilePath, sbdFilePath);
        } catch (HelperException hee) {
            throw new PluginException(hee);
        }
    }

    /**
     * Create the Eclipse .project file.
     * @param workingDir working directory
     * @param projectName project name
     * @throws PluginException thrown
     */
    private void buildEclipseProjectFile(
            final String workingDir, final String projectName)
                                    throws PluginException {

        final ProjectBuilder builder = new ProjectBuilder();
        builder.setProjectName(projectName);

        final File projFile =
            new File(workingDir + File.separator + ".project");

        // only generate a .project file if it does not already exist
        if (!projFile.exists()) {
            try {
                builder.generate();
                builder.save(projFile);
            } catch (HelperException hee) {
                throw new PluginException(hee);
            }
        }
    }

    /**
     * Create the Eclipse .classpath file.
     * @param workingDir working directory
     * @throws PluginException thrown
     */
    private void buildEclipseClasspathFile(final String workingDir)
        throws PluginException {

        final ClasspathBuilder builder = new ClasspathBuilder();

        final File classpathFile =
            new File(workingDir + File.separator + ".classpath");

        // only generate a .classpath file if it does not already exist
        if (!classpathFile.exists()) {
            try {
                builder.generate();
                builder.save(classpathFile);
            } catch (HelperException hee) {
                throw new PluginException(hee);
            }
        }
    }
}
