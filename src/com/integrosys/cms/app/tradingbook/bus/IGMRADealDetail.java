/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

/**
 * This interface represents counter party, agreement and GMRA deal details.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IGMRADealDetail extends ICPAgreementDetail, java.io.Serializable {

	/**
	 * Get GMRA deal details
	 * 
	 * @return IGMRADeal
	 */
	public IGMRADeal getGMRADealDetail();

	/**
	 * Set GMRA deal details
	 * 
	 * @param value is of type IGMRADeal
	 */
	public void setGMRADealDetail(IGMRADeal value);

}