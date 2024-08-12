/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents a deal valuation.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IDealValuation extends Serializable {
	/**
	 * Get deal valuation id.
	 * 
	 * @return long
	 */
	public long getDealValuationID();

	/**
	 * Set deal valuation id.
	 * 
	 * @param value of type long
	 */
	public void setDealValuationID(long value);

	/**
	 * Get CMS deal ID.
	 * 
	 * @return long
	 */
	public long getCMSDealID();

	/**
	 * Set CMS deal ID.
	 * 
	 * @param value of type long
	 */
	public void setCMSDealID(long value);

	/**
	 * Get market value
	 * 
	 * @return Amount
	 */
	public Amount getMarketValue();

	/**
	 * Set market value
	 * 
	 * @param value of type Amount
	 */
	public void setMarketValue(Amount value);

	/**
	 * Get group id.
	 * 
	 * @return long
	 */
	public long getGroupID();

	/**
	 * Set group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID);

	/**
	 * Get status
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set status
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get the version time.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Set the version time.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);
}
