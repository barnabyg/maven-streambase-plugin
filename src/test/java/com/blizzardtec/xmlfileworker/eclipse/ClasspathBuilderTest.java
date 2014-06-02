/**
 * 
 */
package com.blizzardtec.xmlfileworker.eclipse;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.w3c.dom.Document;

import com.blizzardtec.helpers.HelperException;

/**
 * @author Barnaby Golden
 *
 */
public final class ClasspathBuilderTest {

    /**
     * Test the generation of .classpath XML.
     * @throws HelperException thrown
     */
    @Test
    public void buildClasspathXMLTest() throws HelperException {
        final ClasspathBuilder builder = new ClasspathBuilder();
        builder.generate();
        
        final Document doc = builder.getDoc();

        assertNotNull("Document was null", doc);
        assertNotNull("First child node of document was null",
                                            doc.getFirstChild());
    }
}
