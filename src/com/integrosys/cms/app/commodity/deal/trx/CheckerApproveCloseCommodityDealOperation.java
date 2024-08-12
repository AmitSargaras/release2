/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/CheckerApproveCloseCommodityDealOperation.java,v 1.3 2004/07/12 10:03:17 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a checker to approve commodity deal closed
 * by a maker.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/12 10:03:17 $ Tag: $Name: $
 */
public class CheckerApproveCloseCommodityDealOperation extends AbstractCommodityDealTrxOperation {
	/**
	 * Default constructor.
	 */
	public CheckerApproveCloseCommodityDealOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CLOSE_DEAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICommodityDealTrxValue trxValue = super.getCommodityDealTrxValue(value);

			trxValue = super.updateActualDealStatus(trxValue);
			trxValue = super.updateStagingDealStatus(trxValue);
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
