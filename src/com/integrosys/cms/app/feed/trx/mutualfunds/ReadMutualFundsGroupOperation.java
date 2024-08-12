/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/ReadBondGroupOperation.java,v 1.5 2003/08/19 07:52:43 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.mutualfunds;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

/**
 * This operation allows to read bond feed group transaction using transaction
 * id or primary key
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class ReadMutualFundsGroupOperation extends AbstractMutualFundsTrxOperation implements ITrxReadOperation {

	/**
	 * Defaulc Constructor
	 */
	public ReadMutualFundsGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_MUTUAL_FUNDS_FEED_GROUP;
	}

	/**
	 * This method is used to read a transaction object given a transaction ID
	 * 
	 * @param value is the ITrxValue object containing the parameters required
	 *        for retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {

		IMutualFundsFeedGroupTrxValue vv = (IMutualFundsFeedGroupTrxValue) value;

		try {

			DefaultLogger.debug(this, "reference ID = " + vv.getReferenceID());
			DefaultLogger.debug(this, "trx id = " + vv.getTransactionID());

			String trxId = vv.getTransactionID();

			ICMSTrxValue trxValue = null;

			SBCMSTrxManager trxManager = getTrxManager();

			// Get the actual records first.

			if ((trxId == null) || trxId.equals("")) {

				IMutualFundsFeedGroup feedGroup = getMutualFundsFeedBusManager().getMutualFundsFeedGroup(ICMSConstant.MUTUAL_FUNDS_FEED_GROUP_TYPE);

				DefaultLogger.debug(this, "mutualFunds feed group id = " + feedGroup.getMutualFundsFeedGroupID());

				trxValue = trxManager.getTrxByRefIDAndTrxType(String.valueOf(feedGroup.getMutualFundsFeedGroupID()),
						ICMSConstant.INSTANCE_MUTUAL_FUNDS_FEED_GROUP);

				vv = new OBMutualFundsFeedGroupTrxValue(trxValue);
				vv.setMutualFundsFeedGroup(feedGroup);

			}
			else {

				trxValue = trxManager.getTransaction(trxId);

				vv = new OBMutualFundsFeedGroupTrxValue(trxValue);

				if (vv.getReferenceID() != null) {
					DefaultLogger.debug(this, "reference id = " + vv.getReferenceID());

					IMutualFundsFeedGroup feedGroup = getMutualFundsFeedBusManager().getMutualFundsFeedGroup(
							Long.parseLong(vv.getReferenceID()));
					vv.setMutualFundsFeedGroup(feedGroup);

					if ((feedGroup != null) && (feedGroup.getFeedEntries() != null)) {
						DefaultLogger.debug(this, "number of actual feed entries for " + "the actual feed group = "
								+ feedGroup.getFeedEntries().length);
					}
					else {
						DefaultLogger.debug(this, "there is no actual feed group found");
					}

					vv.setMutualFundsFeedGroup(feedGroup);

				}

			}

			DefaultLogger.debug(this, "transaction found for mutualFunds group, trx ID is " + trxValue.getTransactionID());

			// Go get the staging records.

			if (vv.getStagingReferenceID() != null) {

				DefaultLogger.debug(this, "staging reference id = " + vv.getStagingReferenceID());

				IMutualFundsFeedGroup stagingFeedGroup = getStagingMutualFundsFeedBusManager().getMutualFundsFeedGroup(
						Long.parseLong(trxValue.getStagingReferenceID()));
				vv.setStagingMutualFundsFeedGroup(stagingFeedGroup);

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