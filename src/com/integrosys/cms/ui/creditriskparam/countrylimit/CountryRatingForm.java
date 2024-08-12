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
public class CountryRatingForm extends CommonForm implements Serializable {

	private String fromEvent;	
	
	private String[] countryRating;
    private String[] bankCapFundPercent;
    private String[] presetCountryLimitPercent;

	
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
	 * @return Returns the countryRating.
	 */
	public String[] getCountryRating() {
		return countryRating;
	}
	/**
	 * @param countryRating The countryRating to set.
	 */
	public void setCountryRating(String[] countryRating) {
		this.countryRating = countryRating;
	}
	
	/**
	 * @return Returns the percentage of banking group capital Fund.
	 */
	public String[] getBankCapFundPercent() {
		return bankCapFundPercent;
	}
	
	/**
	 * @param bankCapFundPercent The percentage of banking group capital Fund to set.
	 */
	public void setBankCapFundPercent(String[] bankCapFundPercent) {
		this.bankCapFundPercent = bankCapFundPercent;
	}
	
	/**
	 * @return Returns the percentage of preset country limit.
	 */
	public String[] getPresetCountryLimitPercent() {
		return presetCountryLimitPercent;
	}
	
	/**
	 * @param presetCountryLimitPercent The percentage of preset country limit.
	 */
	public void setPresetCountryLimitPercent(String[] presetCountryLimitPercent) {
		this.presetCountryLimitPercent = presetCountryLimitPercent;
	}
	
	
	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = 
		{
				{"CountryRatingForm", "com.integrosys.cms.ui.creditriskparam.countrylimit.CountryRatingMapper"},
		};
		return input;
	}
}
