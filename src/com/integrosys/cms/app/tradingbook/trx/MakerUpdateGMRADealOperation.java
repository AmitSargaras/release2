/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to update a GMRA deal.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MakerUpdateGMRADealOperation extends AbstractGMRADealTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerUpdateGMRADealOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_GMRA_DEAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging GMRA deal record 2. update transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IGMRADealTrxValue trxValue = super.getGMRADealTrxValue(value);

			trxValue = super.createStagingGMRADeal(trxValue);

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

	/**
	 * Create a GMRA deal transaction
	 * @param intRateTrxValue of IGMRADealTrxValue type
	 * @return IGMRADealTrxValue - the GMRA deal specific transaction object
	 *         created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private IGMRADealTrxValue createIntRateTransaction(IGMRADealTrxValue intRateTrxValue) throws TrxOperationException {

		try {
			/*
			 * intRateTrxValue = prepareTrxValue(intRateTrxValue); ICMSTrxValue
			 * trxValue = createTransaction(intRateTrxValue); OBGMRADealTrxValue
			 * newIntRateTrxValue = new OBGMRADealTrxValue (trxValue);
			 * newIntRateTrxValue.setStagingGMRADeals
			 * (intRateTrxValue.getStagingGMRADeals());
			 * newIntRateTrxValue.setGMRADeals(intRateTrxValue.getGMRADeals());
			 */
			return null;
		}
		// catch(TransactionException ex)
		// {
		// ex.printStackTrace();
		// throw new TrxOperationException(ex);
		// }
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

}
