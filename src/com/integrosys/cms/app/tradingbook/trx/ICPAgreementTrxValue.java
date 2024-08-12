/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains counter party and agreement details for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ICPAgreementTrxValue extends ICMSTrxValue {
	/**
	 * Get limit profile ID.
	 * 
	 * @return long
	 */
	public long getLimitProfileID();

	/**
	 * Set limit profile ID.
	 * 
	 * @param value of type long
	 */
	public void setLimitProfileID(long value);

	/**
	 * Get agreement ID.
	 * 
	 * @return long
	 */
	public long getAgreementID();

	/**
	 * Set agreement ID.
	 * 
	 * @param value of type long
	 */
	public void setAgreementID(long value);

	/**
	 * Get counter party and agreement details.
	 * 
	 * @return ICPAgreementDetail
	 */
	public ICPAgreementDetail getCPAgreementDetail();

	/**
	 * Set counter party and agreement details.
	 * 
	 * @param value of type ICPAgreementDetail
	 */
	public void setCPAgreementDetail(ICPAgreementDetail value);

}
