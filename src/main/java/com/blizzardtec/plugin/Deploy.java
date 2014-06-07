/**
 *
 */
package com.blizzardtec.plugin;

import java.io.File;
import java.util.Collection;

import com.blizzardtec.helpers.FileHelper;
import com.blizzardtec.helpers.DirectoryHelper;
import com.blizzardtec.helpers.HelperException;

import org.apache.maven.plugin.logging.Log;

/**
 * Worker class for the Deploy command.
 *
 * @author Barnaby Golden
 *
 */
public final class Deploy {

    /**
     * Maven logger.
     */
    private final transient Log log;

    /**
     * Project artifactId.
     */
    private final transient String artifactId;

    /**
     * Constructor.
     *
     * @param log logger
     * @param artifactId project artifactId
     */
    public Deploy(final Log log, final String artifactId) {

        this.log = log;
        this.artifactId = artifactId;

    }

    /**
     * Main worker method. Does a Streambase deployment.
     *
     * @param streambasedir Streambase directory
     * @param workingDir Current working directory
     * @param copyjars Flag to indicate if jar should be copied over
     * @param directorys List of directories to copy
     * @param extensions List of file extensions to copy over
     * @param version version number
     * @throws PluginException thrown
     */
    public void streambaseDeploy(final String streambasedir,
                                 final String workingDir,
                                 final boolean copyjars,
                                 final String[] directorys,
                                 final String[] extensions,
                                 final String version)
            throws PluginException {

        verify(streambasedir, directorys, extensions, version);

        // the target directory consisting of the Streambase apps
        // base directory plus the project name
        final String targetDir =
                        streambasedir + File.separator + artifactId;

        // make sure the target directory exists
        final File targetDirectory = new File(targetDir);

        if (!targetDirectory.exists()) {

            final boolean createSuccess = targetDirectory.mkdir();

            if (!createSuccess) {
                throw new PluginException("Unable to make target directory");
            }
        }

        // for each extension, copy all the files to the target directory
        // and also copy over each named directory
        try {
            copyDirectoriesOver(directorys, workingDir, targetDir);
            copyFilesWithNamedExtensions(extensions, workingDir, targetDir);

        } catch (HelperException hec) {
            throw new PluginException(hec);
        }

        // copy over the Maven generated jar if required
        if (copyjars) {
            try {
                copyJar(version, workingDir, targetDir);
            } catch (HelperException hee) {
                throw new PluginException(hee);
            }
        }
    }

    /**
     * Verify parameters have been set correctly.
     * @param streambasedir Streambase dir
     * @param directorys list of directories
     * @param extensions list of extensions
     * @param version version
     * @throws PluginException thrown
     */
    private void verify(final String streambasedir,
                        final String[] directorys,
                        final String[] extensions,
                        final String version) throws PluginException {

        if ((streambasedir == null) || (!new File(streambasedir).exists())) {
            throw new PluginException(
                    "Streambase directory undefined or does not exist");
        }
        if (directorys == null) {
            throw new PluginException("Undefined directories list");
        }
        if (extensions == null) {
            throw new PluginException("Undefined extensions list");
        }
        if (version == null) {
            throw new PluginException("Undefined version");
        }
    }

    /**
     * Copy directories to the new location.
     * @param directorys Directories to copy
     * @param workingDir the current working directory
     * @param targetDir location to copy directories to
     * @throws HelperException thrown
     */
    private void copyDirectoriesOver(final String[] directorys,
                                     final String workingDir,
                                     final String targetDir)
                throws HelperException {
        for (int i = 0; i < directorys.length; i++) {
            log.info("Copying directory " + directorys[i]);
            final String cpDir = workingDir + File.separator + directorys[i];
            DirectoryHelper.copyDirectory(cpDir, targetDir);
        }
    }

    /**
     * Copy files with the specified extensions to the new location.
     * @param extensions extensions to copy
     * @param workingDir the current working directory
     * @param targetDir location to copy files to
     * @throws HelperException thrown
     */
    private void copyFilesWithNamedExtensions(final String[] extensions,
                                              final String workingDir,
                                              final String targetDir)
                throws HelperException {

        // get a list of the files in the current working directory
        final Collection<File> files =
            DirectoryHelper.recursiveDirContents(workingDir);

        for (int i = 0; i < extensions.length; i++) {
            log.info(
                    "Copying files with extension " + extensions[i]);
            FileHelper.copyFiles(
                    FileHelper.selectFilesByExtension(
                                    files, extensions[i]), targetDir);
        }
    }

    /**
     * Copy Maven generated jar file to deploy lib directory.
     * @param version project version
     * @param workingDir the current working directory
     * @param targetDir the location of the deployed application
     * @throws HelperException thrown
     */
    private void copyJar(final String version,
                         final String workingDir,
                         final String targetDir)
                throws HelperException {
        log.info("Copying over the Maven generated jar file");

        final String jarName = artifactId + "-" + version + ".jar";
        final String jarPath =
            workingDir + File.separator + "target" + File.separator + jarName;

        // check to see if the lib directory exists already, else create it
        final File libDir = new File(targetDir + File.separator + "lib");
        if (!libDir.exists()) {
            libDir.mkdir();
        }
        FileHelper.copyFile(jarPath, libDir.getPath());
    }
}
