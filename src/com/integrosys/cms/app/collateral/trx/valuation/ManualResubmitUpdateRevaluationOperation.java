/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ManualResubmitUpdateRevaluationOperation.java,v 1.2 2003/07/18 02:15:39 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.valuation;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to resubmit update of collateral
 * revaluation rejected by a checker.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/18 02:15:39 $ Tag: $Name: $
 */
public class ManualResubmitUpdateRevaluationOperation extends AbstractRevaluationTrxOperation {
	/**
	 * Default constructor.
	 */
	public ManualResubmitUpdateRevaluationOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MANUAL_RESUBMIT_UPDATE_REVAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging collateral record 2. update transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IValuationTrxValue trxValue = super.getValuationTrxValue(value);

			trxValue = super.createStagingValuation(trxValue);
			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}
