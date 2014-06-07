/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;
import com.blizzardtec.xmlfileworker.streambase.model.CustomFunction;
import com.blizzardtec.xmlfileworker.streambase.model.ModuleSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorParameter;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorResSearch;
import com.blizzardtec.xmlfileworker.streambase.model.Plugin;

/**
 * Utility class that contains methods for working on the Streambase
 * sbd.sbconf file.
 *
 * @author Barnaby Golden
 *
 */
public final class ConfHelper {

    /**
     * Op param.
     */
    private static final String OPPARAM = "operator-parameters";
    /**
     * Custom function.
     */
    private static final String CUSTFUNC = "custom-functions";
    /**
     * Global.
     */
    private static final String GLOBAL = "global";

    /**
     * Private constructor denotes utility class.
     */
    private ConfHelper() {

    }


    /**
     * Extract the global node from the node list.
     * @param doc XML document
     * @return global node or null if not found
     * @throws HelperException thrown
     */
    public static Node getGlobalNode(final Document doc)
                throws HelperException {

        // list of nodes under parent node
        final NodeList nodeList = ConfHelper.getSbdConfNodes(doc);

        Node global = XMLHelper.getNodeFromList(nodeList, GLOBAL);

        // if the operator-parameters node does not exist, create it
        if (global == null) {
            global = doc.createElement(GLOBAL);
            getTopNode(doc).appendChild(global);
        }

        return global;
    }

    /**
     * Extract the custom-functions node from the list of nodes.
     * @param doc XML document
     * @return custom-functions node or null if not found
     * @throws HelperException thrown
     */
    public static Node getCustomFunctionsNode(final Document doc)
                throws HelperException {

        // list of nodes under parent node
        final NodeList nodeList = ConfHelper.getSbdConfNodes(doc);

        Node custFunc =
            XMLHelper.getNodeFromList(nodeList, CUSTFUNC);

        // if the custom-functions node does not exist, create it
        if (custFunc == null) {
            custFunc = doc.createElement(CUSTFUNC);
            getTopNode(doc).appendChild(custFunc);
        }

        return custFunc;
    }

    /**
     * Extract the operator-parameters node from the list of nodes.
     * @param doc XML document
     * @return custom-functions node or null if not found
     * @throws HelperException thrown
     */
    public static Node getOperatorParametersNode(final Document doc)
                throws HelperException {

        // list of nodes under parent node
        final NodeList nodeList = ConfHelper.getSbdConfNodes(doc);

        Node opParams =
            XMLHelper.getNodeFromList(nodeList, OPPARAM);

        // if the operator-parameters node does not exist, create it
        if (opParams == null) {
            opParams = doc.createElement(OPPARAM);
            getTopNode(doc).appendChild(opParams);
        }

        return opParams;
    }

    /**
     * Get the list of nodes under the streambase-configuration
     * parent node.
     * @param doc XML document
     * @return list of nodes
     * @throws HelperException thrown
     */
    private static NodeList getSbdConfNodes(final Document doc)
                                        throws HelperException {

        return getTopNode(doc).getChildNodes();
    }

    /**
     * Get the top level node in the sbd.sbconf file.
     * This will be the streambase-configuration node.
     * @param doc XML document
     * @return top level node
     * @throws HelperException thrown
     */
    private static Node getTopNode(final Document doc)
                                        throws HelperException {

        final Node sBConfig = doc.getFirstChild();

        // sanity check
        if (!"streambase-configuration".equalsIgnoreCase(
                                            sBConfig.getNodeName())) {
            throw new HelperException("Invalid sbd.sbconf XML format");
        }

        return sBConfig;
    }

    /**
     * Get the list of custom-function entries.
     * Will return a zero length list if no entries are found.
     *
     * @param doc XML document
     * @return list of custom-function
     * @throws HelperException thrown
     */
    public static List<CustomFunction> getCustomFunctions(final Document doc)
                                        throws HelperException {

        final List<CustomFunction> list = new ArrayList<CustomFunction>();

        final Node custFuncNode = getCustomFunctionsNode(doc);

        if (custFuncNode != null) {
            // for each node found, create a corresponding
            // CustomFunction object
            final NodeList nodeList = custFuncNode.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                if ("custom-function".equals(nodeList.item(i).getNodeName())) {

                    list.add(new CustomFunction(nodeList.item(i)));
                }
            }
        }

        return list;
    }

    /**
     * Get the list of operator-parameter entries.
     * Will return a zero length list if no entries are found.
     *
     * @param doc XML document
     * @return list of operator-parameter
     * @throws HelperException thrown
     */
    public static List<OperatorParameter> getOperatorParameters(
                            final Document doc)
                                        throws HelperException {

        final List<OperatorParameter> list = new ArrayList<OperatorParameter>();

        final Node opParamNode = getOperatorParametersNode(doc);

        if (opParamNode != null) {
            // for each node found, create a corresponding
            // OperatorParameter object
            final NodeList nodeList = opParamNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if ("operator-parameter".equals(
                        nodeList.item(i).getNodeName())) {
                    list.add(new OperatorParameter(nodeList.item(i)));
                }
            }
        }

        return list;
    }

    /**
     * Get the list of module search entries.
     * Will return a zero length list if no entries are found.
     *
     * @param doc XML document
     * @return list of module-search
     * @throws HelperException thrown
     */
    public static List<ModuleSearch> getModuleSearches(
                            final Document doc)
                                        throws HelperException {

        final List<ModuleSearch> list = new ArrayList<ModuleSearch>();

        final Node globalNode = getGlobalNode(doc);

        if (globalNode != null) {
            // for each node found, create a corresponding
            // ModuleSearch object
            final NodeList nodeList = globalNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if ("module-search".equals(nodeList.item(i).getNodeName())) {
                    list.add(new ModuleSearch(nodeList.item(i)));
                }
            }
        }

        return list;
    }

    /**
     * Get the list of plugins.
     * Will return a zero length list if no entries are found.
     *
     * @param doc XML document
     * @return list of plugins
     * @throws HelperException thrown
     */
    public static List<Plugin> getPlugins(final Document doc)
                                        throws HelperException {

        final List<Plugin> list = new ArrayList<Plugin>();

        final Node globalNode = getGlobalNode(doc);

        if (globalNode != null) {
            // for each node found, create a corresponding
            // Plugin object
            final NodeList nodeList = globalNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if ("plugin".equals(nodeList.item(i).getNodeName())) {
                    list.add(new Plugin(nodeList.item(i)));
                }
            }
        }

        return list;
    }

    /**
     * Get the list of operator-resource-search.
     * Will return a zero length list if no entries are found.
     *
     * @param doc XML document
     * @return list of operator-resource-search
     * @throws HelperException thrown
     */
    public static List<OperatorResSearch> getOpResSearches(final Document doc)
                                        throws HelperException {

        final List<OperatorResSearch> list = new ArrayList<OperatorResSearch>();

        final Node globalNode = getGlobalNode(doc);

        if (globalNode != null) {
            // for each node found, create a corresponding
            // operator-resource-search object
            final NodeList nodeList = globalNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if ("operator-resource-search".equals(
                            nodeList.item(i).getNodeName())) {
                    list.add(new OperatorResSearch(nodeList.item(i)));
                }
            }
        }

        return list;
    }
}
