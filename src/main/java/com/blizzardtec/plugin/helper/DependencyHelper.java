/**
 *
 */
package com.blizzardtec.plugin.helper;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.model.Dependency;

import com.blizzardtec.helpers.DirectoryHelper;
import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.StringHelper;
import com.blizzardtec.helpers.ZipHelper;
import com.blizzardtec.plugin.PluginException;
import com.blizzardtec.xmlfileworker.XmlFileModifier;
import com.blizzardtec.xmlfileworker.XmlEntry.EntryType;
import com.blizzardtec.xmlfileworker.streambase.ConfList;
import com.blizzardtec.xmlfileworker.streambase.ConfModifier;
import com.blizzardtec.xmlfileworker.streambase.ConfReader;

/**
 * Utility class for dealing with Maven dependencies.
 *
 * @author Barnaby Golden
 *
 */
public final class DependencyHelper {

    /**
     * Target.
     */
    private static final String TARGET = "target";
    /**
     * sbd.sbconf name.
     */
    private static final String SBDCONF = "sbd.sbconf";
    /**
     * Sleep time.
     */
    private static final int SLEEP_TIME = 500;
    /**
     * Temporary working directory name.
     */
    private static final String TMP_DIR = "tmp";
    /**
     * Name of the external-modules directory.
     */
    public static final String EXTERNAL_MODULES = "external-modules";

    /**
     * Private constructor denotes utility class.
     */
    private DependencyHelper() {

    }

    /**
     * Get the dependency projects from the repository and place
     * them in the local external-modules directory.
     *
     * @param workingDir working directory
     * @param dependencies list of dependencies
     * @param manager artifact manager
     * @return returns true if at least one dependency exists
     * @throws PluginException thrown
     */
    public static boolean dependentProjects(
            final String workingDir,
            final List<Dependency> dependencies,
            final ArtifactManager manager)
                        throws PluginException {

        boolean hasSbDependecy = false;

        final Iterator<Dependency> iterator = dependencies.iterator();

        // loop through the dependencies, download them to the
        // local external modules directory
        while (iterator.hasNext()) {
            final Dependency dependency = iterator.next();

            final boolean result = DependencyHelper.dependentProject(
                                        workingDir, dependency, manager);

            if (result) {
                hasSbDependecy = true;
            }
        }

        return hasSbDependecy;
    }

    /**
     * Get a copy of the Streambase files from a dependency and
     * copy them in to the local external-modules directory.
     *
     * @param workingDir working directory
     * @param dependency dependency
     * @param manager artifact manager
     * @return true if the dependency exists and is a Streambase dependency
     * @throws PluginException thrown
     */
    private static boolean dependentProject(final String workingDir,
            final Dependency dependency,
            final ArtifactManager manager) throws PluginException {

        boolean isSbDependency = false;

        resolveDependency(dependency, manager);

        final File warFile =
            DependencyHelper.getWarFile(dependency, manager.getRepo());

        if (warFile.exists()) {

            final File modDir =
                getExtModDir(workingDir);

            final File destDir =
                getDestinationDir(modDir, dependency);

            final File tmpDir = getTmpDir(workingDir);

            isSbDependency =
                copyStreambaseDir(tmpDir, warFile, destDir);

            // add the dependency aliases to sbd.sbconf
            modifySbConf(workingDir, destDir.getPath());
        }

        return isSbDependency;
    }

    /**
     * Get the temporary directory, used for expanding the war file.
     *
     * @param workingDir current working directory
     * @return temp directory
     * @throws PluginException thrown
     */
    private static File getTmpDir(final String workingDir)
            throws PluginException {

        final File tmpDir =
            new File(workingDir + File.separator + TMP_DIR);

        if (tmpDir.exists()) {
            try {
                DirectoryHelper.deleteDir(tmpDir);
            } catch (HelperException hee) {
                throw new PluginException(hee);
            }
        }

        tmpDir.mkdir();

        return tmpDir;
    }

    /**
     * Resolve a dependency.
     * I believe this ensures that a copy of the dependency
     * exists in the local repository.
     *
     * @param dependency dependency
     * @param manager artifact manager
     * @throws PluginException thrown
     */
    private static void resolveDependency(
                    final Dependency dependency,
                    final ArtifactManager manager)
                        throws PluginException {

        if (manager.getFactory() != null) {
            // create artifact
            final Artifact artifact =
                manager.getFactory().createArtifact(
                     dependency.getGroupId(),
                     dependency.getArtifactId(),
                     dependency.getVersion(),
                     dependency.getScope(),
                     dependency.getType());

            try {
                manager.getArtifactResolver().resolve(
                    artifact, manager.getRemoteRepos(), manager.getRepo());
            } catch (ArtifactNotFoundException afe) {
                throw new PluginException(afe);
            } catch (ArtifactResolutionException are) {
                throw new PluginException(are);
            }
        }
    }

    /**
     * Find the path to a dependency in the local repository.
     *
     * @param dependency dependency
     * @param repo local repository
     * @return path
     */
    private static String getDependencyPath(final Dependency dependency,
                                           final ArtifactRepository repo) {

        String path = null;

        if (repo != null) {
            path = repo.getBasedir() + File.separator
            + StringHelper.convertPackageToPath(dependency.getGroupId())
                 + File.separator
                    + dependency.getArtifactId()
                      + File.separator
                        + dependency.getVersion();
        }

        return path;
    }

