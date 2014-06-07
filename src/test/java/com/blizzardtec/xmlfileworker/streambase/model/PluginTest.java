/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.testbase.AbstractTest;
import com.blizzardtec.xmlfileworker.streambase.ConfHelper;
import com.blizzardtec.xmlfileworker.streambase.ConfModifier;

/**
 * @author Barnaby Golden
 *
 */
public final class PluginTest extends AbstractTest {

    /**
     * Test the construction of a plugin object from XML.
     *
     * @throws HelperException thrown
     */
    @Test
    public void constructFromXml() throws HelperException {

        final File confFile =
            new File(getResourceDir() + File.separator + "sbd.sbconf");

        final ConfModifier modifier = new ConfModifier();
        modifier.load(confFile);
        final Document doc = modifier.getDoc();

        final Node node = ConfHelper.getGlobalNode(doc);

        final NodeList nodeList = node.getChildNodes();

        Node pluginNode = null;

        // find a plugin node
        for (int i = 0; i < nodeList.getLength(); i++) {
            if ("plugin".equals(nodeList.item(i).getNodeName())) {
                pluginNode = nodeList.item(i);
                break;
            }
        }

        assertNotNull("plugin node not found", pluginNode);

        final Plugin plugin = new Plugin(pluginNode);

        assertEquals(
                "Directory value did not match",
                  "${STREAMBASE_HOME}/plugin", plugin.getDirectory());

        final Element element = plugin.toXML(doc);

        assertNotNull("XML generation failed", element);
    }
}
