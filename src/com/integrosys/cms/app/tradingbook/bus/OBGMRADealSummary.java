/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds list of GMRA deal summary which belongs to an agreement ID.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBGMRADealSummary extends OBCPAgreementDetail implements IGMRADealSummary {

	private IGMRADealVal[] summary = null;

	/**
	 * Default Constructor
	 */
	public OBGMRADealSummary() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICPAgreementDetail
	 */
	public OBGMRADealSummary(ICPAgreementDetail value) {
		super(value);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IGMRADealSummary
	 */
	public OBGMRADealSummary(IGMRADealSummary value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADealSummary#getGMRADealSummary
	 */
	public IGMRADealVal[] getGMRADealSummary() {
		return this.summary;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADealSummary#setGMRADealSummary
	 */
	public void setGMRADealSummary(IGMRADealVal[] value) {
		this.summary = value;
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