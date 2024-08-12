/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/ICoBorrowerLimitTrxValue.java,v 1.3 2005/09/23 05:26:30 whuang Exp $
 */
package com.integrosys.cms.app.limit.trx;

import java.util.ArrayList;

import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CoBorrower Limit trx value.
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/23 05:26:30 $ Tag: $Name: $
 */
public interface ICoBorrowerLimitTrxValue extends ICMSTrxValue {
	/**
	 * Get the Limit object
	 * 
	 * @return ICoBorrowerLimit
	 */
	public ICoBorrowerLimit getLimit();

	/**
	 * Get the staging limit object
	 * 
	 * @return ICoBorrowerLimit
	 */
	public ICoBorrowerLimit getStagingLimit();

	/**
	 * Set the limit object
	 * 
	 * @param value is of type ILimit
	 */
	public void setLimit(ICoBorrowerLimit value);

	/**
	 * Set the staging limit object
	 * 
	 * @param value is of type ICoBorrowerLimit
	 */
	public void setStagingLimit(ICoBorrowerLimit value);

	/**
	 * set coBorrower limit trxValue
	 * @param trxValues
	 */
	public void setCoBorrowerLimitTrxValues(ICoBorrowerLimitTrxValue[] trxValues);

	/**
	 * get coBorrower limit trxValue
	 * @return
	 */
	public ICoBorrowerLimitTrxValue[] getCoBorrowerLimitTrxValues();

	public void setCustomerList(ArrayList customerList);

	public ArrayList getCustomerList();
}