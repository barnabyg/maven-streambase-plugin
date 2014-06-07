/**
 *
 */
package com.blizzardtec.xmlfileworker.streambase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.blizzardtec.xmlfileworker.streambase.model.CustomFunction;
import com.blizzardtec.xmlfileworker.streambase.model.ModuleSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorResSearch;
import com.blizzardtec.xmlfileworker.streambase.model.OperatorParameter;
import com.blizzardtec.xmlfileworker.streambase.model.Plugin;

/**
 * Contains lists of entries to add to an sbd.sbconf file.
 *
 * @author Barnaby Golden
 *
 */
public final class ConfList {

    /**
     * plugin entries.
     * @parameter
     */
    private List<Plugin> sbPlugins;
    /**
     * operator-parameter entries.
     * @parameter
     */
    private List<OperatorParameter> opParameters;
    /**
     * module-search entries.
     * @parameter
     */
    private List<ModuleSearch> moduleSearchPaths;
    /**
     * operator-resource-search entries.
     * @parameter
     */
    private List<OperatorResSearch> opResSearchPaths;
    /**
     * custom-function entries.
     */
    private List<CustomFunction> customFunctions;

    /**
     * Constructor.
     */
    public ConfList() {
        this.sbPlugins = new ArrayList<Plugin>();
        this.opParameters = new ArrayList<OperatorParameter>();
        this.moduleSearchPaths = new ArrayList<ModuleSearch>();
        this.opResSearchPaths = new ArrayList<OperatorResSearch>();
        this.customFunctions = new ArrayList<CustomFunction>();
    }

    /**
     * Add a set of lists to this set of lists.
     *
     * @param addList the lists to add
     */
    public void add(final ConfList addList) {

        if (addList != null) {
            addCustFunc(addList.getCustomFunctions());
            addOpRes(addList.getOpResSearchPaths());
            addMod(addList.getModuleSearchPaths());
            addOpParam(addList.getOpParameters());
            addPlugins(addList.getSbPlugins());
        }
    }

    /**
     * Add plugins list.
     *
     * @param list plugins list
     */
    private void addPlugins(final List<Plugin> list) {

        if (list != null) {
            if (this.sbPlugins == null) {
                this.sbPlugins = new ArrayList<Plugin>();
            }

            final Iterator<Plugin> iterator = list.iterator();

            while (iterator.hasNext()) {
                this.sbPlugins.add(iterator.next());
            }
        }
    }

    /**
     * Add op param list.
     *
     * @param list op param list
     */
    private void addOpParam(final List<OperatorParameter> list) {

        if (list != null) {
            if (this.opParameters == null) {
                this.opParameters = new ArrayList<OperatorParameter>();
            }

            final Iterator<OperatorParameter> iterator = list.iterator();

            while (iterator.hasNext()) {
                this.opParameters.add(iterator.next());
            }
        }
    }

    /**
     * Add module search list.
     *
     * @param list mod search list
     */
    private void addMod(final List<ModuleSearch> list) {

        if (list != null) {
            if (this.moduleSearchPaths == null) {
                this.moduleSearchPaths = new ArrayList<ModuleSearch>();
            }

            final Iterator<ModuleSearch> iterator = list.iterator();

            while (iterator.hasNext()) {
                this.moduleSearchPaths.add(iterator.next());
            }
        }
    }

    /**
     * Add operator resource search list.
     *
     * @param list op res list
     */
    private void addOpRes(final List<OperatorResSearch> list) {

        if (list != null) {
            if (this.opResSearchPaths == null) {
                this.opResSearchPaths = new ArrayList<OperatorResSearch>();
            }

            final Iterator<OperatorResSearch> iterator = list.iterator();

            while (iterator.hasNext()) {
                this.opResSearchPaths.add(iterator.next());
            }
        }
    }

    /**
     * Add custom-function list.
     *
     * @param list custom-function list
     */
    private void addCustFunc(final List<CustomFunction> list) {

        if (list != null) {
            if (this.customFunctions == null) {
                this.customFunctions = new ArrayList<CustomFunction>();
            }

            final Iterator<CustomFunction> iterator = list.iterator();

            while (iterator.hasNext()) {
                this.customFunctions.add(iterator.next());
            }
        }
    }

    /**
     * @return the sbPlugins
     */
    public List<Plugin> getSbPlugins() {
        return sbPlugins;
    }
    /**
     * @param sbPlugins the sbPlugins to set
     */
    public void setSbPlugins(final List<Plugin> sbPlugins) {
        this.sbPlugins = sbPlugins;
    }
    /**
     * @return the opParameters
     */
    public List<OperatorParameter> getOpParameters() {
        return opParameters;
    }
    /**
     * @param opParameters the opParameters to set
     */
    public void setOpParameters(final List<OperatorParameter> opParameters) {
        this.opParameters = opParameters;
    }
    /**
     * @return the moduleSearchPaths
     */
    public List<ModuleSearch> getModuleSearchPaths() {
        return moduleSearchPaths;
    }
    /**
     * @param moduleSearchPaths the moduleSearchPaths to set
     */
    public void setModuleSearchPaths(
            final List<ModuleSearch> moduleSearchPaths) {
        this.moduleSearchPaths = moduleSearchPaths;
    }
    /**
     * @return the opResSearchPaths
     */
    public List<OperatorResSearch> getOpResSearchPaths() {
        return opResSearchPaths;
    }
    /**
     * @param opResSearchPaths the opResSearchPaths to set
     */
    public void setOpResSearchPaths(
            final List<OperatorResSearch> opResSearchPaths) {
        this.opResSearchPaths = opResSearchPaths;
    }
    /**
     * @return the customFunctions
     */
    public List<CustomFunction> getCustomFunctions() {
        return customFunctions;
    }
    /**
     * @param customFunctions the customFunctions to set
     */
    public void setCustomFunctions(
            final List<CustomFunction> customFunctions) {
        this.customFunctions = customFunctions;
    }
}
