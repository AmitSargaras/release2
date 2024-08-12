/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CheckerApproveRevaluationOperation.java,v 1.3 2003/07/18 02:15:27 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.valuation;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by checker to approve a collateral revaluated
 * by maker.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/18 02:15:27 $ Tag: $Name: $
 */
public class CheckerApproveRevaluationOperation extends AbstractRevaluationTrxOperation {
	/**
	 * Default constructor.
	 */
	public CheckerApproveRevaluationOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_REVAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create actual valuation record 2. update transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IValuationTrxValue trxValue = super.getValuationTrxValue(value);

			trxValue = createActualValuation(trxValue);
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

	/**
	 * Create actual valuation record.
	 * 
	 * @param value is of type IValuationTrxValue
	 * @return IValuationTrxValue
	 * @throws TrxOperationException on errors creating valuation
	 */
	protected IValuationTrxValue createActualValuation(IValuationTrxValue value) throws TrxOperationException {
		// when approved, createActual gets data from staging.
		value.setValuation(value.getStagingValuation());
		return super.createActualValuation(value);
	}
}
