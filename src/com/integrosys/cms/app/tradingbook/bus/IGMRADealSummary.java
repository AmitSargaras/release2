/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

/**
 * This interface represents list of GMRA deal summary data which belongs to an
 * agreement ID.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IGMRADealSummary extends ICPAgreementDetail, java.io.Serializable {

	/**
	 * Get array of GMRA deal and its valuation detail
	 * 
	 * @return IGMRADealVal[]
	 */
	public IGMRADealVal[] getGMRADealSummary();

	/**
	 * Set array of GMRA deal and its valuation detail
	 * 
	 * @param value is of type IGMRADealVal[]
	 */
	public void setGMRADealSummary(IGMRADealVal[] value);

}