/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual and staging GMRA Deal Valuation for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBGMRADealValTrxValue extends OBCPAgreementTrxValue implements IGMRADealValTrxValue {
	private IGMRADealVal[] actual;

	private IGMRADealVal[] staging;

	/**
	 * Default constructor.
	 */
	public OBGMRADealValTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_GMRA_DEAL_VAL);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type IGMRADealTrxValue
	 */
	public OBGMRADealValTrxValue(IGMRADealValTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBGMRADealValTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue#getGMRADeals
	 */
	public IGMRADealVal[] getGMRADealValuation() {
		return this.actual;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue#setGMRADeals
	 */
	public void setGMRADealValuation(IGMRADealVal[] value) {
		this.actual = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue#getStagingGMRADeals
	 */
	public IGMRADealVal[] getStagingGMRADealValuation() {
		return this.staging;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue#setStagingGMRADeals
	 */
	public void setStagingGMRADealValuation(IGMRADealVal[] value) {
		this.staging = value;
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
