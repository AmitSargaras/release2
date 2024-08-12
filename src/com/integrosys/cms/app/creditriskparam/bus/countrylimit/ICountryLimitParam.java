/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import java.io.Serializable;

/**
 * Interface of data model holds Country Limit Param.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface ICountryLimitParam extends Serializable {

	/**
	 * Get the array of country limit
	 * @return ICountryLimit[]
	 */
	public ICountryLimit[] getCountryLimitList();
	
	/**
	 * Setting the array of country limit
	 * @param countryLimitList is array of type ICountryLimit
	 */
	public void setCountryLimitList(ICountryLimit[] countryLimitList);
				
    /**
	 * Get the array of country rating
	 * @return ICountryRating[]
	 */
    public ICountryRating[] getCountryRatingList();
    
    /**
	 * Setting the array of country rating
	 * @param countryRatingList is array of type ICountryRating
	 */
     public void setCountryRatingList(ICountryRating[] countryRatingList);
	
		
	 /**
	     * Get groupID
	     *
	     * @return long
	     */
	 public long getGroupID();

	 /**
	     * Set the groupID.
	     *
	     * @param groupID is of type long
	     */
	 public void setGroupID(long groupID); 
    
    
}
