/**
 * 
 */
package com.blizzardtec.plugin;

/**
 * Wrapper for exceptions occurring directly in the plugin classes.
 * @author Barnaby Golden
 *
 */
public final class PluginException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -4469765680350064383L;

    /**
     * Constructor takes an exception argument.
     * @param exception the exception to wrap
     */
    public PluginException(final Exception exception) {
        super(exception);
    }

    /**
     * Constructor takes string message.
     * @param message error message
     */
    public PluginException(final String message) {
        super(message);
    }
}
