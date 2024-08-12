/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;

/**
 * Contains actual GMRA Deal and staging GMRA Deal for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IGMRADealTrxValue extends ICPAgreementTrxValue {
	/**
	 * Gets the actual GMRA Deal objects in this transaction.
	 * 
	 * @return The actual IGMRADeal
	 */
	public IGMRADeal getGMRADeal();

	/**
	 * Sets the actual GMRA Deal objects for this transaction.
	 * 
	 * @param value the actual IGMRADeal
	 */
	public void setGMRADeal(IGMRADeal value);

	/**
	 * Gets the staging GMRA Deal objects in this transaction.
	 * 
	 * @return the staging IGMRADeal
	 */
	public IGMRADeal getStagingGMRADeal();

	/**
	 * Sets the staging GMRA Deal objects for this transaction.
	 * 
	 * @param value the staging IGMRADeal
	 */
	public void setStagingGMRADeal(IGMRADeal value);

}
