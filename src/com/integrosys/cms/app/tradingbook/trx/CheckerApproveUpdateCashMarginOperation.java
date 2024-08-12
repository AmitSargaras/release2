/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;

/**
 * This operation class is invoked by a checker to approve cash margin updated
 * by a maker.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class CheckerApproveUpdateCashMarginOperation extends AbstractCashMarginTrxOperation {
	/**
	 * Default constructor.
	 */
	public CheckerApproveUpdateCashMarginOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CASH_MARGIN;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update actual cash margin record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICashMarginTrxValue trxValue = super.getCashMarginTrxValue(value);

			ICashMargin[] actual = trxValue.getCashMargin();
			ICashMargin[] staging = trxValue.getStagingCashMargin();

			if ((staging != null) && (staging.length > 0)) {
				trxValue = super.calcInterest(trxValue);
			}

			if ((actual == null) || (actual.length == 0)) {
				trxValue = super.createActualCashMargin(trxValue);
			}
			else {
				trxValue = super.updateActualCashMargin(trxValue);
			}
			trxValue = super.createStagingCashMargin(trxValue);

			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}
}
