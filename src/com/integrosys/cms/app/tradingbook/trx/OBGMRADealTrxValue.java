/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual and staging GMRA deal for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBGMRADealTrxValue extends OBCPAgreementTrxValue implements IGMRADealTrxValue {
	private IGMRADeal actual;

	private IGMRADeal staging;

	/**
	 * Default constructor.
	 */
	public OBGMRADealTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_GMRA_DEAL);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type IGMRADealTrxValue
	 */
	public OBGMRADealTrxValue(IGMRADealTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBGMRADealTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue#getGMRADeal
	 */
	public IGMRADeal getGMRADeal() {
		return this.actual;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue#setGMRADeal
	 */
	public void setGMRADeal(IGMRADeal value) {
		this.actual = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue#getStagingGMRADeal
	 */
	public IGMRADeal getStagingGMRADeal() {
		return this.staging;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue#setStagingGMRADeal
	 */
	public void setStagingGMRADeal(IGMRADeal value) {
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
