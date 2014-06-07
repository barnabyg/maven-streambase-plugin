/**
 *
 */
package com.blizzardtec.xmlfileworker.maven.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.XmlEntry;

/**
 * Builds a template maven-streambase-plugin section of the
 * pom.xml file.
 *
 * @author Barnaby Golden
 *
 */
public final class MavenStreambasePlugin implements XmlEntry {

    /**
     * Extension.
     */
    private static final String EXTENSION = "extension";
    /**
     * groupId for maven-streambase-plugin.
     */
    private static final String GROUP_ID = "com.blizzardtec.plugins";
    /**
     * artifactId for maven-streambase-plugin.
     */
    private static final String ARTIFACT_ID = "maven-streambase-plugin";
    /**
     * version for maven-streambase-plugin.
     */
    private static final String VERSION = "0.0.1-SNAPSHOT";
    /**
     * Phase the install will take place in.
     */
    private static final String PHASE = "validate";

    /* (non-Javadoc)
     * @see com.blizzardtec.xmlfilebuilders.maven.model.MavenEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element mavenPluginEl = doc.createElement("plugin");

        final Element groupId = doc.createElement("groupId");
        groupId.setTextContent(GROUP_ID);
        final Element artifactId = doc.createElement("artifactId");
        artifactId.setTextContent(ARTIFACT_ID);
        final Element version = doc.createElement("version");
        version.setTextContent(VERSION);

        mavenPluginEl.appendChild(groupId);
        mavenPluginEl.appendChild(artifactId);
        mavenPluginEl.appendChild(version);

        final Element configuration = doc.createElement("configuration");

        final Element directorys = doc.createElement("directorys");

        // add the .sbapp and .sbconf extensions as default extension filter
        // values to be included in the installation copy
        // we also copy over any xml files, including the pom.xml
        final Element extensions = doc.createElement("extensions");
        Element extension = doc.createElement(EXTENSION);
        extension.setTextContent("sbapp");
        extensions.appendChild(extension);
        extension = doc.createElement(EXTENSION);
        extension.setTextContent("sbconf");
        extensions.appendChild(extension);
        extension = doc.createElement(EXTENSION);
        extension.setTextContent("xml");
        extensions.appendChild(extension);

        configuration.appendChild(directorys);
        configuration.appendChild(extensions);

        mavenPluginEl.appendChild(configuration);

        final Element executions = doc.createElement("executions");

        final Element execution = doc.createElement("execution");

        final Element phase = doc.createElement("phase");
        phase.setTextContent(PHASE);

        final Element goals = doc.createElement("goals");
        final Element goal = doc.createElement("goal");
        goal.setTextContent("install");
        goals.appendChild(goal);

        execution.appendChild(phase);
        execution.appendChild(goals);

        executions.appendChild(execution);

        mavenPluginEl.appendChild(executions);

        return mavenPluginEl;
    }
}
