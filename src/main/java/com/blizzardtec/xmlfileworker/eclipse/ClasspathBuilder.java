/**
 *
 */
package com.blizzardtec.xmlfileworker.eclipse;

import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;
import com.blizzardtec.xmlfileworker.AbstractXmlFileBuilder;

/**
 * Builds an Eclipse .classpath file.
 *
 * @author Barnaby Golden
 *
 */
public final class ClasspathBuilder extends AbstractXmlFileBuilder {

    /**
     * Src.
     */
    private static final String SRC = "src";
    /**
     * Con.
     */
    private static final String CON = "con";
    /**
     * Target classes.
     */
    private static final String TARGETCLASSES = "target/classes";
    /**
     * Output.
     */
    private static final String OUTPUT = "output";

    /**
     * Build the .classpath Eclipse XML file.
     * @throws HelperException thrown
     */
    public void generate() throws HelperException {

        initialiseDoc();

        final Element classpathElement
                = getDoc().createElement("classpath");

        classpathElement.appendChild(classpathElement(
           SRC, TARGETCLASSES, "src/main/java"));

        classpathElement.appendChild(classpathElement(
           SRC, "target/test-classes", "src/test/java"));

        classpathElement.appendChild(classpathElement(
           CON, null, "org.eclipse.jdt.launching.JRE_CONTAINER"));

        classpathElement.appendChild(classpathElement(
           CON, null,
           "com.streambase.sb.studio.jdt.clientTemplates.SBCLIENT_CONTAINER"));

        classpathElement.appendChild(classpathElement(
           CON, null, "org.eclipse.jdt.junit.JUNIT_CONTAINER/4"));

        classpathElement.appendChild(classpathElement(
           CON, null, "com.streambase.sb.sbtest.LIBRARY_CONTAINER"));

        classpathElement.appendChild(classpathElement(
           OUTPUT, null, TARGETCLASSES));

        getDoc().appendChild(classpathElement);
    }

    /**
     * Add a classpath entry.
     * @param kind type of classpath element
     * @param output type of output - optional, may be null
     * @param path classpath path
     * @return classpath entry element
     */
    private Element classpathElement(final String kind,
                                               final String output,
                                               final String path) {

        final Element classpathentry = getDoc().createElement("classpathentry");

        XMLHelper.addAttribute(getDoc(), classpathentry, "kind", kind);

        if (output != null) {
            XMLHelper.addAttribute(getDoc(), classpathentry, OUTPUT, output);
        }

        XMLHelper.addAttribute(getDoc(), classpathentry, "path", path);

        return classpathentry;
    }
}
