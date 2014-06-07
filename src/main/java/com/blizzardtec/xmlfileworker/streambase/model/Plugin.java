/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;
import com.blizzardtec.xmlfileworker.XmlEntry;

/**
 * @author Barnaby Golden
 *
 */
public final class Plugin implements XmlEntry {

    /**
     * Directory.
     */
    private static final String DIRECTORY = "directory";
    /**
     * Directory attribute.
     */
    private transient String directory;

    /**
     * Constructor with no parameters - needed by mojo plugin code.
     */
    public Plugin() {
        super();
    }

    /**
     * Constructor.
     * @param directory plugin directory
     */
    public Plugin(final String directory) {
        this.directory = directory;
    }

    /**
     * Construct the object from a plugin XML node.
     *
     * @param node plugin XML node
     */
    public Plugin(final Node node) {

        final NamedNodeMap map = node.getAttributes();

        this.directory =
                XMLHelper.getAttributeTextFromList(map, DIRECTORY);
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.streambase.SBEntry#toXML(org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element plugin = doc.createElement("plugin");

        XMLHelper.addAttribute(doc, plugin, DIRECTORY, directory);

        return plugin;
    }

    /**
     * Setter - used by Mojo plugin code to insert values
     * in the object.
     * @param directory directory value
     */
    public void setDirectory(final String directory) {
        this.directory = directory;
    }

    /**
     *
     * @return directory
     */
    public String getDirectory() {
        return this.directory;
    }
}
