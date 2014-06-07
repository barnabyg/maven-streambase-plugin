/**
 *
 */
package com.blizzardtec.xmlfileworker.eclipse;

import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.AbstractXmlFileBuilder;

/**
 * Builds an Eclipse .project file.
 *
 * @author Barnaby Golden
 *
 */
public final class ProjectBuilder extends AbstractXmlFileBuilder {

    /**
     * Name.
     */
    private static final String NAME = "name";
    /**
     * Nature.
     */
    private static final String NATURE = "nature";
    /**
     * Project name.
     */
    private transient String projectName = "NEWPROJECT";

    /**
     * Build the .project Eclipse XML file.
     * @throws HelperException thrown
     */
    public void generate() throws HelperException {

        initialiseDoc();

        final Element projectDesc
                = getDoc().createElement("projectDescription");

        final Element name = getDoc().createElement(NAME);
        name.setTextContent(projectName);

        final Element comment = getDoc().createElement("comment");
        final Element projects = getDoc().createElement("projects");

        final Element natures = getDoc().createElement("natures");
        final Element nature1 = getDoc().createElement(NATURE);
        nature1.setTextContent("com.streambase.sb.ide.core.StreamBaseNature");
        final Element nature2 = getDoc().createElement(NATURE);
        nature2.setTextContent("org.eclipse.jdt.core.javanature");
        natures.appendChild(nature1);
        natures.appendChild(nature2);

        projectDesc.appendChild(name);
        projectDesc.appendChild(comment);
        projectDesc.appendChild(projects);
        projectDesc.appendChild(buildBuildSpecElement());
        projectDesc.appendChild(natures);

        getDoc().appendChild(projectDesc);
    }

    /**
     * Create the buildSpec element.
     * @return new buildSpec element
     */
    private Element buildBuildSpecElement() {

        final Element buildSpec = getDoc().createElement("buildSpec");

        buildSpec.appendChild(
                buildCommandElement(
                        "org.eclipse.jdt.core.javabuilder"));
        buildSpec.appendChild(
                buildCommandElement(
                        "com.streambase.sb.ide.core.sbappProjectBuilder2"));

        return buildSpec;
    }

    /**
     * Create the buildCommand element.
     * @param cmd build command
     * @return new buildCommand element
     */
    private Element buildCommandElement(final String cmd) {

        final Element buildCommand = getDoc().createElement("buildCommand");

        final Element name = getDoc().createElement(NAME);
        name.setTextContent(cmd);

        final Element arguments = getDoc().createElement("arguments");

        buildCommand.appendChild(name);
        buildCommand.appendChild(arguments);

        return buildCommand;
    }

    /**
     * Set the project name.
     * @param projectName param
     */
    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }
}
