/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

/**
 * This interface represents a ISDA CSA Deal and its valuation.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IISDACSADealVal extends IDealValuation {

	/**
	 * Get ISDA CSA Deal details
	 * 
	 * @return IISDACSADeal
	 */
	public IISDACSADeal getISDACSADealDetail();

	/**
	 * Set ISDA CSA Deal details
	 * 
	 * @param value of type IISDACSADeal
	 */
	public void setISDACSADealDetail(IISDACSADeal value);

}
