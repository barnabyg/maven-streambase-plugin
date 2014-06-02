/**
 * 
 */
package com.blizzardtec.xmlfileworker.streambase;

import com.blizzardtec.helpers.HelperException;
import com.blizzardtec.xmlfileworker.AbstractXmlFileWorker;

/**
 * @author Barnaby Golden
 *
 */
public final class ConfReader extends AbstractXmlFileWorker {

    /**
     * Get lists of operator-parameters and custom-functions
     * from the loaded pom file.
     *
     * @return list of operator-parameter and a list of custom-function
     * embedded in a SbdConfList
     * @throws HelperException thrown
     */
    public ConfList getConfigurationLists()
                                throws HelperException {

        final ConfList confList = new ConfList();

        confList.setCustomFunctions(ConfHelper.getCustomFunctions(getDoc()));
        confList.setOpParameters(ConfHelper.getOperatorParameters(getDoc()));

        return confList;
    }
}
