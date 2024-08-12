/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a checker to approve liquidation updated
 * by a maker.
 * 
 * @author lini
 * @author Chong Jun Yong
 */
public class CheckerApproveUpdateLiquidationOperation extends AbstractLiquidationTrxOperation {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5342715157325160565L;

	/**
	 * Default constructor.
	 */
	public CheckerApproveUpdateLiquidationOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_LIQUIDATION;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update actual Liquidation record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ILiquidationTrxValue trxValue = super.getLiquidationTrxValue(value);

		// trxValue = super.createStagingLiquidations (trxValue);
		trxValue = super.updateActualLiquidations(trxValue);
		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);

	}
}
