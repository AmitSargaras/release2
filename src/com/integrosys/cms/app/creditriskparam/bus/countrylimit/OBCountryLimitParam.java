/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Data model holds Country Limit Param which contain array of country limit and array of country rating.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBCountryLimitParam implements ICountryLimitParam {

	private ICountryLimit[] countryLimitList;
	private ICountryRating[] countryRatingList;
		
	private long groupID;
	
	
	 /**
	     * Default Constructor.
	     */
    public OBCountryLimitParam() {
        super();
    }

    /**
	     * Construct the object from its interface.
	     *
	     * @param obj is of type ICountryLimitParam
	     */
    public OBCountryLimitParam (ICountryLimitParam obj) {       
		this();		
        AccessorUtil.copyValue (obj, this);
    }
	
	public ICountryLimit[] getCountryLimitList() {
		return this.countryLimitList;
	}
	
	public void setCountryLimitList(ICountryLimit[] countryLimitList) {
		this.countryLimitList = countryLimitList;
		
	}
	
	public ICountryRating[] getCountryRatingList() {
		return this.countryRatingList;
	}

	public void setCountryRatingList(ICountryRating[] countryRatingList) {
		this.countryRatingList = countryRatingList;
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
        else if (!(obj instanceof OBCountryLimitParam))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }
}
