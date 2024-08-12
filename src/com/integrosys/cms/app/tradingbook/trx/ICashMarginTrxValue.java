/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.cms.app.tradingbook.bus.ICashMargin;

/**
 * Contains actual cash margin and staging cash margin for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ICashMarginTrxValue extends ICPAgreementTrxValue {
	/**
	 * Gets the actual cash margin objects in this transaction.
	 * 
	 * @return The actual cash margin objects
	 */
	public ICashMargin[] getCashMargin();

	/**
	 * Sets the actual cash margin objects for this transaction.
	 * 
	 * @param value the actual cash margin objects
	 */
	public void setCashMargin(ICashMargin[] value);

	/**
	 * Gets the staging cash margin objects in this transaction.
	 * 
	 * @return the staging cash margin objects
	 */
	public ICashMargin[] getStagingCashMargin();

	/**
	 * Sets the staging cash margin objects for this transaction.
	 * 
	 * @param value the staging cash margin objects
	 */
	public void setStagingCashMargin(ICashMargin[] value);

	/**
	 * Get agreement Type.
	 * 
	 * @return String
	 */
	public Double getTotalCashInterest();

	/**
	 * Set agreement Type.
	 * 
	 * @param value of type String
	 */
	public void setTotalCashInterest(Double value);

}
