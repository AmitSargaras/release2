/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents a threshold rating.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IThresholdRating extends IExternalRating {

	/**
	 * Get Threshold Rating ID
	 * 
	 * @return long
	 */
	public long getThresholdRatingID();

	/**
	 * Set Threshold Rating ID
	 * 
	 * @param value is of type long
	 */
	public void setThresholdRatingID(long value);

	/**
	 * Get Trading Agreement ID
	 * 
	 * @return long
	 */
	public long getAgreementID();

	/**
	 * Set Trading Agreement ID
	 * 
	 * @param value is of type long
	 */
	public void setAgreementID(long value);

	/**
	 * Get Threshold Amount
	 * 
	 * @return Amount
	 */
	public Amount getThresholdAmount();

	/**
	 * Set Threshold Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setThresholdAmount(Amount value);

	/**
	 * Get the status of this threshold rating.
	 * 
	 * @return long
	 */
	public String getStatus();

	/**
	 * Set the status of this threshold rating.
	 * 
	 * @param value is of type String
	 */
	public void setStatus(String value);

	/**
	 * Get the version time of this threshold rating.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Set the version time of this threshold rating.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);

}