package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 7, 2008
 * Time: 6:07:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBDynamicProperty implements IDynamicProperty {

    private long propertyID = ICMSConstant.LONG_INVALID_VALUE;          //PK

    private long propertySetupID = ICMSConstant.LONG_INVALID_VALUE;     //FK

    private String propertyCategory = null;

    private String propertyValue = null;

    private long referenceID = ICMSConstant.LONG_INVALID_VALUE;         //link between actual & staging

    private String status = ICMSConstant.STATE_ACTIVE;


    /**
     * Gets the property id
     * @return property id
     */
    public long getPropertyID() {
        return propertyID;
    }

    /**
     * Sets the property id
     * @param propertyID - property id
     */
    public void setPropertyID(long propertyID) {
        this.propertyID = propertyID;
    }


    /**
     * Gets the property setup id
     * @return property setup id
     * @see OBDynamicPropertySetup
     */
    public long getPropertySetupID() {
        return propertySetupID;
    }

    /**
     * Sets the property setup id
     * @param propertySetupID - property setup id
     * @see OBDynamicPropertySetup
     */
    public void setPropertySetupID(long propertySetupID) {
        this.propertySetupID = propertySetupID;
    }


    /**
     * Gets the property common code category
     * @return property common code category
     */
    public String getPropertyCategory() {
        return propertyCategory;
    }

    /**
     * Sets the property common code category
     * @param propertyCategory - property common code category 
     */
    public void setPropertyCategory(String propertyCategory) {
        this.propertyCategory = propertyCategory;
    }

    /**
     * Gets the value for this property
     * @return value for property
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * Sets the value for this property
     * @param propertyValue - value for property
     */
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }


    /**
     * Gets the reference id for linking between actual and staging objects.
     * @return reference id
     */
    public long getReferenceID() {
        return referenceID;
    }

    /**
     * Sets the reference id, for linking between actual and staging objects.
     * @param referenceID - reference id
     */
    public void setReferenceID(long referenceID) {
        this.referenceID = referenceID;
    }

    /**
     * Gets the status of this dynamic property
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this dynamic property
     * @param status - status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
      * Prints a String representation of this object
      * @return String
      */
     public String toString() {
         return AccessorUtil.printMethodValue(this);
     }

}
