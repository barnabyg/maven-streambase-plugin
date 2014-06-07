/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
     * Name of the template sbd.sbconf file to use.
     */
    private static final String SBD_CONF_TEMPLATE = SBDCONF;

    /**
     * Working folder for testing.
     */
    private static File scratch;

    /**
     * Setup.
     */
    @BeforeClass
    public static void setUp() {

        // make the scratch working directory
        scratch = new File(
                    getBaseDir() + File.separator + "scratch");

        scratch.mkdir();
    }

    /**
     * Load an sbd.sbconf file, modify it and then save it.
     * @throws HelperException thrown
     */
    @Test
    public void modifyTest() throws HelperException {

        // first copy the test sbd.sbconf file to the XML test directory
        final String srcXMLFile =
            getBaseDir() + File.separator + "src"
                         + File.separator + "test"
                         + File.separator + "resources"
                         + File.separator + SBD_CONF_TEMPLATE;

        FileHelper.copyFile(srcXMLFile, scratch.getPath());

        final ConfModifier modifier = new ConfModifier();

        final File sbdFile = new File(scratch + File.separator + SBDCONF);

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
    }

    /**
     * Cleanup.
     *
     * @throws IOException thrown
     */
    @AfterClass
    public static void tearDown() throws IOException {

        FileUtils.deleteDirectory(
                new File(getBaseDir() + File.separator + "scratch"));

    }
}
