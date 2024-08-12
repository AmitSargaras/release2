/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;

/**
 * Contains actual GMRA Deal Valuation and staging GMRA Deal Valuation for
 * transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IGMRADealValTrxValue extends ICPAgreementTrxValue {
	/**
	 * Gets the actual GMRA Deal Valuation objects in this transaction.
	 * 
	 * @return array of actual IGMRADealVal
	 */
	public IGMRADealVal[] getGMRADealValuation();

	/**
	 * Sets the actual GMRA Deal Valuation objects for this transaction.
	 * 
	 * @param value the array of actual IGMRADealVal
	 */
	public void setGMRADealValuation(IGMRADealVal[] value);

	/**
	 * Gets the staging GMRA Deal Valuation objects in this transaction.
	 * 
	 * @return the array of staging IGMRADealVal
	 */
	public IGMRADealVal[] getStagingGMRADealValuation();

	/**
	 * Sets the staging GMRA Deal Valuation objects for this transaction.
	 * 
	 * @param value the array of staging IGMRADealVal
	 */
	public void setStagingGMRADealValuation(IGMRADealVal[] value);

}
