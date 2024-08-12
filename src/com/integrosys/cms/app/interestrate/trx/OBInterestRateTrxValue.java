/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.trx;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Contains actual and staging interestrate for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBInterestRateTrxValue extends OBCMSTrxValue implements IInterestRateTrxValue {
	private IInterestRate[] actual;

	private IInterestRate[] staging;

	private String intRateType;

	private Date monthYear;

	/**
	 * Default constructor.
	 */
	public OBInterestRateTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_INT_RATE);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type IInterestRateTrxValue
	 */
	public OBInterestRateTrxValue(IInterestRateTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBInterestRateTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue#getInterestRates
	 */
	public IInterestRate[] getInterestRates() {
		return actual;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue#setInterestRates
	 */
	public void setInterestRates(IInterestRate[] intRates) {
		actual = intRates;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue#getStagingInterestRates
	 */
	public IInterestRate[] getStagingInterestRates() {
		return staging;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue#setStagingInterestRates
	 */
	public void setStagingInterestRates(IInterestRate[] intRates) {
		staging = intRates;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue#getIntRateType
	 */
	public String getIntRateType() {
		return intRateType;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue#setIntRateType
	 */
	public void setIntRateType(String intRateType) {
		this.intRateType = intRateType;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue#getMonthYear
	 */
	public Date getMonthYear() {
		return monthYear;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue#setMonthYear
	 */
	public void setMonthYear(Date monthYear) {
		this.monthYear = monthYear;
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
