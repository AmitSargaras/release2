/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual and staging ISDA CSA deal valuation for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBISDACSADealValTrxValue extends OBCPAgreementTrxValue implements IISDACSADealValTrxValue {
	private IISDACSADealVal[] actual;

	private IISDACSADealVal[] staging;

	/**
	 * Default constructor.
	 */
	public OBISDACSADealValTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_ISDA_DEAL_VAL);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type IISDACSADealTrxValue
	 */
	public OBISDACSADealValTrxValue(IISDACSADealValTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBISDACSADealValTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IISDACSADealTrxValue#getISDACSADealValuation
	 */
	public IISDACSADealVal[] getISDACSADealValuation() {
		return this.actual;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IISDACSADealTrxValue#setISDACSADealValuation
	 */
	public void setISDACSADealValuation(IISDACSADealVal[] value) {
		this.actual = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IISDACSADealTrxValue#getStagingISDACSADealValuation
	 */
	public IISDACSADealVal[] getStagingISDACSADealValuation() {
		return this.staging;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.IISDACSADealTrxValue#setStagingISDACSADealValuation
	 */
	public void setStagingISDACSADealValuation(IISDACSADealVal[] value) {
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
