package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerCloseDraftOperation extends AbstractGoldTrxOperation {

	public MakerCloseDraftOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_GOLD_FEED_GROUP;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IGoldFeedGroupTrxValue trxValue = super.getGoldFeedGroupTrxValue(anITrxValue);
		trxValue = updateGoldFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}
