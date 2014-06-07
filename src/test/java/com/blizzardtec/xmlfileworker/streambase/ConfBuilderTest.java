/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.w3c.dom.Document;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.streambase.model.CustomFunction;
import com.blizzardtec.xmlfileworker.streambase.model.ModuleSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorResSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorParameter;
import com.blizzardtec.xmlfileworker.streambase.model.Plugin;

/**
 * @author Barnaby Golden
 *
 */
public final class ConfBuilderTest {

    /**
     * Test the sbd.sbconf file XML builder.
     * @throws HelperException thrown
     */
    @Test
    public void streambaseConfTest() throws HelperException {

        final ConfBuilder streambaseConf = new ConfBuilder();

        final CustomFunction sbCustFunc =
            new CustomFunction("isWarnEnabled", "simple");
        final String[] args = {"auto"};
        sbCustFunc.configure(
                "log2", args, "yak.streambase.logger.LogUtil", "java", null);

        streambaseConf.addCustomFunction(sbCustFunc);

        final OperatorParameter sbOpParam =
                new OperatorParameter("EventCostInterval", "1");

        streambaseConf.addOperatorParameter(sbOpParam);

        final Plugin plugin =
            new Plugin("${STREAMBASE_HOME}/plugin");

        streambaseConf.addPlugin(plugin);

        final ModuleSearch modSearch =
            new ModuleSearch("${STREAMBASE_HOME}/modules");

        streambaseConf.addModuleSearch(modSearch);

        final OperatorResSearch opSearch =
            new OperatorResSearch("${STREAMBASE_HOME}/resources");

        streambaseConf.addOperatorResourceSearch(opSearch);

        streambaseConf.generate();

        final Document doc = streambaseConf.getDoc();

        assertNotNull("sbd.sbconf XML document was null", doc);
    }
}
