package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 7, 2008
 * Time: 11:10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBHostBranchRCDependency implements IDependency {

    private static final String[] MATCHING_PROPERTIES = new String[] { "branchNumber" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "branchNumber" };

    private String branchNumber;
    private String rcCode;


    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getRcCode() {
        return rcCode;
    }

    public void setRcCode(String rcCode) {
        this.rcCode = rcCode;
    }


    /****** Methods from IDependency ******/
    public String[] getMatchingProperties() {
        return MATCHING_PROPERTIES;
    }

    public String[] getIgnoreProperties() {
        return IGNORED_PROPERTIES;
    }

    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
        trimStringAttributes();
    }

    public void updatePropertiesForDelete(IParameterProperty paramProperty) {
        trimStringAttributes();
    }

    private void trimStringAttributes() {
        if(getBranchNumber() != null){
            setBranchNumber(getBranchNumber().trim());
        }
        if(getRcCode() != null){
            setRcCode(getRcCode().trim());
        }
    }

}
