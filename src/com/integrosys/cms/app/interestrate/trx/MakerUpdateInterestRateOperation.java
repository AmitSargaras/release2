/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation class is invoked by maker to update a interest rate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MakerUpdateInterestRateOperation extends AbstractInterestRateTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerUpdateInterestRateOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_INT_RATE;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging interestrate parameter record 2. create staging
	 * transaction record if the status is ND, otherwise update transaction
	 * record.
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IInterestRateTrxValue trxValue = super.getInterestRateTrxValue(value);

			trxValue = super.createStagingInterestRates(trxValue);

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
				trxValue = createIntRateTransaction(trxValue);
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
	 * Create a interest rate transaction
	 * @param intRateTrxValue of IInterestRateTrxValue type
	 * @return IInterestRateTrxValue - the interest rate specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private IInterestRateTrxValue createIntRateTransaction(IInterestRateTrxValue intRateTrxValue)
			throws TrxOperationException {

		try {
			intRateTrxValue = prepareTrxValue(intRateTrxValue);
			ICMSTrxValue trxValue = createTransaction(intRateTrxValue);
			OBInterestRateTrxValue newIntRateTrxValue = new OBInterestRateTrxValue(trxValue);
			newIntRateTrxValue.setStagingInterestRates(intRateTrxValue.getStagingInterestRates());
			newIntRateTrxValue.setInterestRates(intRateTrxValue.getInterestRates());
			return newIntRateTrxValue;
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
