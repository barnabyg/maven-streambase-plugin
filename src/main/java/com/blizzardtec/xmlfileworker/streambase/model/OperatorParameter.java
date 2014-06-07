/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;
import com.blizzardtec.xmlfileworker.XmlEntry;

/**
 * @author Barnaby Golden
 *
 */
public final class OperatorParameter implements XmlEntry {

    /**
     * Enciphered.
     */
    private static final String ENCIPHERED = "enciphered";
    /**
     * True.
     */
    private static final String TRUE = "true";
    /**
     * Name.
     */
    private static final String NAME = "name";
    /**
     * Value.
     */
    private static final String VALUE = "value";
    /**
     * Name attribute.
     */
    private transient String name;
    /**
     * Value attribute.
     */
    private transient String value;
    /**
     * Enciphered flag.
     */
    private transient boolean enciphered = false;

    /**
     * No param constructor used by mojo code.
     */
    public OperatorParameter() {
        super();
    }

    /**
     * Constructor.
     * @param name name attribute
     * @param value value attribute
     */
    public OperatorParameter(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Construct the object using an operator-parameter XML node.
     *
     * @param node operator-parameter XML node
     */
    public OperatorParameter(final Node node) {

        // next we try the attributes
        final NamedNodeMap map = node.getAttributes();

        this.name =
                XMLHelper.getAttributeTextFromList(map, NAME);

        this.value =
                XMLHelper.getAttributeTextFromList(map, VALUE);

        final String encAtt =
            XMLHelper.getAttributeTextFromList(map, ENCIPHERED);

        if ((encAtt != null) && (TRUE.equals(encAtt))) {
            this.enciphered = true;
        }
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.streambase.StreambaseEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element opParamElement = doc.createElement("operator-parameter");

        XMLHelper.addAttribute(doc, opParamElement, NAME, name);
        XMLHelper.addAttribute(doc, opParamElement, VALUE, value);

        if (enciphered) {
            XMLHelper.addAttribute(doc, opParamElement, ENCIPHERED, TRUE);
        }
        return opParamElement;
    }

    /**
     * Set name.
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Set value.
     * @param value value
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * @return the enciphered
     */
    public boolean isEnciphered() {
        return enciphered;
    }

    /**
     * @param enciphered the enciphered to set
     */
    public void setEnciphered(final boolean enciphered) {
        this.enciphered = enciphered;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
