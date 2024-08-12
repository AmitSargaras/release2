/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/ILimitTrxValue.java,v 1.5 2005/09/23 05:26:30 whuang Exp $
 */
package com.integrosys.cms.app.limit.trx;

import java.util.HashMap;

import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a Limit trx value.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.5 $
 * @since $Date: 2005/09/23 05:26:30 $ Tag: $Name: $
 */
public interface ILimitTrxValue extends ICMSTrxValue {
	/**
	 * Get the Limit object
	 * 
	 * @return ILimit
	 */
	public ILimit getLimit();

	/**
	 * Get the staging limit object
	 * 
	 * @return ILimit
	 */
	public ILimit getStagingLimit();

	/**
	 * Set the limit object
	 * 
	 * @param value is of type ILimit
	 */
	public void setLimit(ILimit value);

	/**
	 * Set the staging limit object
	 * 
	 * @param value is of type ILimit
	 */
	public void setStagingLimit(ILimit value);

	/**
	 * Indicator to update operational limit.
	 * 
	 * @return boolean
	 */
	public boolean isToUpdateOPLimit();

	/**
	 * Set indicator to update operational limit.
	 * 
	 * @param toUpdateOPLimit of type boolean
	 */
	public void setToUpdateOPLimit(boolean toUpdateOPLimit);

	/**
	 * Get limit transaction values.
	 * 
	 * @return of type ILimitTrxValue[]
	 */
	public ILimitTrxValue[] getLimitTrxValues();

	/**
	 * Set limit transaction values.
	 * 
	 * @param limitTrxValues of type ILimitTrxValue[]
	 */
	public void setLimitTrxValues(ILimitTrxValue[] limitTrxValues);

	/**
	 * set bca list
	 * @param bcaList
	 */
	public void setBcaList(HashMap bcaList);

	/**
	 * get bca list
	 * @return
	 */
	public HashMap getBcaList();
}