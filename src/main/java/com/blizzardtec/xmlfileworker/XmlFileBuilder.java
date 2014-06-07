/**
 *
 */
package com.blizzardtec.xmlfileworker;

import com.blizzardtec.helpers.HelperException;

/**
 * Interface for classes that build XML configuration files.
 *
 * @author Barnaby Golden
 *
 */
public interface XmlFileBuilder extends XmlFileWorker {

    /**
     * Construct xml from the basic elements.
     * This method is used when we want to create the xml
     * from scratch.
     *
     * @throws HelperException thrown
     */
    void generate() throws HelperException;
}
