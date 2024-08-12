/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds a GMRA deal and its valuation details.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBGMRADealVal extends OBDealValuation implements IGMRADealVal {
	private IGMRADeal details;

	/**
	 * Default Constructor.
	 */
	public OBGMRADealVal() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IDealValuation
	 */
	public OBGMRADealVal(IDealValuation obj) {
		super(obj);
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IGMRADealVal
	 */
	public OBGMRADealVal(IGMRADealVal obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADealVal#getGMRADealDetail
	 */
	public IGMRADeal getGMRADealDetail() {
		return this.details;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADealVal#setGMRADealDetail
	 */
	public void setGMRADealDetail(IGMRADeal value) {
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
		else if (!(obj instanceof OBGMRADealVal)) {
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