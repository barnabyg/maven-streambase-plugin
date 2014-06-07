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
public final class Plugins implements XmlEntry {

    /**
     * List of plugins (may be zero length).
     */
    private final transient List<Plugin> pluginList;

    /**
     * Constructor.
     */
    public Plugins() {
        pluginList = new ArrayList<Plugin>();
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.maven.MavenEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element pluginsEl = doc.createElement("plugins");

        final Iterator<Plugin> iterator = pluginList.iterator();

        // add the nested plugin elements
        while (iterator.hasNext()) {
            final Plugin plugin = iterator.next();
            pluginsEl.appendChild(plugin.toXML(doc));
        }

        // and finally, add the special maven-streambase-plugin entry
        final MavenStreambasePlugin mavenSbPlugin = new MavenStreambasePlugin();
        pluginsEl.appendChild(mavenSbPlugin.toXML(doc));

        return pluginsEl;
    }

    /**
     * Add a Maven plugin.
     * @param plugin the plugin to add
     */
    public void addPlugin(final Plugin plugin) {
        pluginList.add(plugin);
    }
}
