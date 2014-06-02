/**
 * 
 */
package com.blizzardtec.xmlfileworker;

/**
 * Abstract base class for classes modifying XML configuration
 * files.
 *
 * @author Barnaby Golden
 *
 */
public abstract class AbstractXmlFileModifier
                  extends AbstractXmlFileWorker implements XmlFileModifier {

    /**
     * Flag to indicate if the file has been modified.
     */
    private boolean modified = false;

    /**
     * @return the modified
     */
    public final boolean isModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public final void setModified(final boolean modified) {
        this.modified = modified;
    }
}
