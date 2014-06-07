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
public final class Generic implements XmlEntry {

    /**
     * Name of the element.
     */
    private final transient String name;
    /**
     * Value of the element.
     */
    private final transient String value;

    /**
     * Constructor - takes the name and value of a simple XML element.
     * @param name name of element
     * @param value value of element
     */
    public Generic(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.maven.MavenEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element simpleElement = doc.createElement(name);
        simpleElement.setTextContent(value);

        return simpleElement;
    }

}
