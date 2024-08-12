/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/MakerSaveCommodityDealOperation.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to save commodity deal.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public class MakerSaveCommodityDealOperation extends AbstractCommodityDealTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerSaveCommodityDealOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_DEAL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging commodity deal record 2. create/update Transaction
	 * record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICommodityDealTrxValue trxValue = super.getCommodityDealTrxValue(value);

			trxValue = super.createStagingDeal(trxValue);

			if (trxValue.getTransactionID() == null) {
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
