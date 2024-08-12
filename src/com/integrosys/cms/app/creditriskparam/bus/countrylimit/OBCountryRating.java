/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Data model holds Country Rating.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBCountryRating implements ICountryRating {

	private long countryRatingID = ICMSConstant.LONG_INVALID_VALUE;
	private String countryRatingCode;
	private Double bankCapFundPercentage;
	private Double presetCtryLimitPercentage;
	private long versionTime;
	private long groupID;		
	
	 /**
	     * Default Constructor.
	     */
    public OBCountryRating() {
        super();
    }

    /**
	     * Construct the object from its interface.
	     *
	     * @param obj is of type ICountryRating
	     */
    public OBCountryRating (ICountryRating obj) {       
		this();		
        AccessorUtil.copyValue (obj, this);
    }
	
	public long getCountryRatingID() {
		return this.countryRatingID;
	}
	
	public void setCountryRatingID(long countryRatingID) {
		this.countryRatingID = countryRatingID;
		
	}
	
	public String getCountryRatingCode() {
		return this.countryRatingCode;
	}
	
	public void setCountryRatingCode(String countryRatingCode) {
		this.countryRatingCode = countryRatingCode;
	}
	
	public Double getBankCapFundPercentage() {
		return this.bankCapFundPercentage;
	}

	public void setBankCapFundPercentage(Double bankCapFundPercentage) {
		this.bankCapFundPercentage = bankCapFundPercentage;
	}
	
	public Double getPresetCtryLimitPercentage() {
		return this.presetCtryLimitPercentage;
	}
	
	public void setPresetCtryLimitPercentage(Double presetCtryLimitPercentage) {
		this.presetCtryLimitPercentage = presetCtryLimitPercentage;
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
        else if (!(obj instanceof OBCountryRating))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }
}
