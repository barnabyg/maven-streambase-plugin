/**
 *
 */
package com.blizzardtec.xmlfileworker;

import java.io.File;

import org.w3c.dom.Document;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;

/**
 * Abstract base class for classes that operate on XML
 * configuration files.
 *
 * @author Barnaby Golden
 *
 */
public abstract class AbstractXmlFileWorker implements XmlFileWorker {

    /**
     * XML document representing the pom.xml file.
     */
    private transient Document doc;
    /**
     * File handle for the original XML file.
     */
    private transient File xmlFile;

    /* (non-Javadoc)
     * @see com.blizzardtec.xmlfilebuilders.XmlFileWorker#load(
     * org.w3c.dom.Document)
     */
    @Override
    public final void load(final File xmlFile) throws HelperException {
        doc = XMLHelper.loadXMLFile(xmlFile);
        this.xmlFile = xmlFile;
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.xmlfilebuilders.XmlFileWorker#save(
     * org.w3c.dom.Document)
     */
    @Override
    public final void save() throws HelperException {
        save(xmlFile);
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.xmlfilebuilders.XmlFileWorker#save(
     * org.w3c.dom.Document)
     */
    @Override
    public final void save(final File newXmlFile) throws HelperException {
        // sanity check
        if (doc == null) {
            throw new HelperException("XML Document undefined");
        }

        XMLHelper.saveXMLFile(doc, newXmlFile.getPath());
    }

    /**
     * Initialise the XML document.
     * @throws HelperException thrown
     */
    protected final void initialiseDoc() throws HelperException {
        doc = XMLHelper.getDocument();
    }

    /**
     * Get the document.
     * @return XML document
     */
    public final Document getDoc() {
        return this.doc;
    }
}
