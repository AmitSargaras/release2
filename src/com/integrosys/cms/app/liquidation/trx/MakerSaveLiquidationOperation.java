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
 * This operation class is invoked by maker to save liquidation.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MakerSaveLiquidationOperation extends AbstractLiquidationTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerSaveLiquidationOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_LIQUIDATION;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging Liquidation parameter record 2. create staging
	 * transaction record if the status is ND, otherwise update transaction
	 * record.
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILiquidationTrxValue trxValue = super.getLiquidationTrxValue(value);

			trxValue = super.createStagingLiquidations(trxValue);

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
				trxValue = super.createTransaction(trxValue);
			}
			else {
				trxValue = super.updateTransaction(trxValue);
			}

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
