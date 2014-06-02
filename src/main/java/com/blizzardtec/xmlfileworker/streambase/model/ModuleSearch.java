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
public final class ModuleSearch implements XmlEntry {

    /**
     * Directory.
     */
    private static final String DIRECTORY = "directory";
    /**
     * Directory attribute.
     */
    private transient String directory;

    /**
     * No param constructor needed by mojo code.
     */
    public ModuleSearch() {
        super();
    }

    /**
     * Constructor.
     * @param directory plugin directory
     */
    public ModuleSearch(final String directory) {
        this.directory = directory;
    }

    /**
     * Constructor using a module-search XML node.
     *
     * @param node module-search XML node
     */
    public ModuleSearch(final Node node) {

        // then check the attributes
        final NamedNodeMap map = node.getAttributes();

        this.directory =
                XMLHelper.getAttributeTextFromList(map, DIRECTORY);
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.streambase.SBEntry#toXML(org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {
        final Element module = doc.createElement("module-search");

        XMLHelper.addAttribute(doc, module, DIRECTORY, directory);

        return module;
    }

    /**
     * @param directory the directory to set
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
