/**
 * 
 */
package com.blizzardtec.xmlfileworker.maven;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.maven.model.Configuration;
import com.blizzardtec.xmlfileworker.maven.model.Dependency;
import com.blizzardtec.xmlfileworker.maven.model.Generic;
import com.blizzardtec.xmlfileworker.maven.model.Plugin;

/**
 * @author Barnaby Golden
 *
 */
public final class PomBuilderTest {

    /**
     * Test the generation of POM XML.
     * @throws HelperException thrown
     */
    @Test
    public void generateTest() throws HelperException {
        final PomBuilder mavenPom = new PomBuilder();

        mavenPom.setGroupId("myGroup");
        mavenPom.setArtifactId("myArtifact");
        mavenPom.setVersion("0.0.1-SNAPSHOT");

        final Dependency dependency =
            new Dependency("org.apache.maven",
                                    "maven-plugin-api", "2.0", "compile");

        mavenPom.addDependency(dependency);

        final Plugin plugin =
            new Plugin("org.apache.maven.plugins",
                                           "maven-pmd-plugin", "2.5");

        final Configuration configuration =
                                    new Configuration();

        final Generic simple = new Generic("name", "value");

        configuration.addEntry(simple);

        plugin.addConfiguration(configuration);

        mavenPom.addPlugin(plugin);

        // generate the XML document from the model
        mavenPom.generate();

        final Document doc = mavenPom.getDoc();

        assertNotNull("POM XML document was null", doc);

        final Node projectNode = doc.getFirstChild();
        NodeList nodeList = projectNode.getChildNodes();

        Node node = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if ("dependencies".equals(node.getNodeName())) {
                break;
            }
        }

        boolean flag = false;
        nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if ("dependency".equals(node.getNodeName())) {
                flag = true;
            }
        }

        assertTrue("Generated XML did not contain added dependency", flag);
    }
}
