/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation class is invoked by maker to update a liquidation.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MakerUpdateLiquidationOperation extends AbstractLiquidationTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerUpdateLiquidationOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_LIQUIDATION;
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
				trxValue = createLiqTransaction(trxValue);
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

	/**
	 * Create a liquidation transaction
	 * @param LiqTrxValue of ILiquidationTrxValue type
	 * @return ILiquidationTrxValue - the liquidation specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	private ILiquidationTrxValue createLiqTransaction(ILiquidationTrxValue LiqTrxValue) throws TrxOperationException {

		try {
			LiqTrxValue = prepareTrxValue(LiqTrxValue);
			ICMSTrxValue trxValue = createTransaction(LiqTrxValue);
			OBLiquidationTrxValue newLiqTrxValue = new OBLiquidationTrxValue(trxValue);
			newLiqTrxValue.setStagingLiquidation(LiqTrxValue.getStagingLiquidation());
			newLiqTrxValue.setLiquidation(LiqTrxValue.getLiquidation());
			return newLiqTrxValue;
		}
		catch (TransactionException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

}
