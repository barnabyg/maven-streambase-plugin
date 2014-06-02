/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase;

import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.AbstractXmlFileBuilder;
import com.blizzardtec.xmlfileworker.streambase.model.CustomFunction;
import com.blizzardtec.xmlfileworker.streambase.model.CustomFunctions;
import com.blizzardtec.xmlfileworker.streambase.model.Global;
import com.blizzardtec.xmlfileworker.streambase.model.ModuleSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorResSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorParameter;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorParameters;
import com.blizzardtec.xmlfileworker.streambase.model.Plugin;

/**
 * Build a template sbd.sbconf file from scratch.
 * 
 * @author Barnaby Golden
 * 
 */
public final class ConfBuilder extends AbstractXmlFileBuilder {

    /**
     * Global entry.
     */
    private final transient Global global;
    /**
     * Custom functions entry.
     */
    private final transient CustomFunctions custFunctions;
    /**
     * Operator parameters entry.
     */
    private final transient OperatorParameters opParameters;
   
    /**
     * Constructor.
     */
    public ConfBuilder() {
        super();
        global = new Global();
        custFunctions = new CustomFunctions();
        opParameters = new OperatorParameters();
    }

    /**
     * Construct an sbd.sbconf from the basic elements.
     * This method is used when we want to create an sbd.sbconf
     * file from scratch.
     *
     * @throws HelperException thrown
     */
    public void generate() throws HelperException {
        initialiseDoc();

        final Element sbConf =
            getDoc().createElement("streambase-configuration");

        sbConf.appendChild(global.toXML(getDoc()));
        sbConf.appendChild(custFunctions.toXML(getDoc()));
        sbConf.appendChild(opParameters.toXML(getDoc()));

        getDoc().appendChild(sbConf);
    }

//    /**
//     * Build the custom-functions element.
//     * @param doc XML document
//     * @return custom-functions element
//     */
//    private static Element buildCustomFunctions(final Document doc) {
//        final Element custFuncs = doc.createElement("custom-functions");
//
//        final Element custFunc = doc.createElement("custom-function");
//        NamedNodeMap atts = custFunc.getAttributes();
//        final Attr nameAttr = doc.createAttribute("name");
//        nameAttr.setValue("nth_value");
//        final Attr typeAttr = doc.createAttribute("type");
//        typeAttr.setValue("aggregate");
//        atts.setNamedItem(nameAttr);
//        atts.setNamedItem(typeAttr);
//
//        final Element args = doc.createElement("args");
//        final Element arg1 = doc.createElement("arg");
//        atts = arg1.getAttributes();
//        final Attr typeAttr1 = doc.createAttribute("type");
//        typeAttr1.setValue("double");
//        atts.setNamedItem(typeAttr1);
//        final Element arg2 = doc.createElement("arg");
//        atts = arg2.getAttributes();
//        final Attr typeAttr2 = doc.createAttribute("type");
//        typeAttr2.setValue("int");
//        atts.setNamedItem(typeAttr2);
//
//        args.appendChild(arg1);
//        args.appendChild(arg2);
//
//        custFunc.appendChild(args);
//
//        final Element returnEl = doc.createElement("return");
//        atts = returnEl.getAttributes();
//        final Attr rTypeAttr = doc.createAttribute("type");
//        rTypeAttr.setValue("double");
//        atts.setNamedItem(rTypeAttr);
//        
//        custFunc.appendChild(returnEl);
//
//        custFuncs.appendChild(custFunc);
//        
//        return custFuncs;
//    }

//    /**
//     * Build the Streambase sbd.sbconf XML.
//     * @return XML document for sbd.sbconf
//     * @throws HelperException thrown
//     */
//    public static Document sbdConfXML() throws HelperException {
//
//        final Document doc = XMLHelper.getDocument();
//
//        final Element sbdconf
//                = doc.createElement("streambase-configuration");
//
//        final Element global = doc.createElement("global");
//        final Element plugin = doc.createElement("plugin");
//        NamedNodeMap atts = plugin.getAttributes();
//        final Attr fileAttr = doc.createAttribute("file");
//        fileAttr.setValue("./nth_value");
//        atts.setNamedItem(fileAttr);
//        global.appendChild(plugin);
//
//        final Element server = doc.createElement("server");
//        final Element param = doc.createElement("param");
//        atts = param.getAttributes();
//        final Attr nameAttr = doc.createAttribute("name");
//        nameAttr.setValue("tcp-port");
//        final Attr valueAttr = doc.createAttribute("value");
//        valueAttr.setValue("10000");
//        atts.setNamedItem(nameAttr);
//        atts.setNamedItem(valueAttr);
//
//        server.appendChild(param);
//        
//        sbdconf.appendChild(global);
//        sbdconf.appendChild(server);
//        sbdconf.appendChild(buildCustomFunctions(doc));
//
//        doc.appendChild(sbdconf);
//
//        return doc;
//    }

    /**
     * Add an operator parameter.
     * @param opParameter operator parameter element
     */
    public void addOperatorParameter(
            final OperatorParameter opParameter) {
        opParameters.addOperatorParameter(opParameter);
    }

    /**
     * Add a new custom function.
     * @param customFunction custom function element
     */
    public void addCustomFunction(
            final CustomFunction customFunction) {
        custFunctions.addCustomFunction(customFunction);
    }

    /**
     * Add plugin.
     * @param plugin plugin
     */
    public void addPlugin(final Plugin plugin) {
        global.addPlugin(plugin);
    }

    /**
     * Add module search.
     * @param modSearch module search entry
     */
    public void addModuleSearch(final ModuleSearch modSearch) {
        global.addModuleSearch(modSearch);
    }

    /**
     * Add operator resource search.
     * @param opSearch operator resource search entry
     */
    public void addOperatorResourceSearch(
                    final OperatorResSearch opSearch) {
        global.addOperatorResourceSearch(opSearch);
    }
}
