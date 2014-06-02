/**
 * 
 */
package com.blizzardtec.xmlfileworker.maven.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.XmlEntry;

/**
 * @author Barnaby Golden
 *
 */
public final class Plugin implements XmlEntry {

    /**
     * Plugin groupId.
     */
    private final transient String groupId;
    /**
     * Plugin artifactId.
     */
    private final transient String artifactId;
    /**
     * Plugin version.
     */
    private final transient String version;
    /**
     * Configuration entry (may be null).
     */
    private transient Configuration configuration = null;

    /**
     * Constructor, takes usual plugin fields.
     * @param groupId Plugin groupId
     * @param artifactId Plugin artifactId
     * @param version Plugin version
     */
    public Plugin(final String groupId,
                            final String artifactId,
                            final String version) {

        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.maven.MavenEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        // sanity check
        if ((groupId == null) || (artifactId == null)
                || (groupId.length() < 1) || (artifactId.length() < 1)) {
            throw new HelperException(
                    "groupId and artifactId incorrectly defined");
        }

        final Element plugin = doc.createElement("plugin");

        final Element groupIdElement = doc.createElement("groupId");
        groupIdElement.setTextContent(groupId);
        plugin.appendChild(groupIdElement);

        final Element artifactIdElement = doc.createElement("artifactId");
        artifactIdElement.setTextContent(artifactId);
        plugin.appendChild(artifactIdElement);

        // version is not mandatory
        if ((version != null) && (version.length() > 1)) {
            final Element versionElement = doc.createElement("version");
            versionElement.setTextContent(version);
            plugin.appendChild(versionElement);
        }

        if (configuration != null) {
            plugin.appendChild(configuration.toXML(doc));
        }

        return plugin;
    }

    /**
     * Add a plugin configuration entry.
     * @param configuration the config entry to add
     */
    public void addConfiguration(final Configuration configuration) {
        this.configuration = configuration;
    }
}
