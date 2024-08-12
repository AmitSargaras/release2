/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual and staging cash margin for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBCashMarginTrxValue extends OBCPAgreementTrxValue implements ICashMarginTrxValue {
	private ICashMargin[] actual;

	private ICashMargin[] staging;

	private Double totalCashInterest;

	/**
	 * Default constructor.
	 */
	public OBCashMarginTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_CASH_MARGIN);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICashMarginTrxValue
	 */
	public OBCashMarginTrxValue(ICashMarginTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBCashMarginTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue#getCashMargin
	 */
	public ICashMargin[] getCashMargin() {
		return this.actual;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue#setCashMargin
	 */
	public void setCashMargin(ICashMargin[] value) {
		this.actual = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue#getStagingCashMargin
	 */
	public ICashMargin[] getStagingCashMargin() {
		return this.staging;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue#setStagingCashMargin
	 */
	public void setStagingCashMargin(ICashMargin[] value) {
		this.staging = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue#getTotalCashInterest
	 */
	public Double getTotalCashInterest() {
		return this.totalCashInterest;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue#setTotalCashInterest
	 */
	public void setTotalCashInterest(Double value) {
		this.totalCashInterest = value;
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
