/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/OBCoBorrowerLimitTrxValue.java,v 1.3 2005/09/23 05:27:38 whuang Exp $
 */
package com.integrosys.cms.app.limit.trx;

import java.util.ArrayList;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class represents a Limit trx value.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/23 05:27:38 $ Tag: $Name: $
 */
public class OBCoBorrowerLimitTrxValue extends OBCMSTrxValue implements ICoBorrowerLimitTrxValue {
	private ICoBorrowerLimit _limit = null;

	private ICoBorrowerLimit _stagingLimit = null;

	private ICoBorrowerLimitTrxValue[] trxValues = null;

	private ArrayList customerList = null;

	/**
	 * @return Returns the trxValues.
	 */
	public ICoBorrowerLimitTrxValue[] getCoBorrowerLimitTrxValues() {
		return trxValues;
	}

	/**
	 * @param trxValues The trxValues to set.
	 */
	public void setCoBorrowerLimitTrxValues(ICoBorrowerLimitTrxValue[] trxValues) {
		this.trxValues = trxValues;
	}

	/**
	 * Default Constructor
	 */
	public OBCoBorrowerLimitTrxValue() {
		super.setTransactionType(ICMSConstant.INSTANCE_COBORROWER_LIMIT);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICoBorrowerLimitTrxValue
	 */
	public OBCoBorrowerLimitTrxValue(ICoBorrowerLimitTrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICMSTrxValue
	 */
	public OBCoBorrowerLimitTrxValue(ICMSTrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * Get the Limit object
	 * 
	 * @return ICoBorrowerLimit
	 */
	public ICoBorrowerLimit getLimit() {
		return _limit;
	}

	/**
	 * Get the staging limit object
	 * 
	 * @return ICoBorrowerLimit
	 */
	public ICoBorrowerLimit getStagingLimit() {
		return _stagingLimit;
	}

	/**
	 * Set the limit object
	 * 
	 * @param value is of type ICoBorrowerLimit
	 */
	public void setLimit(ICoBorrowerLimit value) {
		_limit = value;
	}

	/**
	 * Set the staging limit object
	 * 
	 * @param value is of type ICoBorrowerLimit
	 */
	public void setStagingLimit(ICoBorrowerLimit value) {
		_stagingLimit = value;
	}

	/**
	 * @return Returns the customerList.
	 */
	public ArrayList getCustomerList() {
		return customerList;
	}

	/**
	 * @param customerList The customerList to set.
	 */
	public void setCustomerList(ArrayList customerList) {
		this.customerList = customerList;
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