/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase.model;

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
public final class CustomFunctions implements XmlEntry {

    /**
     * List of custom function elements.
     */
    private final transient List<CustomFunction> custFunctions;

    /**
     * Constructor.
     */
    public CustomFunctions() {
        custFunctions = new ArrayList<CustomFunction>();
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.streambase.StreambaseEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element custFunctElement = doc.createElement("custom-functions");

        final Iterator<CustomFunction> iterator =
                                            custFunctions.iterator();
        while (iterator.hasNext()) {
            final CustomFunction entry = iterator.next();
            custFunctElement.appendChild(entry.toXML(doc));
        }

        return custFunctElement;
    }

    /**
     * Add a new custom function.
     * @param customFunction custom function element
     */
    public void addCustomFunction(
            final CustomFunction customFunction) {
        custFunctions.add(customFunction);
    }
}
