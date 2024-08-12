/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds list of ISDA CSA deal summary which belongs to an agreement
 * ID.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBISDACSADealSummary extends OBCPAgreementDetail implements IISDACSADealSummary {

	private IISDACSADealVal[] summary = null;

	/**
	 * Default Constructor
	 */
	public OBISDACSADealSummary() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICPAgreementDetail
	 */
	public OBISDACSADealSummary(ICPAgreementDetail value) {
		super(value);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IISDACSADealSummary
	 */
	public OBISDACSADealSummary(IISDACSADealSummary value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADealSummary#getISDACSADealSummary
	 */
	public IISDACSADealVal[] getISDACSADealSummary() {
		return this.summary;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADealSummary#setISDACSADealSummary
	 */
	public void setISDACSADealSummary(IISDACSADealVal[] value) {
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