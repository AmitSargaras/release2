package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.bus.gold.GoldFeedGroupException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;

public class CheckerApproveUpdateOperation extends AbstractGoldTrxOperation {

	public CheckerApproveUpdateOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_GOLD_FEED_GROUP;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IGoldFeedGroupTrxValue trxValue = getGoldFeedGroupTrxValue(anITrxValue);
		trxValue = createStagingGoldFeedGroup(trxValue);
		trxValue = updateActualGoldFeedGroup(trxValue);
		trxValue = updateGoldFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	private IGoldFeedGroupTrxValue updateActualGoldFeedGroup(IGoldFeedGroupTrxValue anIGoldFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			IGoldFeedGroup staging = anIGoldFeedGroupTrxValue.getStagingGoldFeedGroup();
			IGoldFeedGroup actual = anIGoldFeedGroupTrxValue.getGoldFeedGroup();

			IGoldFeedGroup updatedActualGoldFeedGroup = getGoldFeedBusManager().updateToWorkingCopy(actual, staging);

			anIGoldFeedGroupTrxValue.setGoldFeedGroup(updatedActualGoldFeedGroup);
			return anIGoldFeedGroupTrxValue;
		}
		catch (GoldFeedGroupException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualGoldFeedGroup(): " + ex.toString());
		}
	}
}
