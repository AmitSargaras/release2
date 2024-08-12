/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.entitylimit;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Data model holds Entity Limit.
 *
 * @author  $Author: skchai $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBEntityLimit implements IEntityLimit {

	private static final long serialVersionUID = 1L;
	
	private String customerName;
	private String lEReference;
	private String custIDSource;
	private long entityLimitID = ICMSConstant.LONG_INVALID_VALUE;
	private Amount limitAmount = null;
	private Date limitLastReviewDate;
	private long versionTime;
	private long groupID;
	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;
	private long customerID = ICMSConstant.LONG_INVALID_VALUE;
	private String status;
    private String limitCurrency;
	
	 /**
	     * Default Constructor.
	     */
    public OBEntityLimit() {
        super();
    }

    /**
	     * Construct the object from its interface.
	     *
	     * @param obj is of type IEntity Limit
	     */
    public OBEntityLimit (IEntityLimit obj) {       
		this();		
        AccessorUtil.copyValue (obj, this);
    }
	
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getLEReference() {
		return this.lEReference;
	}
	
	public void setLEReference(String lEReference) {
		this.lEReference = lEReference;
	}
	
	public String getCustIDSource() {
		return this.custIDSource;
	}
	
	public void setCustIDSource(String custIDSource) {
		this.custIDSource = custIDSource;
	}
	
	public long getCustomerID() {
		return this.customerID;
	}
	
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public long getEntityLimitID() {
		return this.entityLimitID;
	}
	
	public void setEntityLimitID(long entityLimitID) {
		this.entityLimitID = entityLimitID;
		
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;		
	}
	
	public long getGroupID() {
		return this.groupID;
	}
	
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}	
  	
	public long getCommonRef() {
		return this.commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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
        else if (!(obj instanceof OBEntityLimit))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }

	/**
	 * @return the limitAmount
	 */
	public Amount getLimitAmount() {
		return limitAmount;
	}

	/**
	 * @param limitAmount the limitAmount to set
	 */
	public void setLimitAmount(Amount limitAmount) {
		this.limitAmount = limitAmount;
	}

	/**
	 * @return the limitLastReviewDate
	 */
	public Date getLimitLastReviewDate() {
		return limitLastReviewDate;
	}

	/**
	 * @param limitLastReviewDate the limitLastReviewDate to set
	 */
	public void setLimitLastReviewDate(Date limitLastReviewDate) {
		this.limitLastReviewDate = limitLastReviewDate;
	}

    public String getLimitCurrency() {
        return limitCurrency;
    }

    public void setLimitCurrency(String limitCurrency) {
        this.limitCurrency = limitCurrency;
    }
}
