/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds a ISDA CSA deal and its valuation details.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBISDACSADealVal extends OBDealValuation implements IISDACSADealVal {
	private IISDACSADeal details;

	/**
	 * Default Constructor.
	 */
	public OBISDACSADealVal() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IDealValuation
	 */
	public OBISDACSADealVal(IDealValuation obj) {
		super(obj);
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IISDACSADealVal
	 */
	public OBISDACSADealVal(IISDACSADealVal obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal#getISDACSADealDetail
	 */
	public IISDACSADeal getISDACSADealDetail() {
		return this.details;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal#setISDACSADealDetail
	 */
	public void setISDACSADealDetail(IISDACSADeal value) {
		this.details = value;
	}

	/**
	 * Update specific deal valuation to this object.
	 * 
	 * @param obj is of type IDealValuation
	 */
	public void updateValuation(IDealValuation value) {
		super.update(value);
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBISDACSADealVal)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}