    /**
     * Get the expanded streambase directory and
     * copy it to the external modules directory.
     *
     * @param tmpDir temporary directory
     * @param warFile the war file
     * @param destDir destination directory
     * @return true if this is a Streambase dependency
     * @throws PluginException thrown
     */
    private static boolean copyStreambaseDir(
                      final File tmpDir,
                      final File warFile,
                      final File destDir)
                            throws PluginException {

        boolean isSbDependency = false;

        try {
            ZipHelper.expandWarFile(warFile, tmpDir.getPath());
        } catch (HelperException hee) {
            throw new PluginException(hee);
        }

        final File classesDir =
            new File(tmpDir.getPath() + File.separator
                    + "WEB-INF" + File.separator
                    + "classes");

        // the expanded streambase directory
        final File sbDir =
            new File(classesDir.getPath() + File.separator + "streambase");

        if (sbDir.exists()) {
            try {
                DirectoryHelper.copyDirectoryContents(sbDir, destDir);

                // remove the Streambase directory now that we have it's
                // contents
                DirectoryHelper.deleteDir(sbDir);

                // we also copy over the classes directory
                DirectoryHelper.copyDirectory(
                        classesDir.getPath(), destDir.getPath());

                // cleanup by deleting the tmp working directory
                DirectoryHelper.deleteDir(tmpDir);

                isSbDependency = true;

            } catch (HelperException hee) {
                throw new PluginException(hee);
            }
        }

        return isSbDependency;
    }

    /**
     * Get the Destination directory. This will be the target
     * for the expanded war file contents.
     *
     * @param modDir external-modules directory
     * @param dependency the dependency
     * @return file handle for the target directory
     * @throws PluginException thrown
     */
    private static File getDestinationDir(
            final File modDir, final Dependency dependency)
                        throws PluginException {

        final File destDir =
            new File(modDir.getPath()
                    + File.separator + dependency.getArtifactId());

        // if the project directory already exists we delete it
        // and then re-create it
        if (destDir.exists()) {
            try {
                DirectoryHelper.deleteDir(destDir);
            } catch (HelperException hee) {
                throw new PluginException(hee);
            }

            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException iee) {
                throw new PluginException(iee);
            }
        }

        destDir.mkdir();

        return destDir;
    }

    /**
     * Get the relative path to the external-modules directory.
     *
     * @return returns the path to the external modules directory
     * relative to the base directory of the project
     * @throws PluginException thrown
     */
    public static String getExtModRelativePath()
                    throws PluginException {

        return TARGET + "\\" + EXTERNAL_MODULES;
    }

    /**
     * Get the external-modules directory handle.
     *
     * @param workingDir current working directory
     * @return handle to the external-modules directory
     * @throws PluginException thrown
     */
    private static File getExtModDir(final String workingDir)
                    throws PluginException {

        final File targetDir = new File(workingDir + File.separator + TARGET);

        // create the target directory if it does not exist
        // (usually happens after an mvn clean command)
        if (!targetDir.exists()) {
            targetDir.mkdir();
        }

        final File modDir =
           new File(targetDir.getPath() + File.separator + EXTERNAL_MODULES);

        // create the external-modules directory if necessary
        // sleep a while to make sure the OS has time to create
        if (!modDir.exists()) {
            modDir.mkdir();

            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException iee) {
                throw new PluginException(iee);
            }
        }

        return modDir;
    }

    /**
     * Get the war file for the dependency.
     *
     * @param dependency the dependency
     * @param repo the local repository
     * @return the war file handle
     */
    private static File getWarFile(
            final Dependency dependency, final ArtifactRepository repo) {

        final String path =
            DependencyHelper.getDependencyPath(
                    dependency, repo);

        final File warFile = new File(path + File.separator
          + dependency.getArtifactId() + "-"
          + dependency.getVersion() + ".war");

        return warFile;
    }

    /**
     * Update sbd.sbconf with aliases from a dependency.
     *
     * @param workingDir working directory
     * @param destDir dependency directory
     * @throws PluginException thrown
     */
    private static void modifySbConf(
                final String workingDir, final String destDir)
                            throws PluginException {

        // resolve dependency and retrieve the entries that need to be added
        final ConfList list =
                    getListsForDependency(destDir);

        final File sbConfFile =
            new File(workingDir + File.separator + SBDCONF);

        // sanity check
        if (!sbConfFile.exists()) {
            throw new PluginException(
                    "There is no sbd.sbconf file in the current directory");
        }

        final XmlFileModifier modifier = new ConfModifier();

        try {
            modifier.load(sbConfFile);
            modifier.update(list.getCustomFunctions(), EntryType.CUSTOM);
            modifier.update(list.getModuleSearchPaths(), EntryType.MODULE);
            modifier.update(list.getOpParameters(), EntryType.OPPARAM);
            modifier.update(list.getOpResSearchPaths(), EntryType.OPRES);
            modifier.update(list.getSbPlugins(), EntryType.PLUGIN);
            modifier.save();
        } catch (HelperException hee) {
            throw new PluginException(hee);
        }
    }

    /**
     * For a given dependency location, find the sbd.sbconf file and extract
     * the aliases that need to be copied in to the local sbd.sbconf file.
     *
     * @param path the path to the dependency
     * @throws PluginException thrown
     * @return lists of values from the pom file if found
     */
    private static ConfList getListsForDependency(final String path)
                                                 throws PluginException {

        ConfList confList = null;

        final File depConfFile =
            new File(path + File.separator + SBDCONF);

        final ConfReader reader = new ConfReader();
        try {
            reader.load(depConfFile);
            confList = reader.getConfigurationLists();
        } catch (HelperException hee) {
            throw new PluginException(hee);
        }

        return confList;
    }
}
