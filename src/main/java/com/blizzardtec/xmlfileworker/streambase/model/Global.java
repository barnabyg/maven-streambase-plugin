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
public final class Global implements XmlEntry {

    /**
     * Plugins.
     */
    private final transient List<Plugin> plugins;
    /**
     * Module searches.
     */
    private final transient List<ModuleSearch> moduleSearches;
    /**
     * Operator resource searches.
     */
    private final transient List<OperatorResSearch> opSearches;

    /**
     * Constructor.
     */
    public Global() {
        plugins = new ArrayList<Plugin>();
        moduleSearches = new ArrayList<ModuleSearch>();
        opSearches = new ArrayList<OperatorResSearch>();
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.streambase.SBEntry#toXML(org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element global = doc.createElement("global");

        final Iterator<Plugin> iterator = plugins.iterator();

        while (iterator.hasNext()) {
            global.appendChild(iterator.next().toXML(doc));
        }

        final Iterator<ModuleSearch> iterator2 =
                                        moduleSearches.iterator();

        while (iterator2.hasNext()) {
            global.appendChild(iterator2.next().toXML(doc));
        }

        final Iterator<OperatorResSearch> iterator3 =
                                        opSearches.iterator();

        while (iterator3.hasNext()) {
            global.appendChild(iterator3.next().toXML(doc));
        }

        return global;
    }

    /**
     * Add plugin.
     * @param plugin plugin
     */
    public void addPlugin(final Plugin plugin) {
        plugins.add(plugin);
    }

    /**
     * Add module search.
     * @param modSearch module search entry
     */
    public void addModuleSearch(final ModuleSearch modSearch) {
        moduleSearches.add(modSearch);
    }

    /**
     * Add operator resource search.
     * @param opSearch operator resource search entry
     */
    public void addOperatorResourceSearch(
                    final OperatorResSearch opSearch) {
        opSearches.add(opSearch);
    }
}
