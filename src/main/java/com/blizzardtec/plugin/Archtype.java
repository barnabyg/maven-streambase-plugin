/**
 * 
 */
package com.blizzardtec.plugin;

import java.io.File;

import com.blizzardtec.helpers.DirectoryHelper;
import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.plugin.helper.WebXmlHelper;
import com.blizzardtec.xmlfileworker.maven.PomBuilder;
import com.blizzardtec.xmlfileworker.streambase.AppBuilder;
import com.blizzardtec.xmlfileworker.streambase.ConfBuilder;
import com.blizzardtec.xmlfileworker.XmlFileBuilder;

/**
 * Worker class for the streambase:archtype maven plugin command.
 * Creates a shell Streambase application, just enough to compile and run.
 *
 * @author Barnaby Golden
 *
 */
public final class Archtype {

    /**
     * Java.
     */
    private static final String JAVA = "java";
    /**
     * Resources.
     */
    private static final String RESOURCES = "resources";
    /**
     * web-inf.
     */
    private static final String WEBINF = "WEB-INF";
    /**
     * Src.
     */
    private static final String SRC = "src";
    /**
     * Main.
     */
    private static final String MAIN = "main";
    /**
     * Test.
     */
    private static final String TEST = "test";
    /**
     * Webapp.
     */
    private static final String WEBAPP = "webapp";
    /**
     * Local sbd.sbconf filename.
     */
    public static final String SBD_LOCAL = "sbd.local";
    /**
     * Default version to apply to new projects.
     */
    private static final String DEFAULT_VERSION = "0.0.1-SNAPSHOT";
    /**
     * Src dir.
     */
    private static final String SRCSTR = SRC + File.separator;
    /**
     * Main dir.
     */
    private static final String MAINSTR = MAIN + File.separator;
    /**
     * Test dir.
     */
    private static final String TESTSTR = TEST + File.separator;

    /**
     * Directory structure of new project.
     * All these directories will be created.
     */
    private static final String[] DIRECTORIES =
              {SRC,
               "target",
               SRCSTR + MAIN,
               SRCSTR + TEST,
               SRCSTR + MAINSTR + JAVA,
               SRCSTR + MAINSTR + RESOURCES,
               SRCSTR + MAINSTR + WEBAPP,
               SRCSTR + MAINSTR + WEBAPP + File.separator + WEBINF,
               SRCSTR + TESTSTR + JAVA,
               SRCSTR + TESTSTR + RESOURCES};

    /**
     * Create a new, template Streambase application.
     * This includes the sbd.sbconf file and a minimal .sbapp file.
     *
     * @param workingDir working directory
     * @param groupId groupId
     * @param artifactId artifactId
     * @throws PluginException thrown
     */
    public void streambaseArchtype(final String workingDir,
            final String groupId,
            final String artifactId) throws PluginException {

        verify(workingDir, artifactId, groupId);
        createDirectoryStructure(workingDir);
        createSbdConf(workingDir);
        createSbapp(workingDir, artifactId);
        createPOM(workingDir, groupId, artifactId);
        createWebXml(workingDir);
    }

    /**
     * Verify state is valid to run.
     * @param workingDir working directory
     * @param groupId groupId
     * @param artifactId artifactId
     * @throws PluginException thrown
     */
    private void verify(final String workingDir,
                        final String artifactId,
                        final String groupId)
                            throws PluginException {

        final File wDir = new File(workingDir);
        if (!wDir.exists()) {
            throw new PluginException("Invalid working directory");
        }

        if (artifactId == null) {
            throw new PluginException(
                    "ERROR: artifactId was null - "
                    + "please ensure the id is passed as a parameter");
        }

        if (groupId == null) {
            throw new PluginException(
                    "ERROR: groupId was null - "
                    + "please ensure the group is passed as a parameter");
        }
    }

    /**
     * Create the standard directory layout under the given working directory.
     * @param workingDir working directory
     */
    private void createDirectoryStructure(final String workingDir) {
        for (int i = 0; i < DIRECTORIES.length; i++) {
            DirectoryHelper.createDirectory(workingDir, DIRECTORIES[i]);
        }
    }

    /**
     * Create the sbd.local file.
     * This file will be used as a template to auto-generate
     * the sbd.sbconf file.
     *
     * @param workingDir working directory
     * @throws PluginException thrown
     */
    private void createSbdConf(final String workingDir)
        throws PluginException {

        final XmlFileBuilder builder = new ConfBuilder();

        final File sbdFile =
            new File(workingDir + File.separator + SBD_LOCAL);

        try {
            builder.generate();
            builder.save(sbdFile);
        } catch (HelperException hee) {
            throw new PluginException(hee);
        }
    }

    /**
     * Create the .sbapp file.
     * @param workingDir working directory
     * @param artifactId artifactId
     * @throws PluginException thrown
     */
    private void createSbapp(final String workingDir,
                             final String artifactId)
        throws PluginException {

        final XmlFileBuilder builder = new AppBuilder();

        final File sbappFile =
            new File(workingDir + File.separator + artifactId + ".sbapp");

        try {
            builder.generate();
            builder.save(sbappFile);
        } catch (HelperException hee) {
            throw new PluginException(hee);
        }
    }

    /**
     * Create a pom.xml for a basic Streambase application.
     *
     * @param workingDir working directory
     * @param groupId project groupId
     * @param artifactId project artifactId
     * @throws PluginException thrown
     */
    private void createPOM(final String workingDir,
                           final String groupId,
                           final String artifactId)
            throws PluginException {

        final String version = DEFAULT_VERSION;

        final PomBuilder mavenPom =
            new PomBuilder();

        mavenPom.setGroupId(groupId);
        mavenPom.setArtifactId(artifactId);
        mavenPom.setVersion(version);

        try {
            mavenPom.generate();
            final File pomFile =
                new File(workingDir + File.separator + "pom.xml");
            mavenPom.save(pomFile);            
        } catch (HelperException hee) {
            throw new PluginException(hee);
        }
    }

    /**
     * Create a dummy web.xml file, needed by Maven war:war command.
     *
     * @param workingDir current working directory
     * @throws PluginException thrown
     */
    private void createWebXml(final String workingDir)
            throws PluginException {

        final String webInfDir =
            workingDir + File.separator + SRCSTR + MAINSTR
                    + WEBAPP + File.separator + WEBINF;

        WebXmlHelper.generateWebXml(webInfDir);
    }
}
