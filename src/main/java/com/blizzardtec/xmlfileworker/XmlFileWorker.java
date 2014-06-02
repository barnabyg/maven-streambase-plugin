/**
 * 
 */
package com.blizzardtec.xmlfileworker;

import java.io.File;

import com.blizzardtec.helpers.HelperException;

/**
 * Interface for classes that will operate on XML files
 * such as pom.xml and sbd.sbconf.
 *
 * @author Barnaby Golden
 *
 */
public interface XmlFileWorker {

    /**
     * Load an XML file in and store in the XML Document.
     *
     * @param xmlfile source XML file
     * @throws HelperException thrown
     */
    void load(final File xmlfile) throws HelperException;

    /**
     * Save the modified XML back to the original XML file.
     * @throws HelperException thrown
     */
    void save() throws HelperException;

    /**
     * Save the modified XML back to a new XML file.
     * @param newXmlFile new file
     * @throws HelperException thrown
     */
    void save(final File newXmlFile) throws HelperException;

}
