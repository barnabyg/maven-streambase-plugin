/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.AbstractXmlFileBuilder;

/**
 * Builds a template Streambase .sbapp file.
 *
 * @author Barnaby Golden
 *
 */
@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
public final class AppBuilder extends AbstractXmlFileBuilder {

    /**
     * Build a basic .sbapp file.
     * @throws HelperException thrown
     */
    public void generate() throws HelperException {

        initialiseDoc();

        final Element modify
                = getDoc().createElement("modify");

        NamedNodeMap atts = modify.getAttributes();
        final Attr version = getDoc().createAttribute("version");
        version.setValue("6.6.5.0");
        atts.setNamedItem(version);

        final Element addEl = getDoc().createElement("add");
        atts = addEl.getAttributes();
        final Attr generator = getDoc().createAttribute("generator");
        generator.setValue("gui");
        atts.setNamedItem(generator);

        modify.appendChild(addEl);

        getDoc().appendChild(modify);
    }
}
