/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.blizzardtec.xmlfileworker.streambase.model.CustomFunction;
import com.blizzardtec.xmlfileworker.streambase.model.ModuleSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorParameter;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorResSearch;
import com.blizzardtec.xmlfileworker.streambase.model.Plugin;

/**
 * @author Barnaby Golden
 *
 */
public final class ConfListTest {

    /**
     * Add two sets of lists test.
     */
    @Test
    public void addTest() {

        final ConfList list1 = getEmptyConfList();

        CustomFunction cfe = new CustomFunction();
        list1.getCustomFunctions().add(cfe);

        final ConfList list2 = getEmptyConfList();

        list1.add(list2);

        cfe = new CustomFunction();
        list2.getCustomFunctions().add(cfe);

        final Plugin plugin = new Plugin();
        list2.getSbPlugins().add(plugin);

        final OperatorParameter oparam = new OperatorParameter();
        list2.getOpParameters().add(oparam);

        final ModuleSearch mod = new ModuleSearch();
        list2.getModuleSearchPaths().add(mod);

        final OperatorResSearch ors = new OperatorResSearch();
        list2.getOpResSearchPaths().add(ors);

        list1.add(list2);

        assertEquals(
           "invalid conflist size", 2, list1.getCustomFunctions().size());

        final ConfList list3 = new ConfList();

        list1.add(list3);
    }

    /**
     * Get a ConfList with empty lists.
     *
     * @return empty ConfList
     */
    private ConfList getEmptyConfList() {

        final ConfList list1 = new ConfList();

        final List<CustomFunction> cfList = new ArrayList<CustomFunction>();

        final List<Plugin> pList = new ArrayList<Plugin>();

        final List<OperatorParameter> oList =
                new ArrayList<OperatorParameter>();

        final List<ModuleSearch> mList = new ArrayList<ModuleSearch>();

        final List<OperatorResSearch> orList =
                new ArrayList<OperatorResSearch>();

        list1.setCustomFunctions(cfList);
        list1.setSbPlugins(pList);
        list1.setOpParameters(oList);
        list1.setModuleSearchPaths(mList);
        list1.setOpResSearchPaths(orList);

        return list1;
    }
}
