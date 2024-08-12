package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerRejectOperation extends AbstractGoldTrxOperation {

	public CheckerRejectOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_GOLD_FEED_GROUP;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IGoldFeedGroupTrxValue trxValue = super.getGoldFeedGroupTrxValue(anITrxValue);
		trxValue = updateGoldFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
