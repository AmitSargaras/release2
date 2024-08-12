/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds counter party, agreement and GMRA deal details.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBGMRADealDetail extends OBCPAgreementDetail implements IGMRADealDetail {

	private IGMRADeal gmraDeal = null;

	/**
	 * Default Constructor
	 */
	public OBGMRADealDetail() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICPAgreementDetail
	 */
	public OBGMRADealDetail(ICPAgreementDetail value) {
		super(value);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IGMRADealDetail
	 */
	public OBGMRADealDetail(IGMRADealDetail value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADealDetail#getGMRADealDetail
	 */
	public IGMRADeal getGMRADealDetail() {
		return this.gmraDeal;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADealDetail#setGMRADealDetail
	 */
	public void setGMRADealDetail(IGMRADeal value) {
		this.gmraDeal = value;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}