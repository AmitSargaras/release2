/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import java.io.Serializable;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Interface of data model holds Country Rating.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface ICountryRating extends Serializable, IValueObject {

	/**
	 * Get the country rating ID
	 * @return long
	 */
	public long getCountryRatingID();
	
	/**
	 * Setting the country rating ID
	 * @param id is of type long
	 */
	public void setCountryRatingID(long id);
	
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
	 * Get percentage of banking group capital fund
	 * @return Double
	 */
	public Double getBankCapFundPercentage();
	
	/**
	 * Set percentage of banking group capital fund
	 * @param bankCapFundPercentage is of type Double
	 */
	public void setBankCapFundPercentage(Double bankCapFundPercentage);
	
    /**
	 * Get percentage of preset country limit
	 * @return Double
	 */
    public Double getPresetCtryLimitPercentage();
    
    /**
	 * Set percentage of preset country limit
	 * @param presetCtryLimitPercentage is of type Double
	 */
     public void setPresetCtryLimitPercentage(Double presetCtryLimitPercentage);
	
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
