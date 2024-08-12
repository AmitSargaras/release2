/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/ReadForexGroupOperation.java,v 1.11 2003/08/19 07:52:43 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.forex;

// java

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.11 $
 * @since $Date: 2003/08/19 07:52:43 $ Tag: $Name: $
 */
public class ReadForexGroupOperation extends AbstractForexTrxOperation implements ITrxReadOperation {

	/**
	 * Default Constructor
	 */
	public ReadForexGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_FOREX_FEED_GROUP;
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

		IForexFeedGroupTrxValue vv = (IForexFeedGroupTrxValue) value;

		try {

			DefaultLogger.debug(this, "reference ID = " + vv.getReferenceID());
			DefaultLogger.debug(this, "trx id = " + vv.getTransactionID());

			String trxId = vv.getTransactionID();

			ICMSTrxValue trxValue = null;

			SBCMSTrxManager trxManager = getTrxManager();

			// Get the actual records first.

			if ((trxId == null) || trxId.equals("")) {

				IForexFeedGroup feedGroup = getForexFeedBusManager().getForexFeedGroup(
						ICMSConstant.FOREX_FEED_GROUP_TYPE);

				DefaultLogger.debug(this, "forex feed group id = " + feedGroup.getForexFeedGroupID());

				trxValue = trxManager.getTrxByRefIDAndTrxType(String.valueOf(feedGroup.getForexFeedGroupID()),
						ICMSConstant.INSTANCE_FOREX_FEED_GROUP);

				vv = new OBForexFeedGroupTrxValue(trxValue);
				vv.setForexFeedGroup(feedGroup);

			}
			else {

				trxValue = trxManager.getTransaction(trxId);

				vv = new OBForexFeedGroupTrxValue(trxValue);

				if (vv.getReferenceID() != null) {
					DefaultLogger.debug(this, "reference id = " + vv.getReferenceID());

					IForexFeedGroup feedGroup = getForexFeedBusManager().getForexFeedGroup(
							Long.parseLong(vv.getReferenceID()));
					vv.setForexFeedGroup(feedGroup);

					if ((feedGroup != null) && (feedGroup.getFeedEntries() != null)) {
						DefaultLogger.debug(this, "number of actual feed entries for " + "the actual feed group = "
								+ feedGroup.getFeedEntries().length);
					}
					else {
						DefaultLogger.debug(this, "there is no actual feed group found");
					}

					vv.setForexFeedGroup(feedGroup);

				}

			}

			DefaultLogger.debug(this, "transaction found for forex group, trx ID is " + trxValue.getTransactionID());

			// Go get the staging records.

			if (vv.getStagingReferenceID() != null) {

				DefaultLogger.debug(this, "staging reference id = " + vv.getStagingReferenceID());

				IForexFeedGroup stagingFeedGroup = getStagingForexFeedBusManager().getForexFeedGroup(
						Long.parseLong(trxValue.getStagingReferenceID()));
				vv.setStagingForexFeedGroup(stagingFeedGroup);

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