/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/CheckerApproveCreateCommodityDealOperation.java,v 1.5 2005/08/05 12:45:38 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a checker to approve commodity deal
 * created by a maker.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/08/05 12:45:38 $ Tag: $Name: $
 */
public class CheckerApproveCreateCommodityDealOperation extends AbstractCommodityDealTrxOperation {

	/**
	 * Default constructor.
	 */
	public CheckerApproveCreateCommodityDealOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_DEAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create actual commodity deal record 2. update Transaction record 3.
	 * send deal approval notification
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICommodityDealTrxValue trxValue = super.getCommodityDealTrxValue(value);

			trxValue = super.createActualDeal(trxValue);
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

	/**
	 * Post Processing after the trx operation. 1. To calculate commodity value.
	 * 
	 * @param result transaction result
	 * @return the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		ICommodityDealTrxValue trxValue = super.getCommodityDealTrxValue(result.getTrxValue());
		super.revaluateCommodity(trxValue);
		return result;
	}
}
