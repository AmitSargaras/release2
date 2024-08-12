/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/SystemCloseRevaluationOperation.java,v 1.1 2003/07/18 02:16:00 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.valuation;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a system to close revaluation of collateral
 * transaction.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/18 02:16:00 $ Tag: $Name: $
 */
public class SystemCloseRevaluationOperation extends AbstractRevaluationTrxOperation {
	/**
	 * Default constructor.
	 */
	public SystemCloseRevaluationOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_REVAL;
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
			IValuationTrxValue trxValue = super.getValuationTrxValue(value);

			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TrxOperationException("Exception caught!", e);
		}
	}
}