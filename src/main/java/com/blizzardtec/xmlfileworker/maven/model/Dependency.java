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
public final class Dependency implements XmlEntry {

    /**
     * Dependency groupId.
     */
    private final transient String groupId;
    /**
     * Dependency artifactId.
     */
    private final transient String artifactId;
    /**
     * Dependency version.
     */
    private final transient String version;
    /**
     * Dependency scope.
     */
    private final transient String scope;

    /**
     * Constructor - takes all fields needed for dependency.
     * @param groupId groupId
     * @param artifactId artifactId
     * @param version version - can be zero length string or null
     * @param scope scope - can be zero length string or null
     */
    public Dependency(final String groupId,
                           final String artifactId,
                           final String version,
                           final String scope) {

        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.scope = scope;
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

        final Element dependency = doc.createElement("dependency");

        final Element groupIdElement = doc.createElement("groupId");
        groupIdElement.setTextContent(groupId);
        dependency.appendChild(groupIdElement);

        final Element artifactIdElement = doc.createElement("artifactId");
        artifactIdElement.setTextContent(artifactId);
        dependency.appendChild(artifactIdElement);

        // version is not mandatory
        if ((version != null) && (version.length() > 1)) {
            final Element versionElement = doc.createElement("version");
            versionElement.setTextContent(version);
            dependency.appendChild(versionElement);
        }

        // scope is not mandatory
        if ((scope != null) && (scope.length() > 1)) {
            final Element scopeElement = doc.createElement("scope");
            scopeElement.setTextContent(scope);
            dependency.appendChild(scopeElement);
        }

        return dependency;
    }
}
