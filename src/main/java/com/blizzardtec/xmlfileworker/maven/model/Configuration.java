/**
 *
 */
package com.blizzardtec.xmlfileworker.maven.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.XmlEntry;

/**
 * @author Barnaby Golden
 *
 */
public final class Configuration implements XmlEntry {

    /**
     * List of child entries required to make up the configuration entry.
     */
    private final transient List<XmlEntry> childEntries;

    /**
     * Constructor.
     */
    public Configuration() {

        childEntries = new ArrayList<XmlEntry>();
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.maven.MavenEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element configEntry = doc.createElement("configuration");

        final Iterator<XmlEntry> iterator = childEntries.iterator();

        while (iterator.hasNext()) {
            configEntry.appendChild(iterator.next().toXML(doc));
        }

        return configEntry;
    }

    /**
     * Add a configuration entry.
     * @param entry config entry
     */
    public void addEntry(final XmlEntry entry) {
        childEntries.add(entry);
    }
}
