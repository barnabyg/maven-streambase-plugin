/**
 *
 */
package com.blizzardtec.xmlfileworker;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;

/**
 * Interface for classes representing XML nodes.
 *
 * @author Barnaby Golden
 *
 */
public interface XmlEntry {

    /**
     * Types of entries to add to sbd.sbconf.
     *
     */
    public enum EntryType { PLUGIN, CUSTOM, MODULE, OPRES, OPPARAM }

    /**
     * Convert the entry to the appropriate sbd.sbconf XML.
     * @param doc XML document
     * @return XML element
     * @throws HelperException thrown
     */
    Element toXML(final Document doc) throws HelperException;
}
