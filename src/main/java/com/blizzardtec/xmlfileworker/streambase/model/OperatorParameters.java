/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.XmlEntry;

/**
 * @author Barnaby Golden
 *
 */
public final class OperatorParameters implements XmlEntry {

    /**
     * List of operator parameters.
     */
    private final transient List<OperatorParameter> opParameters;

    /**
     * Constructor.
     */
    public OperatorParameters() {
        opParameters = new ArrayList<OperatorParameter>();
    }

    /* (non-Javadoc)
     * @see com.blizzardtec.streambase.StreambaseEntry#toXML(
     * org.w3c.dom.Document)
     */
    @Override
    public Element toXML(final Document doc) throws HelperException {

        final Element opParamsElement =
                doc.createElement("operator-parameters");

        final Iterator<OperatorParameter> iterator =
                                                opParameters.iterator();
        while (iterator.hasNext()) {
            opParamsElement.appendChild(iterator.next().toXML(doc));
        }

        return opParamsElement;
    }

    /**
     * Add an operator parameter.
     * @param opParameter operator parameter element
     */
    public void addOperatorParameter(
            final OperatorParameter opParameter) {
        opParameters.add(opParameter);
    }
}
