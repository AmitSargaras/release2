/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

/**
 * This interface represents a GMRA Deal and its valuation.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IGMRADealVal extends IDealValuation {

	/**
	 * Get GMRA Deal details
	 * 
	 * @return IGMRADeal
	 */
	public IGMRADeal getGMRADealDetail();

	/**
	 * Set GMRA Deal details.
	 * 
	 * @param value of type IGMRADeal
	 */
	public void setGMRADealDetail(IGMRADeal value);

}
