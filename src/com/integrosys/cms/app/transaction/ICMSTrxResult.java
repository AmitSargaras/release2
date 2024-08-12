/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ICMSTrxResult.java,v 1.4 2003/08/25 00:55:55 sathish Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;

/**
 * This interface represents the transaction result object for CMS.
 * 
 * @author Alfred Lee
 */
public interface ICMSTrxResult extends ITrxResult {
	/**
	 * Set Trx Value
	 * 
	 * @param value is of type ITrxValue
	 */
	public void setTrxValue(ITrxValue value);

	public String getLastUserInfo();

	public void setLastUserInfo(String userInfo);

	public String getLastRemarks();

	public void setLastRemarks(String remarks);
}
