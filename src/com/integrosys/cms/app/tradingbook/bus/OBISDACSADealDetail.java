/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds counter party, agreement and ISDA CSA deal details.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBISDACSADealDetail extends OBCPAgreementDetail implements IISDACSADealDetail {

	private IISDACSADeal csaDeal = null;

	/**
	 * Default Constructor
	 */
	public OBISDACSADealDetail() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICPAgreementDetail
	 */
	public OBISDACSADealDetail(ICPAgreementDetail value) {
		super(value);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IISDACSADealDetail
	 */
	public OBISDACSADealDetail(IISDACSADealDetail value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADealDetail#getISDACSADealDetail
	 */
	public IISDACSADeal getISDACSADealDetail() {
		return this.csaDeal;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADealDetail#setISDACSADealDetail
	 */
	public void setISDACSADealDetail(IISDACSADeal value) {
		this.csaDeal = value;
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