/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public final class CustomFunctionTest extends AbstractTest {

    /**
     * Test the construction of a CustomFunction object
     * from a custom-function XML node.
     *
     * @throws HelperException thrown
     */
    @Test
    public void constructFromXml() throws HelperException {

        final File confFile =
            new File(getBaseDir() + File.separator + "sbd.sbconf");

        final ConfModifier modifier = new ConfModifier();
        modifier.load(confFile);
        final Document doc = modifier.getDoc();

        final Node node = ConfHelper.getCustomFunctionsNode(doc);

        assertNotNull("No custom-functions node found", node);

        final NodeList list = node.getChildNodes();

        final List<CustomFunction> funcs = new ArrayList<CustomFunction>();

        // strip out the real nodes
        for (int i = 0; i < list.getLength(); i++) {
            if ("custom-function".equals(list.item(i).getNodeName())) {
                funcs.add(new CustomFunction(list.item(i)));
            }
        }

        final Iterator<CustomFunction> iterator = funcs.iterator();

        while (iterator.hasNext()) {
            final CustomFunction cfunc = iterator.next();
            if ("log20".equals(cfunc.getName())) {
                assertEquals(
                "Language did not match", "java", cfunc.getLanguage());
                assertEquals(
                "Alias did not match", "log_base_twenty", cfunc.getAlias());
                assertEquals(
                "Class did not match", "java.lang.Math", cfunc.getFuncClass());
                assertEquals(
                        "Args did not match", 2, cfunc.getArgs().length);
            }
        }
    }
}
