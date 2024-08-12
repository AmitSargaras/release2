/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents a ISDA CSA Deal.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IISDACSADeal extends IDeal {

	/**
	 * Get value date
	 * 
	 * @return Date
	 */
	public Date getValueDate();

	/**
	 * Set value date
	 * 
	 * @param valueDate of type Date
	 */
	public void setValueDate(Date valueDate);

	/**
	 * Get NPV Amount
	 * 
	 * @return Amount
	 */
	public Amount getNPVAmount();

	/**
	 * Set NPV Amount
	 * 
	 * @param nPVAmount of type Amount
	 */
	public void setNPVAmount(Amount nPVAmount);

	/**
	 * Get NPV Base Amount
	 * 
	 * @return Amount
	 */
	public Amount getNPVBaseAmount();

	/**
	 * Set NPV Base Amount
	 * 
	 * @param nPVBaseAmount of type Amount
	 */
	public void setNPVBaseAmount(Amount nPVBaseAmount);

	/**
	 * Get near amount
	 * 
	 * @return Amount
	 */
	public Amount getNearAmount();

	/**
	 * Set near amount
	 * 
	 * @param nearAmount of type Amount
	 */
	public void setNearAmount(Amount nearAmount);

	/**
	 * Get far amount
	 * 
	 * @return Amount
	 */
	public Amount getFarAmount();

	/**
	 * Set far amount
	 * 
	 * @param farAmount of type Amount
	 */
	public void setFarAmount(Amount farAmount);

}
