/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase.model;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;
import com.blizzardtec.xmlfileworker.XmlEntry;

/**
 * @author Barnaby Golden
 *
 */
public final class CustomFunction implements XmlEntry {

    /**
     * Arg.
     */
    private static final String ARG = "arg";
    /**
     * Auto.
     */
    private static final String AUTO = "auto";
    /**
     * Return.
     */
    private static final String RETURN = "return";
    /**
     * Args.
     */
    private static final String ARGS = "args";
    /**
     * Name.
     */
    private static final String NAME = "name";
    /**
     * Type.
     */
    private static final String TYPE = "type";
    /**
     * Alias.
     */
    private static final String ALIAS = "alias";
    /**
     * Class.
     */
    private static final String CLASS = "class";
    /**
     * Language.
     */
    private static final String LANGUAGE = "language";
    /**
     * Name.
     */
    private transient String name;
    /**
     * Type.
     */
    private transient String type;
    /**
     * Alias.
     */
    private transient String alias = null;

    /**
     * Args.
     */
    private transient String[] args = null;
    /**
     * Class.
     */
    private transient String funcClass = null;
    /**
     * Language.
     */
    private transient String language = null;
    /**
     * Return type.
     */
    private transient String returnType = null;

    /**
     * No param constructor used by mojo code.
     */
    public CustomFunction() {
        super();
    }

    /**
     * Constructor - takes all required attributes in custom function entry.
     *
     * @param name name
     * @param type type
     */
    public CustomFunction(final String name, final String type) {

        this.name = name;
        this.type = type;
    }

    /**
     * Constructor that uses a custom-function XML node.
     *
     * @param node custom-function XML node
     */
    public CustomFunction(final Node node) {

        if (node != null) {

            parseNodes(node);
            parseAttributes(node);
        }
    }

    /**
     * Parse the child nodes of a custom-function node.
     *
     * @param node custom-function node
     */
    private void parseNodes(final Node node) {

        // first we try the child nodes
        final NodeList nodeList = node.getChildNodes();

        this.name = XMLHelper.getNodeTextFromList(nodeList, NAME);
        this.type = XMLHelper.getNodeTextFromList(nodeList, TYPE);
        this.alias = XMLHelper.getNodeTextFromList(nodeList, ALIAS);
        this.funcClass = XMLHelper.getNodeTextFromList(nodeList, CLASS);
        this.language = XMLHelper.getNodeTextFromList(nodeList, LANGUAGE);

        final Node returnNode =
            XMLHelper.getNodeFromList(nodeList, RETURN);

        if (returnNode != null) {
            final NodeList returnList = returnNode.getChildNodes();
            this.returnType =
                XMLHelper.getNodeTextFromList(returnList, TYPE);
            // if the node was not found, then the type must be in
            // as an attribute
            if (this.returnType == null) {
                final NamedNodeMap map = returnNode.getAttributes();
                for (int i = 0; i < map.getLength(); i++) {
                    if (TYPE.equals(map.item(i).getNodeName())) {
                        this.returnType = map.item(i).getTextContent();
                    }
                }
            }
        }

        final Node argsNode =
            XMLHelper.getNodeFromList(nodeList, ARGS);

        fillArgs(argsNode);
    }

    /**
     * Fill the list of args.
     *
     * @param argsNode args node
     */
    private void fillArgs(final Node argsNode) {
        if (argsNode != null) {
            // check to see if this is an auto args entry
            if (AUTO.equals(argsNode.getTextContent())) {
                args = new String[] {AUTO};
            } else {
                final NodeList argsList = argsNode.getChildNodes();

                final ArrayList<String> strings = new ArrayList<String>();
                for (int i = 0; i < argsList.getLength(); i++) {
                    if (ARG.equals(argsList.item(i).getNodeName())) {
                        final NamedNodeMap map =
                            argsList.item(i).getAttributes();
                        for (int j = 0; j < map.getLength(); j++) {
                            if ((TYPE.equals(map.item(j).getNodeName()))
                                 && (!map.item(j).getTextContent().isEmpty())) {
                                strings.add(map.item(j).getTextContent());
                                break;
                            }
                        }
                    }
                }
                args = strings.toArray(new String[strings.size()]);
            }
        }           
    }

