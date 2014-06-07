/**
 *
 */
package com.blizzardtec.testbase;

import java.io.File;

/**
 * @author Barnaby Golden
 *
 */
public abstract class AbstractTest {

    /**
     * @return the baseDir
     */
    public static final String getBaseDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Resource folder location.
     * @return resource folder path
     */
    public static final String getResourceDir() {
        return getBaseDir()
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources";
    }
}
