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
public final class Dependencies implements XmlEntry {

    /**
     * List of dependencies (may be zero length).
     */
    private final transient List<Dependency> dependencyList;

    /**
     * Constructor.
     */
    public Dependencies() {
        dependencyList = new ArrayList<Dependency>();
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.maven.MavenEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {
        final Element dependenciesEl = doc.createElement("dependencies");

        final Iterator<Dependency> iterator = dependencyList.iterator();

        while (iterator.hasNext()) {
            final Dependency dependency = iterator.next();
            dependenciesEl.appendChild(dependency.toXML(doc));
        }

        return dependenciesEl;
    }

    /**
     * Add a Maven dependency.
     * @param dependency the dependency to add
     */
    public void addDependency(final Dependency dependency) {
        dependencyList.add(dependency);
    }
}
