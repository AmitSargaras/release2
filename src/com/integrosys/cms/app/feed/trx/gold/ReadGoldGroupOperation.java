package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

public class ReadGoldGroupOperation extends AbstractGoldTrxOperation implements ITrxReadOperation {

	public ReadGoldGroupOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_READ_GOLD_FEED_GROUP;
	}

	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
		IGoldFeedGroupTrxValue vv = (IGoldFeedGroupTrxValue) value;

		try {

			DefaultLogger.debug(this, "reference ID = " + vv.getReferenceID());
			DefaultLogger.debug(this, "trx id = " + vv.getTransactionID());

			String trxId = vv.getTransactionID();

			ICMSTrxValue trxValue = null;

			SBCMSTrxManager trxManager = getTrxManager();

			// Get the actual records first.

			if ((trxId == null) || trxId.equals("")) {

				IGoldFeedGroup feedGroup = getGoldFeedBusManager().getGoldFeedGroup(ICMSConstant.GOLD_FEED_GROUP_TYPE);

				DefaultLogger.debug(this, "gold feed group id = " + String.valueOf(feedGroup.getGoldFeedGroupID()));

				trxValue = trxManager.getTrxByRefIDAndTrxType(String.valueOf(feedGroup.getGoldFeedGroupID()),
						ICMSConstant.INSTANCE_GOLD_FEED_GROUP);

				vv = new OBGoldFeedGroupTrxValue(trxValue);
				vv.setGoldFeedGroup(feedGroup);
			}
			else {

				trxValue = trxManager.getTransaction(trxId);

				vv = new OBGoldFeedGroupTrxValue(trxValue);

				if (vv.getReferenceID() != null) {
					DefaultLogger.debug(this, "reference id = " + vv.getReferenceID());

					IGoldFeedGroup feedGroup = getGoldFeedBusManager().getGoldFeedGroup(
							Long.parseLong(vv.getReferenceID()));
					vv.setGoldFeedGroup(feedGroup);

					if ((feedGroup != null) && (feedGroup.getFeedEntries() != null)) {
						DefaultLogger.debug(this, "number of actual feed entries for " + "the actual feed group = "
								+ feedGroup.getFeedEntries().length);
					}
					else {
						DefaultLogger.debug(this, "there is no actual feed group found");
					}

					vv.setGoldFeedGroup(feedGroup);

				}

			}

			DefaultLogger.debug(this, "transaction found for gold group, trx ID is " + trxValue.getTransactionID());

			// Go get the staging records.

			if (vv.getStagingReferenceID() != null) {

				DefaultLogger.debug(this, "staging reference id = " + vv.getStagingReferenceID());

				IGoldFeedGroup stagingFeedGroup = getStagingGoldFeedBusManager().getGoldFeedGroup(
						Long.parseLong(trxValue.getStagingReferenceID()));
				vv.setStagingGoldFeedGroup(stagingFeedGroup);

				if ((stagingFeedGroup != null) && (stagingFeedGroup.getFeedEntries() != null)) {
					DefaultLogger.debug(this, "number of staging feed entries for " + "the staging feed group = "
							+ stagingFeedGroup.getFeedEntries().length);
				}
				else {
					DefaultLogger.debug(this, "there is no staging feed group found");
				}

			}
			else {
				// Do nothing.
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TrxOperationException(e);
		}

		return vv;
	}
}
