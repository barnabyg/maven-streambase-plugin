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
public final class ProjectBuilderTest {

    /**
     * Test the generation of .project XML.
     * @throws HelperException thrown
     */
    @Test
    public void buildProjectXMLTest() throws HelperException {
        final ProjectBuilder builder = new ProjectBuilder();
        builder.setProjectName("myProj");
        builder.generate();

        builder.generate();

        final Document doc = builder.getDoc();

        assertNotNull("Document was null", doc);
        assertNotNull("First child node of document was null",
                                            doc.getFirstChild());
    }
}
