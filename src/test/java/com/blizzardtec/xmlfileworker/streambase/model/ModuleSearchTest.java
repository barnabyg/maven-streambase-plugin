/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;
import org.w3c.dom.Document;
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
public final class ModuleSearchTest extends AbstractTest {

    /**
     * Test the construction of a module search object from XML.
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

        Node modNode = null;

        // find a module search node
        for (int i = 0; i < nodeList.getLength(); i++) {
            if ("module-search".equals(nodeList.item(i).getNodeName())) {
                modNode = nodeList.item(i);
                break;
            }
        }

        assertNotNull("module search node not found", modNode);

        ModuleSearch modSearch = new ModuleSearch(modNode);

        assertEquals(
                "Directory value did not match",
                  "${STREAMBASE_HOME}/modules", modSearch.getDirectory());

        modSearch = new ModuleSearch();
        modSearch = new ModuleSearch("dog");
        modSearch.setDirectory("blah");
    }
}