    /**
     * Parse the child attributes of a custom-function node.
     *
     * @param node custom-function node
     */
    private void parseAttributes(final Node node) {

        // next we try the attributes
        final NamedNodeMap map = node.getAttributes();

        if (this.name == null) {
            this.name =
                XMLHelper.getAttributeTextFromList(map, NAME);
        }

        if (this.type == null) {
            this.type =
                XMLHelper.getAttributeTextFromList(map, TYPE);
        }

        if (this.alias == null) {
            this.alias =
                XMLHelper.getAttributeTextFromList(map, ALIAS);
        }

        if (this.funcClass == null) {
            this.funcClass =
                XMLHelper.getAttributeTextFromList(map, CLASS);
        }

        if (this.language == null) {
            this.language =
                XMLHelper.getAttributeTextFromList(map, LANGUAGE);
        }

        if ((this.args == null) || (this.args.length < 1)) {
            final String argStr =
                XMLHelper.getAttributeTextFromList(map, ARGS);
            if (argStr != null) {
                args = new String[] {AUTO};
            }
        }
    }

    /**
     * Configure additional values.
     * @param alias alias - can be null
     * @param args array of arguments - can be null
     * @param funcClass class - can be null
     * @param language language - can be null
     * @param returnType the return type - can be null
     */
    public void configure(final String alias,
                          final String[] args,
                          final String funcClass,
                          final String language,
                          final String returnType) {

        this.alias = alias;
        this.args = (String[]) args.clone();
        this.funcClass = funcClass;
        this.language = language;
        this.returnType = returnType;
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.streambase.StreambaseEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element custFunctEntry = doc.createElement("custom-function");

        XMLHelper.addAttribute(doc, custFunctEntry, NAME, name);
        XMLHelper.addAttribute(doc, custFunctEntry, TYPE, type);

        if (alias != null) {
            XMLHelper.addAttribute(doc, custFunctEntry, ALIAS, alias);
        }
        if (funcClass != null) {
            XMLHelper.addAttribute(doc, custFunctEntry, CLASS, funcClass);
        }
        if (language != null) {
            XMLHelper.addAttribute(doc, custFunctEntry, LANGUAGE, language);
        }

        if ((args != null) && (args.length > 0)) {
            // if the first argument is 'auto' this is a special case
            if (AUTO.equals(args[0])) {
                XMLHelper.addAttribute(doc, custFunctEntry, ARGS, args[0]);
            } else {
                // add the args element
                final Element argsElement = doc.createElement(ARGS);
                custFunctEntry.appendChild(argsElement);

                // add the arg elements in the args section
                Element argElement = null;
                for (int i = 0; i < args.length; i++) {
                    argElement = doc.createElement(ARG);
                    argElement.setTextContent(args[i]);
                    argsElement.appendChild(argElement);
                }
                // and finally add the return type element
                final Element returnElement = doc.createElement(RETURN);
                XMLHelper.addAttribute(doc, returnElement, TYPE, returnType);
                custFunctEntry.appendChild(returnElement);
            }
        }

        return custFunctEntry;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(final String alias) {
        this.alias = alias;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(final String[] args) {
        this.args = args.clone();
    }

    /**
     * @param funcClass the funcClass to set
     */
    public void setFuncClass(final String funcClass) {
        this.funcClass = funcClass;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(final String language) {
        this.language = language;
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturnType(final String returnType) {
        this.returnType = returnType;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @return the args
     */
    public String[] getArgs() {
        return args.clone();
    }

    /**
     * @return the funcClass
     */
    public String getFuncClass() {
        return funcClass;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return the returnType
     */
    public String getReturnType() {
        return returnType;
    }
}
