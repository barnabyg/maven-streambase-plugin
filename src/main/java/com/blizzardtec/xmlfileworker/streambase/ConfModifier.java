/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase;

import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;
import com.blizzardtec.xmlfileworker.AbstractXmlFileModifier;
import com.blizzardtec.xmlfileworker.XmlEntry;
import com.blizzardtec.xmlfileworker.XmlEntry.EntryType;
import com.blizzardtec.xmlfileworker.streambase.model.CustomFunction;
import com.blizzardtec.xmlfileworker.streambase.model.ModuleSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorResSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorParameter;
import com.blizzardtec.xmlfileworker.streambase.model.Plugin;

/**
 * Loads an sbd.sbconf file in as an XML document and allows modification of
 * certain sections.
 *
 * @author Barnaby Golden
 *
 */
//@SuppressWarnings("PMD")
public final class ConfModifier extends AbstractXmlFileModifier {

    /**
     * Add list of entries.
     * @param list list of sbd.sbconf entries
     * @param entryType type of entry
     * @throws HelperException thrown
     */
    public void update(
            final List<? extends XmlEntry> list, final EntryType entryType)
                                                throws HelperException {

        if ((list != null) && (!list.isEmpty())) {

            setModified(true);

            final Iterator<? extends XmlEntry> iterator =
                list.iterator();

            while (iterator.hasNext()) {
                if (entryType == EntryType.PLUGIN) {
                    addPlugin((Plugin) iterator.next());
                } else if (entryType == EntryType.CUSTOM) {
                    addCustumFunction((CustomFunction) iterator.next());
                } else if (entryType == EntryType.MODULE) {
                    addModuleSearch((ModuleSearch) iterator.next());
                } else if (entryType == EntryType.OPPARAM) {
                    addOperatorParameter((OperatorParameter) iterator.next());
                } else if (entryType == EntryType.OPRES) {
                    addOperatorSearch((OperatorResSearch) iterator.next());
                }
            }
        }
    }

    /**
     * Add operator-resource-search node.
     * @param opSearch operator-resource-search node
     * @throws HelperException thrown
     */
    private void addOperatorSearch(
            final OperatorResSearch opSearch)
                        throws HelperException {

        // find the global node
        final Node global =
            ConfHelper.getGlobalNode(getDoc());

        final List<OperatorResSearch> list =
                ConfHelper.getOpResSearches(getDoc());

        // see if the new entry already exists in the list
        boolean found = false;
        final Iterator<OperatorResSearch> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (opSearch.getDirectory().equals(
                    iterator.next().getDirectory())) {
                found = true;
            }
        }

        // if not found, add the operator-resource-search
        // entry to the global node
        if (!found) {
            global.appendChild(opSearch.toXML(getDoc()));
        }
    }

    /**
     * Add a module-search entry.
     * @param modSearch module search entry
     * @throws HelperException thrown
     */
    private void addModuleSearch(final ModuleSearch modSearch)
                        throws HelperException {

        // find the global node
        final Node global =
            ConfHelper.getGlobalNode(getDoc());

        final List<ModuleSearch> list =
            ConfHelper.getModuleSearches(getDoc());

        // see if the new entry already exists in the list
        boolean found = false;
        final Iterator<ModuleSearch> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (modSearch.getDirectory().equals(
                    iterator.next().getDirectory())) {
                found = true;
            }
        }

        // if not found, add the plugin entry to the global node
        if (!found) {
            global.appendChild(modSearch.toXML(getDoc()));
        }
    }

    /**
     * Add a plugin entry.
     * @param plugin plugin entry
     * @throws HelperException thrown
     */
    private void addPlugin(final Plugin plugin)
                        throws HelperException {

        // find the global node
        final Node global =
            ConfHelper.getGlobalNode(getDoc());

        final List<Plugin> list =
            ConfHelper.getPlugins(getDoc());

        // see if the new entry already exists in the list
        boolean found = false;
        final Iterator<Plugin> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (plugin.getDirectory().equals(
                    iterator.next().getDirectory())) {
                found = true;
            }
        }

        // if not found, add the plugin entry to the global node
        if (!found) {
            global.appendChild(plugin.toXML(getDoc()));
        }
    }

    /**
     * Add custom-function node.
     * @param custFunc custom-function node
     * @throws HelperException thrown
     */
    private void addCustumFunction(final CustomFunction custFunc)
                        throws HelperException {

        // find the custom-functions node
        final Node custFunctionsNode =
            ConfHelper.getCustomFunctionsNode(getDoc());

        if (custFunctionsNode == null) {
            throw new HelperException(
                    "could not locate custom-functions node");
        }

        // check to see if there is already an entry that corresponds
        // to the new entry, i.e. it has the same name attribute
        final List<CustomFunction> list =
            ConfHelper.getCustomFunctions(getDoc());

        boolean found = false;
        final Iterator<CustomFunction> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (custFunc.getName().equals(
                    iterator.next().getName())) {
                found = true;
            }
        }

        // add the new custom-function entry if it does not
        // already exist, else we modify the existing entry
        if (found) {
            final Node node =
              XMLHelper.getNodeWithGivenValue(
                custFunctionsNode.getChildNodes(), "name", custFunc.getName());

            node.getParentNode().removeChild(node);
        }

        custFunctionsNode.appendChild(custFunc.toXML(getDoc()));
    }

    /**
     * Add operator-parameter node.
     * @param opParam operator-parameter node
     * @throws HelperException thrown
     */
    private void addOperatorParameter(final OperatorParameter opParam)
                        throws HelperException {

        // find the operator-parameters node
        final Node opParamsNode =
            ConfHelper.getOperatorParametersNode(getDoc());

        // add the new operator-parameters entry
        opParamsNode.appendChild(opParam.toXML(getDoc()));
    }
}
