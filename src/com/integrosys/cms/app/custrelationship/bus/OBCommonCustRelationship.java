/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Data model holds common value for Customer Relationship and Shareholder.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBCommonCustRelationship implements ICommonCustRelationship
{
    private long custRelatnshipID = ICMSConstant.LONG_INVALID_VALUE;
    private long parentSubProfileID = ICMSConstant.LONG_INVALID_VALUE;
    private long subProfileID = ICMSConstant.LONG_INVALID_VALUE;
	private ICMSCustomer customerDetails;

    private Date lastUpdateDate;    
    private String lastUpdateUser;
	
    private long groupID;
    private long refID = ICMSConstant.LONG_INVALID_VALUE;
    private String status;
    private long versionTime;
	
    /**
	     * Default Constructor.
	     */
    public OBCommonCustRelationship() {
        super();
    }

    /**
	     * Construct the object from its interface.
	     *
	     * @param obj is of type ICommonCustRelationship
	     */
    public OBCommonCustRelationship (ICommonCustRelationship obj) {
        this();		
        AccessorUtil.copyValue (obj, this);

    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getCustRelationshipID
	    */
    public long getCustRelationshipID() {
        return this.custRelatnshipID;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setCustRelationshipID
	    */
    public void setCustRelationshipID(long custRelatnshipID) {
        this.custRelatnshipID = custRelatnshipID;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getParentSubProfileID
	    */
    public long getParentSubProfileID() {
        return this.parentSubProfileID;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setParentSubProfileID
	    */
    public void setParentSubProfileID(long parentSubProfileID) {
        this.parentSubProfileID = parentSubProfileID;
    }
	
	/**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getSubProfileID
	    */
    public long getSubProfileID() {
        return this.subProfileID;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setSubProfileID
	    */
    public void setSubProfileID(long subProfileID) {
        this.subProfileID = subProfileID;
    }
	
	/**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getCustomerDetails
	    */
    public ICMSCustomer getCustomerDetails() {
        return this.customerDetails;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setCustomerDetails
	    */
    public void setCustomerDetails(ICMSCustomer customerDetails) {
        this.customerDetails = customerDetails;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getLastUpdateDate
	    */
    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setLastUpdateDate
	    */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getLastUpdateUser
	    */
    public String getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setLastUpdateUser
	    */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getRefID
	    */
    public long getRefID() {
        return this.refID;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setRefID
	    */
    public void setRefID(long refID) {
        this.refID = refID;
    }

	/**
	* @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getGroupID
	*/
	public long getGroupID() {
		return this.groupID;
	}

	/**
	* @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setGroupID
	*/
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}
	
    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getStatus
	    */
    public String getStatus() {
        return this.status;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setStatus
	    */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#getVersionTime
	    */
    public long getVersionTime() {
        return this.versionTime;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICommonCustRelationship#setVersionTime
	    */
    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }
    
    /**
	     * Return a String representation of this object.
	     *
	     * @return String
	     */
    public String toString() {
        return AccessorUtil.printMethodValue (this);
    }

    /**
	     * Test for equality.
	     *
	     * @param obj is of type Object
	     * @return boolean
	     */
    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBCommonCustRelationship))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }

}