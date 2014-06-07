/**
 *
 */
package com.blizzardtec.xmlfileworker.maven.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.XmlEntry;

/**
 * @author Barnaby Golden
 *
 */
public final class Build implements XmlEntry {

    /**
     * List of plugin entries.
     */
    private final transient Plugins plugins;

    /**
     * Constructor.
     */
    public Build() {
        plugins = new Plugins();
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.maven.MavenEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element buildEl = doc.createElement("build");

        buildEl.appendChild(plugins.toXML(doc));

        return buildEl;
    }

    /**
     * Add a Maven plugin.
     * @param plugin the plugin to add
     */
    public void addPlugin(final Plugin plugin) {
        plugins.addPlugin(plugin);
    }
}
