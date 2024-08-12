/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/MakerCloseCommodityDealOperation.java,v 1.3 2005/11/19 06:34:05 czhou Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to close commodity deal.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/11/19 06:34:05 $ Tag: $Name: $
 */
public class MakerCloseCommodityDealOperation extends AbstractCommodityDealTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCloseCommodityDealOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_DEAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging deal record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICommodityDealTrxValue trxValue = super.getCommodityDealTrxValue(value);

			// trxValue = super.updateStagingDealStatus (trxValue); //CR119
			trxValue = super.createStagingDeal(trxValue); // Required for report
															// RPT091 to track
															// status
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
