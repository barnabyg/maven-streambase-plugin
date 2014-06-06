/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
public final class OperatorParameterTest extends AbstractTest {

    /**
     * Test the construction of an OperatorParameter object
     * from a custom-function XML node.
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

        final Node node = ConfHelper.getOperatorParametersNode(doc);

        assertNotNull("No operator-parameters node found", node);

        final NodeList list = node.getChildNodes();

        Node opParamNode = null;

        // strip out the real node
        for (int i = 0; i < list.getLength(); i++) {
            if ("operator-parameter".equals(list.item(i).getNodeName())) {
                opParamNode = list.item(i);
            }
        }

        assertNotNull("Could not find operator-parameter node", opParamNode);

        final OperatorParameter cfunc = new OperatorParameter(opParamNode);

        assertEquals("Name did not match", "MyName", cfunc.getName());
        assertEquals("Value did not match", "'MyValue'", cfunc.getValue());
        assertTrue("", cfunc.isEnciphered());

        final Element element = cfunc.toXML(doc);

        assertNotNull("XML generation failed", element);
    }
}
