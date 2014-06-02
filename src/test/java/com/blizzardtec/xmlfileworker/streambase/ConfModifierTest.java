/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;

import com.blizzardtec.helpers.FileHelper;
import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.testbase.AbstractTest;
import com.blizzardtec.xmlfileworker.XmlEntry.EntryType;
import com.blizzardtec.xmlfileworker.streambase.model.CustomFunction;
import com.blizzardtec.xmlfileworker.streambase.model.ModuleSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorResSearch;
import com.blizzardtec.xmlfileworker.streambase.model.Plugin;

/**
 * @author Barnaby Golden
 *
 */
public final class ConfModifierTest extends AbstractTest {

    /**
     * String.
     */
    private static final String STRING = "string";
    /**
     * sbd.sbconf name.
     */
    private static final String SBDCONF = "sbd.sbconf";
    /**
     * Location to run XML test.
     */
    private static final String XML_TEST_DIR = "xmltestdir";
    /**
     * Name of the template sbd.sbconf file to use.
     */
    private static final String SBD_CONF_TEMPLATE = SBDCONF;

    /**
     * Load an sbd.sbconf file, modify it and then save it.
     * @throws HelperException thrown
     */
    @Test
    public void modifyTest() throws HelperException {
        // first copy the test sbd.sbconf file to the XML test directory
        final String srcXMLFile =
            getBaseDir() + File.separator + SBD_CONF_TEMPLATE;
        final String destDir = getBaseDir() + File.separator + XML_TEST_DIR;
        FileHelper.copyFile(srcXMLFile, destDir);

        final ConfModifier modifier = new ConfModifier();

        final File sbdFile = new File(destDir + File.separator + SBDCONF);

        modifier.load(sbdFile);

        final List<ModuleSearch> modList =
                    new ArrayList<ModuleSearch>();

        final ModuleSearch modSearch =
            new ModuleSearch("home/frog/parrot");
        modList.add(modSearch);
        modifier.update(modList, EntryType.MODULE);

        final List<Plugin> plugList =
                    new ArrayList<Plugin>();

        final Plugin plugin =
            new Plugin("dog/plugins/badger");
        plugList.add(plugin);
        modifier.update(plugList, EntryType.PLUGIN);

        final List<OperatorResSearch> opResList =
                    new ArrayList<OperatorResSearch>();

        final OperatorResSearch opSearch =
            new OperatorResSearch("spade/shovel/widget");
        opResList.add(opSearch);
        modifier.update(opResList, EntryType.OPRES);

        final List<CustomFunction> custList =
                    new ArrayList<CustomFunction>();

        final String[] args = {STRING, "int"};
        final CustomFunction custFunc =
            new CustomFunction("func1", "simple");
        custFunc.configure("f", args, "com.math.stuff", "java", STRING);
        custList.add(custFunc);
        modifier.update(custList, EntryType.CUSTOM);

        final Document doc = modifier.getDoc();

        assertNotNull("Document is null", doc);

        modifier.save();

        // cleanup
        sbdFile.delete();
    }
}
