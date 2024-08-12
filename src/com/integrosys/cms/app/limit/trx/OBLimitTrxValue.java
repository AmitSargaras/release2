/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/OBLimitTrxValue.java,v 1.5 2005/09/23 05:27:38 whuang Exp $
 */
package com.integrosys.cms.app.limit.trx;

import java.util.HashMap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class represents a Limit trx value.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.5 $
 * @since $Date: 2005/09/23 05:27:38 $ Tag: $Name: $
 */
public class OBLimitTrxValue extends OBCMSTrxValue implements ILimitTrxValue {

	private static final long serialVersionUID = -1957889463019934971L;

	private ILimit actualLimit = null;

	private ILimit stagingLimit = null;

	private boolean isToUpdateOPLimit = false;

	private ILimitTrxValue[] limitTrxValues;

	private HashMap bcaList = null;

	/**
	 * Default Constructor
	 */
	public OBLimitTrxValue() {
		super.setTransactionType(ICMSConstant.INSTANCE_LIMIT);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ILimitTrxValue
	 */
	public OBLimitTrxValue(ILimitTrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICMSTrxValue
	 */
	public OBLimitTrxValue(ICMSTrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * Get the Limit object
	 * 
	 * @return ILimit
	 */
	public ILimit getLimit() {
		return actualLimit;
	}

	/**
	 * Get the staging limit object
	 * 
	 * @return ILimit
	 */
	public ILimit getStagingLimit() {
		return stagingLimit;
	}

	/**
	 * Set the limit object
	 * 
	 * @param value is of type ILimit
	 */
	public void setLimit(ILimit value) {
		actualLimit = value;
	}

	/**
	 * Set the staging limit object
	 * 
	 * @param value is of type ILimit
	 */
	public void setStagingLimit(ILimit value) {
		stagingLimit = value;
	}

	/**
	 * Indicator to update operational limit.
	 * 
	 * @return boolean
	 */
	public boolean isToUpdateOPLimit() {
		return isToUpdateOPLimit;
	}

	/**
	 * Set indicator to update operational limit.
	 * 
	 * @param toUpdateOPLimit of type boolean
	 */
	public void setToUpdateOPLimit(boolean toUpdateOPLimit) {
		isToUpdateOPLimit = toUpdateOPLimit;
	}

	/**
	 * Get limit transaction values.
	 * 
	 * @return of type ILimitTrxValue[]
	 */
	public ILimitTrxValue[] getLimitTrxValues() {
		return limitTrxValues;
	}

	/**
	 * Set limit transaction values.
	 * 
	 * @param limitTrxValues of type ILimitTrxValue[]
	 */
	public void setLimitTrxValues(ILimitTrxValue[] limitTrxValues) {
		this.limitTrxValues = limitTrxValues;
	}

	/**
	 * @return Returns the bcaList.
	 */
	public HashMap getBcaList() {
		return bcaList;
	}

	/**
	 * @param bcaList The bcaList to set.
	 */
	public void setBcaList(HashMap bcaList) {
		this.bcaList = bcaList;
	}

}