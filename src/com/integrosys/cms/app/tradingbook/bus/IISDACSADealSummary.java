/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:  $
 */
package com.integrosys.cms.app.tradingbook.bus;

/**
 * This interface represents list of ISDA CSA deal summary data which belongs to
 * an agreement ID.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IISDACSADealSummary extends ICPAgreementDetail, java.io.Serializable {

	/**
	 * Get array of ISDA CSA deal and its valuation detail
	 * 
	 * @return IISDACSADealVal[]
	 */
	public IISDACSADealVal[] getISDACSADealSummary();

	/**
	 * Set array of ISDA CSA deal and its valuation detail
	 * 
	 * @param value is of type IISDACSADealVal[]
	 */
	public void setISDACSADealSummary(IISDACSADealVal[] value);

}