/**
 * 
 */
package com.blizzardtec.xmlfileworker.maven.model;

import org.junit.Test;
import org.w3c.dom.Document;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.helpers.XMLHelper;

/**
 * @author Barnaby Golden
 *
 */
public final class DependencyTest {

    /**
     * Test the Dependency model object.
     *
     * @throws HelperException thrown
     */
    @Test(expected = HelperException.class)
    public void dependencyTest1() throws HelperException {

        final Document doc = XMLHelper.getDocument();

        final Dependency dep = new Dependency(null, "dog", "badger", "owl");
        dep.toXML(doc);
    }

    /**
     * Test the Dependency model object.
     *
     * @throws HelperException thrown
     */
    @Test(expected = HelperException.class)
    public void dependencyTest2() throws HelperException {

        final Document doc = XMLHelper.getDocument();

        final Dependency dep = new Dependency("blah", null, "trout", "duck");
        dep.toXML(doc);
    }
}
