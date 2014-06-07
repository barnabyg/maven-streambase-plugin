/**
 *
 */
package com.blizzardtec.xmlfileworker.maven.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;

/**
 * @author Barnaby Golden
 *
 */
public final class PluginTest {

    /**
     * Test the plugin.
     *
     * @throws HelperException thrown
     */
    @Test(expected = HelperException.class)
    public void pluginNullTest() throws HelperException {

        final Document doc = XMLHelper.getDocument();

        final Plugin plugin = new Plugin(null, null, null);

        plugin.toXML(doc);
    }

    /**
     * Test the plugin.
     *
     * @throws HelperException thrown
     */
    @Test
    public void pluginTest() throws HelperException {

        final Document doc = XMLHelper.getDocument();

        final Plugin plugin = new Plugin("blah", "dog", null);

        plugin.toXML(doc);

        plugin.addConfiguration(new Configuration());

        final Element element = plugin.toXML(doc);

        assertNotNull("element was null", element);
    }
}
