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
 * This operation class is invoked by maker to cancel draft updated by him/her,
 * or close liquidation rejected by a checker.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MakerCancelUpdateLiquidationOperation extends AbstractLiquidationTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCancelUpdateLiquidationOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CANCEL_LIQUIDATION;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging Liquidation record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILiquidationTrxValue trxValue = super.getLiquidationTrxValue(value);

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
				trxValue.setStagingLiquidation(trxValue.getLiquidation());

				trxValue = super.createStagingLiquidations(trxValue);
			}

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
