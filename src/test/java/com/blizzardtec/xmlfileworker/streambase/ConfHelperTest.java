/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;
import com.blizzardtec.testbase.AbstractTest;
import com.blizzardtec.xmlfileworker.streambase.model.CustomFunction;
import com.blizzardtec.xmlfileworker.streambase.model.ModuleSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorParameter;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorResSearch;
import com.blizzardtec.xmlfileworker.streambase.model.Plugin;

/**
 * @author Barnaby Golden
 *
 */
public final class ConfHelperTest extends AbstractTest {

    /**
     * Test.
     * @throws HelperException thrown
     */
    @Test
    public void getTest() throws HelperException {
        final File sbdFile =
            new File(getResourceDir() + File.separator + "sbd.sbconf");

        final Document doc = XMLHelper.loadXMLFile(sbdFile);

        Node node = ConfHelper.getCustomFunctionsNode(doc);

        assertNotNull("Custom functions node was null", node);

        assertEquals("Invalid custom-function node name",
                            "custom-functions", node.getNodeName());

        node = ConfHelper.getOperatorParametersNode(doc);

        assertNotNull("Operator parameters node was null", node);

        assertEquals("Invalid operator parameters node name",
                             "operator-parameters", node.getNodeName());

        final List<CustomFunction> list =
            ConfHelper.getCustomFunctions(doc);

        assertFalse("No custom-functions found", list.isEmpty());

        final List<OperatorParameter> opList =
            ConfHelper.getOperatorParameters(doc);

        assertFalse("No operator-parameters found", opList.isEmpty());

        final List<ModuleSearch> modList =
            ConfHelper.getModuleSearches(doc);

        assertFalse("No module-search entries found", modList.isEmpty());

        final List<Plugin> pluginList =
            ConfHelper.getPlugins(doc);

        assertFalse("No plugin entries found", pluginList.isEmpty());

        final List<OperatorResSearch> opResList =
            ConfHelper.getOpResSearches(doc);

        assertFalse(
             "No operator-resource-search entries found", opResList.isEmpty());
    }
}
