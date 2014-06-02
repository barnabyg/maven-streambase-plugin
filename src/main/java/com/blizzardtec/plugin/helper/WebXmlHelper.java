/**
 * 
 */
package com.blizzardtec.plugin.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.blizzardtec.plugin.PluginException;

/**
 * Simple class to create a dummy web.xml file, needed for no other reason than
 * the packaging as a war file.
 *
 * @author Barnaby Golden
 *
 */
public final class WebXmlHelper {

    /**
     * Private constructor - denotes utility class.
     */
    private WebXmlHelper() {
        
    }

    /**
     * Generate a dummy web.xml, needed by the Maven war:war command
     * but not used by the application.
     *
     * @param targetDir target directory
     * @throws PluginException thrown
     */
    public static void generateWebXml(final String targetDir)
                                            throws PluginException {

        final File webXmlFile =
            new File(targetDir + File.separator + "web.xml");

        try {
            // Create file
            final FileWriter fstream =
                new FileWriter(webXmlFile);
            final BufferedWriter out = new BufferedWriter(fstream);
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<web-app xmlns=\"http://java.sun.com/xml/ns/j2ee\"");
            out.newLine();
            out.write(
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
            out.newLine();
            out.write("xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee"
                    + " http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd\"");
            out.newLine();
            out.write("version=\"2.4\">");
            out.newLine();
            out.write("</web-app>");

            // Close the output stream
            out.close();
        } catch (IOException ioe) {
            throw new PluginException(ioe);
        }
    }
}
