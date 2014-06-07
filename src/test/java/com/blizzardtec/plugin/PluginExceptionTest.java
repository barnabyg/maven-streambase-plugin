/**
 *
 */
package com.blizzardtec.plugin;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class PluginExceptionTest {

    /**
     * Test the creation of a plugin exception.
     */
    @Test
    public void pluginExceptionTest() {
        final PluginException pee =
            new PluginException(new IOException("hello"));
        assertNotNull("PluginException was null", pee);

        final PluginException pee2 = new PluginException("dog");
        assertNotNull("Second PluginException was null", pee2);
    }
}
