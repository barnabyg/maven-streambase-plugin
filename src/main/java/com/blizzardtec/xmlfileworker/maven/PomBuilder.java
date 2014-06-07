/**
 *
 */
package com.blizzardtec.xmlfileworker.maven;

import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;
import com.blizzardtec.xmlfileworker.AbstractXmlFileBuilder;
import com.blizzardtec.xmlfileworker.maven.model.Build;
import com.blizzardtec.xmlfileworker.maven.model.Dependencies;
import com.blizzardtec.xmlfileworker.maven.model.Dependency;
import com.blizzardtec.xmlfileworker.maven.model.Plugin;

/**
 * Build a pom.xml file from scratch.
 *
 * @author Barnaby Golden
 *
 */
public final class PomBuilder extends AbstractXmlFileBuilder {

    /**
     * Pom version.
     */
    private static final String POMVERSION
                        = "http://maven.apache.org/POM/4.0.0";
    /**
     * Model version number.
     */
    private static final String MODEL_VERSION = "4.0.0";
    /**
     * Packaging type, e.g. jar, war, etc.
     */
    private static final String PACKAGING = "war";

    /**
     * Project groupId.
     */
    private transient String groupId;
    /**
     * Project artifactId.
     */
    private transient String artifactId;
    /**
     * Project version.
     */
    private transient String version;
    /**
     * Dependencies section.
     */
    private transient Dependencies dependencies;
    /**
     * Build section.
     */
    private final transient Build build;

    /**
     * Constructor - requires minimum fields to make a POM.
     */
    public PomBuilder() {
        super();

        build = new Build();
    }

    /**
     * Construct a pom.xml from the basic elements.
     * This method is used when we want to create a pom.xml
     * from scratch.
     *
     * @throws HelperException thrown
     */
    public void generate() throws HelperException {

        // initialise the Document member variable
        initialiseDoc();

        final Element project = generateProjectElement();

        final Element modelVersion = getDoc().createElement("modelVersion");
        modelVersion.setTextContent(MODEL_VERSION);
        project.appendChild(modelVersion);

        project.appendChild(modelVersion);

        final Element groupIdElement = getDoc().createElement("groupId");
        groupIdElement.setTextContent(groupId);

        project.appendChild(groupIdElement);

        final Element artifactIdElement = getDoc().createElement("artifactId");
        artifactIdElement.setTextContent(artifactId);

        project.appendChild(artifactIdElement);

        final Element versionElement = getDoc().createElement("version");
        versionElement.setTextContent(version);

        project.appendChild(versionElement);

        final Element packagingElement = getDoc().createElement("packaging");
        packagingElement.setTextContent(PACKAGING);

        project.appendChild(packagingElement);

        final Element propertiesElement = getDoc().createElement("properties");
        final Element projElement =
            getDoc().createElement("project.build.sourceEncoding");
        projElement.setTextContent("UTF-8");
        propertiesElement.appendChild(projElement);
        project.appendChild(propertiesElement);

        if (dependencies != null) {
            project.appendChild(dependencies.toXML(getDoc()));
        }

        project.appendChild(build.toXML(getDoc()));

        getDoc().appendChild(project);
    }

    /**
     * Generate the top level project XML element in the POM.
     * @return project element
     */
    private Element generateProjectElement() {
        final Element project = getDoc().createElement("project");

        XMLHelper.addAttribute(
          getDoc(), project, "xmlns", POMVERSION);
        XMLHelper.addAttribute(
                getDoc(), project, "xmlns:xsi",
                    "http://www.w3.org/2001/XMLSchema-instance");
        XMLHelper.addAttribute(
                getDoc(), project, "xsi:schemaLocation",
                POMVERSION
                    + " http://maven.apache.org/xsd/maven-4.0.0.xsd");

        return project;
    }

    /**
     * Add a Maven dependency.
     * @param dependency the dependency to add
     */
    public void addDependency(final Dependency dependency) {
        if (dependencies == null) {
            dependencies = new Dependencies();
        }
        dependencies.addDependency(dependency);
    }

    /**
     * Add a Maven plugin.
     * @param plugin the plugin to add
     */
    public void addPlugin(final Plugin plugin) {
        build.addPlugin(plugin);
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    /**
     * @param artifactId the artifactId to set
     */
    public void setArtifactId(final String artifactId) {
        this.artifactId = artifactId;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(final String version) {
        this.version = version;
    }
}
