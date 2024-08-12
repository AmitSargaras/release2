/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.currency.Amount;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Data model holds Country Limit.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBCountryLimit implements ICountryLimit {

	private long countryLimitID = ICMSConstant.LONG_INVALID_VALUE;
	private String countryCode;
	private String countryRatingCode;	
	private Amount countryLimitAmount;
	private long versionTime;
	private long groupID;
	private long refID = ICMSConstant.LONG_INVALID_VALUE;
	private String status;
    private String currencyCode;
	
	
	 /**
	     * Default Constructor.
	     */
    public OBCountryLimit() {
        super();
    }

    /**
	     * Construct the object from its interface.
	     *
	     * @param obj is of type ICountryLimit
	     */
    public OBCountryLimit (ICountryLimit obj) {       
		this();		
        AccessorUtil.copyValue (obj, this);
    }
	
	public long getCountryLimitID() {
		return this.countryLimitID;
	}
	
	public void setCountryLimitID(long countryLimitID) {
		this.countryLimitID = countryLimitID;		
	}
	
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCountryRatingCode() {
		return this.countryRatingCode;
	}
	
	public void setCountryRatingCode(String countryRatingCode) {
		this.countryRatingCode = countryRatingCode;
	}
			
	public Amount getCountryLimitAmount() {
		return this.countryLimitAmount;
	}
	
	public void setCountryLimitAmount(Amount countryLimitAmount) {
		this.countryLimitAmount = countryLimitAmount;
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

  	public long getRefID() {
		return this.refID;
	}
	
	public void setRefID(long refID) {
		this.refID = refID;
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
        else if (!(obj instanceof OBCountryLimit))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }
}
