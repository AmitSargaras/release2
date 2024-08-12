/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/MakerCancelUpdateCommodityDealOperation.java,v 1.1 2004/06/11 11:01:34 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to cancel rejected/draft
 * transactions.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/11 11:01:34 $ Tag: $Name: $
 */
public class MakerCancelUpdateCommodityDealOperation extends AbstractCommodityDealTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCancelUpdateCommodityDealOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CANCEL_UPDATE_DEAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging commodity deal 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICommodityDealTrxValue trxValue = super.getCommodityDealTrxValue(value);

			trxValue.setStagingCommodityDeal(trxValue.getCommodityDeal());
			trxValue = super.createStagingDeal(trxValue);
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
