/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/SystemCloseLimitOperation.java,v 1.3 2003/07/23 07:49:37 kllee Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a system to close a limit transaction
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/23 07:49:37 $ Tag: $Name: $
 */
public class SystemCloseLimitOperation extends AbstractLimitTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemCloseLimitOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_LIMIT;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Update Transaction
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILimitTrxValue trxValue = super.getLimitTrxValue(value);

			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}