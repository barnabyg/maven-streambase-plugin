/**
 *
 */
package com.blizzardtec.xmlfileworker;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;
import com.blizzardtec.xmlfileworker.XmlEntry.EntryType;
import com.blizzardtec.xmlfileworker.maven.model.Generic;

/**
 * @author Barnaby Golden
 *
 */
public final class XmlEntryTest {

    /**
     * Test the XmlEntry interface.
     *
     * @throws HelperException thrown
     */
    @Test
    public void xmlEntryTest() throws HelperException {

        EntryType.valueOf(EntryType.PLUGIN.toString());
        EntryType.valueOf(EntryType.CUSTOM.toString());
        EntryType.valueOf(EntryType.MODULE.toString());
        EntryType.valueOf(EntryType.OPPARAM.toString());
        EntryType.valueOf(EntryType.OPRES.toString());

        final XmlEntry generic = new Generic("name", "value");

        final Document doc = XMLHelper.getDocument();

        final Element element = generic.toXML(doc);

        assertNotNull("element was null", element);
    }
}
