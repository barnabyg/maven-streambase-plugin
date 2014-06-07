/**
 *
 */
package com.blizzardtec.xmlfileworker;

import com.blizzardtec.helpers.HelperException;

/**
 * Abstract base class for classes building XML configuration files.
 *
 * @author Barnaby Golden
 *
 */
public abstract class AbstractXmlFileBuilder
                extends AbstractXmlFileWorker implements XmlFileBuilder {

    /**
     * Construct xml from the basic elements.
     * This method is used when we want to create the xml
     * from scratch.
     *
     * @throws HelperException thrown
     */
    public abstract void generate() throws HelperException;
}
