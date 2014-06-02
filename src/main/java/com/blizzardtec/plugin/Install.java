/**
 * 
 */
package com.blizzardtec.plugin;

import java.io.File;
import java.util.Collection;

import com.blizzardtec.helpers.FileHelper;
import com.blizzardtec.helpers.DirectoryHelper;
import com.blizzardtec.helpers.HelperException;

/**
 * Worker class for the streambase:install command.
 *
 * @author Barnaby Golden
 *
 */
public final class Install {

    /**
     * Time to sleep between deleting a directory and recreating it.
     * This allows the OS enough time to clean up.
     */
    private static final int SLEEP_TIME = 500;

    /**
     * Name of the directory to be used for installation
     * under the Maven installation directory.
     */
    public static final String SB_DIR = "streambase";

    /**
     * Install a Streambase application to the local Maven repository.
     *
     * @param workingDir source directory for Streambase application
     * @param directorys list of sub-directories to copy over
     * @param extensions list of file extension filters to include in copy
     * @throws PluginException thrown
     */
    public void streambaseInstall(
                        final String workingDir,
                        final String[] directorys,
                        final String[] extensions)
            throws PluginException {

        final File targetDir = getTargetDirectory(workingDir);

        // for each extension, copy all the files to the target directory
        // and also copy over each named directory
        try {
            if (directorys != null) {
                copyDirectoriesOver(
                        directorys, workingDir, targetDir.getPath());
            }

            if (extensions != null) {
                copyFilesWithNamedExtensions(
                        extensions, workingDir, targetDir.getPath());
            }
        } catch (HelperException hec) {
            throw new PluginException(hec);
        }

        // sleep a short time to make sure the OS
        // has enough time to populate the streambase directory
        // before the war packaging begins
        try {
            Thread.sleep(SLEEP_TIME);    
        } catch (InterruptedException ine) {
            throw new PluginException(ine);
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
            DirectoryHelper.dirContents(workingDir);

        for (int i = 0; i < extensions.length; i++) {
            FileHelper.copyFiles(
                    FileHelper.selectFilesByExtension(
                                    files, extensions[i]), targetDir);
        }                    
    }

    /**
     * Get the cleaned target directory.
     * If the directory already existed, it will be deleted
     * and replaced with a blank directory to prevent any version
     * inconsistencies.
     *
     * @param workingDir working directory for this project
     * @return target directory for installation
     * @throws PluginException thrown
     */
    private File getTargetDirectory(final String workingDir)
                    throws PluginException {

        final File dir = new File(workingDir + File.separator + "src"
                                 + File.separator + "main" + File.separator
                                 + "resources" + File.separator + SB_DIR);

        // if the directory already exists, delete it
        if (dir.exists()) {
            try {
                DirectoryHelper.deleteDir(dir);                
            } catch (HelperException hee) {
                throw new PluginException(hee);
            }

            try {
                Thread.sleep(SLEEP_TIME);    
            } catch (InterruptedException ine) {
                throw new PluginException(ine);
            }
        }

        final boolean flag = dir.mkdir();

        if (!flag) {
            throw new PluginException(
                    "Unable to create dir: " + dir.getPath());
        }

        return dir;
    }
}
