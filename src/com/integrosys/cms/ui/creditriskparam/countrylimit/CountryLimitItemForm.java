/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryLimitItemForm extends CommonForm implements Serializable {

	private String fromEvent;	
	private String indexID;
	
	private String country;
    private String countryRating;	
	
	/**
	 * @return Returns the fromEvent.
	 */
	public String getFromEvent() {
		return fromEvent;
	}
	/**
	 * @param fromEvent The fromEvent to set.
	 */
	public void setFromEvent(String fromEvent) {
		this.fromEvent = fromEvent;
	}
	
	/**
	 * @return Returns the indexID.
	 */
	public String getIndexID() {
		return indexID;
	}
	/**
	 * @param indexID The indexID to set.
	 */
	public void setIndexID(String indexID) {
		this.indexID = indexID;
	}
		
	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return Returns the countryRating.
	 */
	public String getCountryRating() {
		return countryRating;
	}
	/**
	 * @param countryRating The countryRating to set.
	 */
	public void setCountryRating(String countryRating) {
		this.countryRating = countryRating;
	}	
	
	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = 
		{
				{"CountryLimitItemForm", "com.integrosys.cms.ui.creditriskparam.countrylimit.CountryLimitItemMapper"},
		};
		return input;
	}
}
