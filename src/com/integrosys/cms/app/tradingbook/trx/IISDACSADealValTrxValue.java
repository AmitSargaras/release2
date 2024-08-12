/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;

/**
 * Contains actual ISDA CSA Deal Valuation and staging ISDA CSA Deal Valuation
 * for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IISDACSADealValTrxValue extends ICPAgreementTrxValue {
	/**
	 * Gets the actual ISDA CSA Deal Valuation objects in this transaction.
	 * 
	 * @return The array of actual IISDACSADealVal
	 */
	public IISDACSADealVal[] getISDACSADealValuation();

	/**
	 * Sets the actual ISDA CSA Deal Valuation objects for this transaction.
	 * 
	 * @param value the array of actual IISDACSADealVal
	 */
	public void setISDACSADealValuation(IISDACSADealVal[] value);

	/**
	 * Gets the staging ISDA CSA Deal Valuation objects in this transaction.
	 * 
	 * @return the array of staging IISDACSADealVal
	 */
	public IISDACSADealVal[] getStagingISDACSADealValuation();

	/**
	 * Sets the staging ISDA CSA Deal Valuation objects for this transaction.
	 * 
	 * @param value the array of staging IISDACSADealVal
	 */
	public void setStagingISDACSADealValuation(IISDACSADealVal[] value);

}
