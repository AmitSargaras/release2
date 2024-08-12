/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:  $
 */
package com.integrosys.cms.app.tradingbook.bus;

/**
 * This interface represents counter party, agreement and ISDA CSA deal details.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IISDACSADealDetail extends ICPAgreementDetail, java.io.Serializable {

	/**
	 * Get ISDA CSA deal details
	 * 
	 * @return IISDACSADeal
	 */
	public IISDACSADeal getISDACSADealDetail();

	/**
	 * Set ISDA CSA deal details
	 * 
	 * @param value is of type IISDACSADeal
	 */
	public void setISDACSADealDetail(IISDACSADeal value);

}