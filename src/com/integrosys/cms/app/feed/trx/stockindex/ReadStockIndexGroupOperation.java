/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stockindex/ReadStockIndexGroupOperation.java,v 1.3 2003/08/19 07:52:43 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.stockindex;

// java

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/19 07:52:43 $ Tag: $Name: $
 */
public class ReadStockIndexGroupOperation extends AbstractStockIndexTrxOperation implements ITrxReadOperation {

	/**
	 * Defaulc Constructor
	 */
	public ReadStockIndexGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_STOCK_INDEX_FEED_GROUP;
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

		IStockIndexFeedGroupTrxValue vv = (IStockIndexFeedGroupTrxValue) value;

		try {

			DefaultLogger.debug(this, "reference ID = " + vv.getReferenceID());
			DefaultLogger.debug(this, "trx id = " + vv.getTransactionID());

			String trxId = vv.getTransactionID();

			ICMSTrxValue trxValue = null;

			SBCMSTrxManager trxManager = getTrxManager();

			// Get the actual records first.

			if ((trxId == null) || trxId.equals("")) {

				IStockIndexFeedGroup feedGroup = getStockIndexFeedBusManager().getStockIndexFeedGroup(
						ICMSConstant.STOCK_INDEX_FEED_GROUP_TYPE, vv.getStockIndexFeedGroup().getSubType());

				DefaultLogger.debug(this, "stock index feed group id = " + feedGroup.getStockIndexFeedGroupID());

				trxValue = trxManager.getTrxByRefIDAndTrxType(String.valueOf(feedGroup.getStockIndexFeedGroupID()),
						ICMSConstant.INSTANCE_STOCK_INDEX_FEED_GROUP);

				vv = new OBStockIndexFeedGroupTrxValue(trxValue);
				vv.setStockIndexFeedGroup(feedGroup);

			}
			else {

				trxValue = trxManager.getTransaction(trxId);

				vv = new OBStockIndexFeedGroupTrxValue(trxValue);

				if (vv.getReferenceID() != null) {
					DefaultLogger.debug(this, "reference id = " + vv.getReferenceID());

					IStockIndexFeedGroup feedGroup = getStockIndexFeedBusManager().getStockIndexFeedGroup(
							Long.parseLong(vv.getReferenceID()));
					vv.setStockIndexFeedGroup(feedGroup);

					if ((feedGroup != null) && (feedGroup.getFeedEntries() != null)) {
						DefaultLogger.debug(this, "number of actual feed entries for " + "the actual feed group = "
								+ feedGroup.getFeedEntries().length);
					}
					else {
						DefaultLogger.debug(this, "there is no actual feed group found");
					}

					vv.setStockIndexFeedGroup(feedGroup);

				}

			}

			DefaultLogger.debug(this, "transaction found for stock index group, trx ID is "
					+ trxValue.getTransactionID());

			// Go get the staging records.

			if (vv.getStagingReferenceID() != null) {

				DefaultLogger.debug(this, "staging reference id = " + vv.getStagingReferenceID());

				IStockIndexFeedGroup stagingFeedGroup = getStagingStockIndexFeedBusManager().getStockIndexFeedGroup(
						Long.parseLong(trxValue.getStagingReferenceID()));
				vv.setStagingStockIndexFeedGroup(stagingFeedGroup);

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