/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents common data for a Deal.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IDeal extends Serializable {
	/**
	 * Get CMS deal id.
	 * 
	 * @return long
	 */
	public long getCMSDealID();

	/**
	 * Set CMS deal id.
	 * 
	 * @param value of type long
	 */
	public void setCMSDealID(long value);

	/**
	 * Get agreement ID.
	 * 
	 * @return long
	 */
	public long getAgreementID();

	/**
	 * Set agreement ID.
	 * 
	 * @param value of type long
	 */
	public void setAgreementID(long value);

	/**
	 * Get deal ID.
	 * 
	 * @return String
	 */
	public String getDealID();

	/**
	 * Set deal ID.
	 * 
	 * @param dealId of type String
	 */
	public void setDealID(String dealId);

	/**
	 * Get product type
	 * 
	 * @return String
	 */
	public String getProductType();

	/**
	 * Set product type.
	 * 
	 * @param productType of type String
	 */
	public void setProductType(String productType);

	/**
	 * Get deal amount
	 * 
	 * @return Amount
	 */
	public Amount getDealAmount();

	/**
	 * Set deal amount
	 * 
	 * @param dealAmt of type Amount
	 */
	public void setDealAmount(Amount dealAmt);

	/**
	 * Get notional amount
	 * 
	 * @return Amount
	 */
	public Amount getNotionalAmount();

	/**
	 * Set notional amount
	 * 
	 * @param notionalAmount of type Amount
	 */
	public void setNotionalAmount(Amount notionalAmount);

	/**
	 * Get trade date
	 * 
	 * @return Date
	 */
	public Date getTradeDate();

	/**
	 * Set trade date
	 * 
	 * @param tradeDate of type Date
	 */
	public void setTradeDate(Date tradeDate);

	/**
	 * Get maturity date
	 * 
	 * @return Date
	 */
	public Date getMaturityDate();

	/**
	 * Set maturity date
	 * 
	 * @param maturityDate of type Date
	 */
	public void setMaturityDate(Date maturityDate);

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
