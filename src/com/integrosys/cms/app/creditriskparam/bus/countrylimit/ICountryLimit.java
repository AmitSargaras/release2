/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import java.io.Serializable;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.base.businfra.currency.Amount;

/**
 * Interface of data model holds Country Limit.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface ICountryLimit extends Serializable, IValueObject {

	/**
	 * Get the country limit ID
	 * @return long
	 */
	public long getCountryLimitID();
	
	/**
	 * Setting the country limit id
	 * @param id is of type long
	 */
	public void setCountryLimitID(long id);
			
	/**
	 * Get country code
	 * @return String
	 */
	public String getCountryCode();
	
	/**
	 * Set country code
	* @param countryCode is of type String
	 */
	public void setCountryCode(String countryCode);
	
    /**
	 * Get country rating code
	 * @return String
	 */
	public String getCountryRatingCode();
	
	/**
	 * Set country rating code
	 * @param countryRatingCode is of type String
	 */
	public void setCountryRatingCode(String countryRatingCode);	
	
	/**
	 * Get  country imit amount
	 * @return Amount
	 */
	 public Amount getCountryLimitAmount();
	 
	 /**
	 * Set country imit amount
	 * @param countryLimitAmount is of type Amount
	 */
	 public void setCountryLimitAmount(Amount countryLimitAmount);
	    
    /**
	     * Get the record status
	     * @return String
	     */
	public String getStatus();

	/**
	 * Set the record status
	* @param status is of type String
	 */
	public void setStatus(String status);
	
	/**
	     * Get groupID
	     * @return long
	     */
	 public long getGroupID();

	 /**
	     * Set the groupID.
	     * @param groupID is of type long
	     */
	 public void setGroupID(long groupID);
	  
	  /**
	     * Get reference ID
	     * @return long
	     */
	 public long getRefID();

	 /**
	     * Set the reference ID
	     * @param refID is of type long
	     */
	 public void setRefID(long refID);
    
    /**
	     * Get the version timestamp
	     * @return long
	     */
	public long getVersionTime();

	/**
	 * Set the version timestamp
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);
}
