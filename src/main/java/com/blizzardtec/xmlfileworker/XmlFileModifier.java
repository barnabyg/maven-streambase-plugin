/**
 * 
 */
package com.blizzardtec.xmlfileworker;

import java.util.List;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.XmlEntry.EntryType;

/**
 * Interface for classes that modify existing XML configuration files.
 *
 * @author Barnaby Golden
 *
 */
public interface XmlFileModifier extends XmlFileWorker {

    /**
     * Indicate if the file has been modified.
     *
     * @return true if modified
     */
    boolean isModified();

    /**
     * Provide a file of entries to update the list.
     *
     * @param list list of entries
     * @param entryType the type of the entries
     * @throws HelperException thrown
     */
    void update(
            final List<? extends XmlEntry> list, final EntryType entryType)
                                                throws HelperException;
}
