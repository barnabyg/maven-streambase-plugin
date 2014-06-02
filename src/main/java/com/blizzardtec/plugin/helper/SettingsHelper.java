/**
 * 
 */
package com.blizzardtec.plugin.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.blizzardtec.helpers.DirectoryHelper;
import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.TextPropertiesHelper;
import com.blizzardtec.plugin.PluginException;

/**
 * Generate a .settings directory and populate it
 * with standard Eclipse, Streambase and Maven settings files.
 * Also offers a method for adding new Streambase module
 * search path entries.
 *
 * @author Barnaby Golden
 * 
 */
public final class SettingsHelper {

    /**
     * Prefs.
     */
    private static final String PREFS = "eclipse.preferences.version=1";
    /**
     * .settings.
     */
    private static final String SETTINGS = ".settings";
    /**
     * Mod search.
     */
    private static final String MODSEARCH = "moduleSearchPathFolders";
    /**
     * Streambase preferences file.
     */
    public static final String STREAMBASE_PREFS =
                                        "com.streambase.sb.ide.core.prefs";
    /**
     * Maven preferences file.
     */
    public static final String MAVEN_PREFS = "org.maven.ide.eclipse.prefs";
    /**
     * Eclipse preferences file.
     */
    public static final String ECLIPSE_PREFS = "org.eclipse.jdt.core.prefs";
    /**
     * Module search path.
     */
    private static final String MOD_SEARCH_PATH =
                                        "modules|target\\external-modules";
    /**
     * Java version.
     */
    private static final String JAVA_VERSION = "1.6";

    /**
     * Private constructor denotes utility class.
     */
    private SettingsHelper() {

    }

    /**
     * Generate the .settings directory if it does not already exist.
     * The files inside the .settings directory are also generated,
     * but only if they do not already exist.
     *
     * @param workingDir working directory
     * @throws PluginException thrown
     */
    public static void generateSettings(final String workingDir)
                                            throws PluginException {

        final File settingsDir =
            new File(workingDir + File.separator + SETTINGS); 

        // only generate the settings directory if it does not already exist
        if (!settingsDir.exists()) {
            DirectoryHelper.createDirectory(workingDir, SETTINGS);
        }

        final File eclipsePrefsFile =
            new File(settingsDir.getPath() + File.separator + ECLIPSE_PREFS);

        if (!eclipsePrefsFile.exists()) {
            createEclipsePrefs(eclipsePrefsFile);            
        }

        final File mavenPrefsFile =
            new File(settingsDir.getPath() + File.separator + MAVEN_PREFS);

        if (!mavenPrefsFile.exists()) {
            createMavenPrefs(mavenPrefsFile);            
        }

        final File sbPrefsFile =
            new File(settingsDir.getPath() + File.separator + STREAMBASE_PREFS);

        if (!sbPrefsFile.exists()) {
            createStreambasePrefs(sbPrefsFile);
        }
    }

    /**
     * Create the Eclipse Preferences file.
     * @param eclipsePrefsFile prefs file
     * @throws PluginException thrown
     */
    private static void createEclipsePrefs(final File eclipsePrefsFile)
            throws PluginException {

        try {
            // Create file
            final FileWriter fstream =
                new FileWriter(eclipsePrefsFile);
            final BufferedWriter out = new BufferedWriter(fstream);
            out.write(getDateTimeStamp());
            out.newLine();
            out.write(
                    "org.eclipse.jdt.core.compiler.problem.forbiddenReference"
                  + "=warning");
            out.newLine();
            out.write(
                    "org.eclipse.jdt.core.compiler.codegen.targetPlatform"
                  + "=" + JAVA_VERSION);
            out.newLine();
            out.write(PREFS);
            out.newLine();
            out.write(
               "org.eclipse.jdt.core.compiler.source=" + JAVA_VERSION);
            out.newLine();
            out.write(
               "org.eclipse.jdt.core.compiler.compliance=" + JAVA_VERSION);

            // Close the output stream
            out.close();
        } catch (IOException ioe) {
            throw new PluginException(ioe);
        }
    }

    /**
     * Create the Maven Preferences file.
     * @param mavenPrefsFile prefs file
     * @throws PluginException thrown
     */
    private static void createMavenPrefs(final File mavenPrefsFile)
            throws PluginException {

        try {
            // Create file
            final FileWriter fstream =
                new FileWriter(mavenPrefsFile);
            final BufferedWriter out = new BufferedWriter(fstream);
            out.write(getDateTimeStamp());
            out.newLine();
            out.write("activeProfiles=");
            out.newLine();
            out.write(PREFS);
            out.newLine();
            out.write("fullBuildGoals=process-test-resources");
            out.newLine();
            out.write("includeModules=false");
            out.newLine();
            out.write("resolveWorkspaceProjects=true");
            out.newLine();
            out.write("resourceFilterGoals="
                    + "process-resources resources\\:testResources");
            out.newLine();
            out.write("skipCompilerPlugin=true");
            out.newLine();
            out.write("version=1");
            
            // Close the output stream
            out.close();
        } catch (IOException ioe) {
            throw new PluginException(ioe);
        }
    }

    /**
     * Create the Streambase Preferences file.
     * @param sbPrefsFile prefs file
     * @throws PluginException thrown
     */
    private static void createStreambasePrefs(final File sbPrefsFile)
            throws PluginException {

        try {
            // Create file
            final FileWriter fstream =
                new FileWriter(sbPrefsFile);
            final BufferedWriter out = new BufferedWriter(fstream);
            out.write(getDateTimeStamp());
            out.newLine();
            out.write(PREFS);
            out.newLine();
            out.write(
                "moduleSearchPathFolders=" + MOD_SEARCH_PATH);
            out.newLine();
            
            // Close the output stream
            out.close();
        } catch (IOException ioe) {
            throw new PluginException(ioe);
        }
    }

    /**
     * Modify the Streambase prefs file to add a module search
     * entry.
     *
     * @param sbPrefsFile Streambase prefs file
     * @param modSearchEntry module search path entry to add
     * @throws PluginException thrown
     */
    public static void addModuleSearchPath(
            final File sbPrefsFile, final String modSearchEntry)
                                              throws PluginException {

        if (!sbPrefsFile.exists()) {
            throw new PluginException(
                    "prefs file does not exist: " + sbPrefsFile.getPath());
        }

        final TextPropertiesHelper helper =
            new TextPropertiesHelper(sbPrefsFile);

        try {
            helper.load();

            // get the current module path
            final String oldValue =
                helper.getProperty(MODSEARCH);

            final String newValue = addToModulePath(oldValue, modSearchEntry);

            helper.setProperty(MODSEARCH, newValue);
        } catch (HelperException hee) {
            throw new PluginException(hee);
        }
    }

    /**
     * Take a module path and add an entry to it (if the entry
     * is not already present).
     *
     * @param oldPath old module search path
     * @param newEntry new entry to module search path
     * @return new module search path
     */
    private static String addToModulePath(
            final String oldPath, final String newEntry) {

        final String[] elements = oldPath.split("\\|");

        final StringBuffer str = new StringBuffer();

        // check to see if the new entry already exists in the path
        boolean found = false;
        for (int i = 0; i < elements.length; i++) {
            if (newEntry.equals(elements[i])) {
                found = true;
            }
        }

        str.append(oldPath);

        if (!found) {
            str.append('|');
            str.append(newEntry);
        }

        return str.toString();
    }

    /**
     * Get the current date-time.
     *
     * @return current date time as a string
     */
    private static String getDateTimeStamp() {

        final Date now = new Date();

        return "#" + now.toString();
    }
}
