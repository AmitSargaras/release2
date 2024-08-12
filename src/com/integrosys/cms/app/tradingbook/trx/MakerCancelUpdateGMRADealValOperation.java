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
 * This operation class is invoked by maker to cancel draft updated by him/her,
 * or close GMRA deal valuation rejected by a checker.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MakerCancelUpdateGMRADealValOperation extends AbstractGMRADealValTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCancelUpdateGMRADealValOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CANCEL_GMRA_DEAL_VAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging GMRA deal valuation record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IGMRADealValTrxValue trxValue = super.getGMRADealValuationTrxValue(value);

			trxValue.setStagingGMRADealValuation(trxValue.getGMRADealValuation());
			trxValue = super.createStagingGMRADealValuation(trxValue);
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
