/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/propertyindex/ReadPropertyIndexGroupOperation.java,v 1.1 2003/08/20 10:59:58 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.propertyindex;

//java

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.propertyindex.SBPropertyIndexFeedBusManager;
import com.integrosys.cms.app.feed.bus.propertyindex.SBPropertyIndexFeedBusManagerHome;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:58 $ Tag: $Name: $
 */
public class ReadPropertyIndexGroupOperation extends AbstractPropertyIndexTrxOperation implements ITrxReadOperation {

	/**
	 * Defaulc Constructor
	 */
	public ReadPropertyIndexGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_PROPERTY_INDEX_FEED_GROUP;
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

		IPropertyIndexFeedGroupTrxValue vv = (IPropertyIndexFeedGroupTrxValue) value;

		try {

			DefaultLogger.debug(this, "reference ID = " + vv.getReferenceID());
			DefaultLogger.debug(this, "trx id = " + vv.getTransactionID());

			String trxId = vv.getTransactionID();

			ICMSTrxValue trxValue = null;

			SBCMSTrxManager trxManager = getTrxManager();

			// Get the actual records first.

			if ((trxId == null) || trxId.equals("")) {

				IPropertyIndexFeedGroup feedGroup = getPropertyIndexFeedBusManager().getPropertyIndexFeedGroup(
						ICMSConstant.PROPERTY_INDEX_FEED_GROUP_TYPE, vv.getPropertyIndexFeedGroup().getSubType());

				DefaultLogger.debug(this, "property index feed group id = " + feedGroup.getPropertyIndexFeedGroupID());

				trxValue = trxManager.getTrxByRefIDAndTrxType(String.valueOf(feedGroup.getPropertyIndexFeedGroupID()),
						ICMSConstant.INSTANCE_PROPERTY_INDEX_FEED_GROUP);

				vv = new OBPropertyIndexFeedGroupTrxValue(trxValue);
				vv.setPropertyIndexFeedGroup(feedGroup);

			}
			else {

				trxValue = trxManager.getTransaction(trxId);

				vv = new OBPropertyIndexFeedGroupTrxValue(trxValue);

				if (vv.getReferenceID() != null) {
					DefaultLogger.debug(this, "reference id = " + vv.getReferenceID());

					IPropertyIndexFeedGroup feedGroup = getPropertyIndexFeedBusManager().getPropertyIndexFeedGroup(
							Long.parseLong(vv.getReferenceID()));
					vv.setPropertyIndexFeedGroup(feedGroup);

					if ((feedGroup != null) && (feedGroup.getFeedEntries() != null)) {
						DefaultLogger.debug(this, "number of actual feed entries for " + "the actual feed group = "
								+ feedGroup.getFeedEntries().length);
					}
					else {
						DefaultLogger.debug(this, "there is no actual feed group found");
					}

					vv.setPropertyIndexFeedGroup(feedGroup);

				}

			}

			DefaultLogger.debug(this, "transaction found for property index group, trx ID is "
					+ trxValue.getTransactionID());

			// Go get the staging records.

			if (vv.getStagingReferenceID() != null) {

				DefaultLogger.debug(this, "staging reference id = " + vv.getStagingReferenceID());

				IPropertyIndexFeedGroup stagingFeedGroup = getStagingPropertyIndexFeedBusManager()
						.getPropertyIndexFeedGroup(Long.parseLong(trxValue.getStagingReferenceID()));
				vv.setStagingPropertyIndexFeedGroup(stagingFeedGroup);

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

	// helper methods follow
	private SBPropertyIndexFeedBusManager getPropertyIndexFeedBusManager() {

		SBPropertyIndexFeedBusManager mgr = (SBPropertyIndexFeedBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_PROPERTY_INDEX_FEED_BUS_MANAGER_JNDI, SBPropertyIndexFeedBusManagerHome.class
						.getName());

		if (mgr == null) {
			DefaultLogger.error(this, "Unable to get property index feed manager");
		}

		return mgr;
	}

	private SBPropertyIndexFeedBusManager getStagingPropertyIndexFeedBusManager() {

		SBPropertyIndexFeedBusManager mgr = (SBPropertyIndexFeedBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_PROPERTY_INDEX_FEED_BUS_MANAGER_JNDI_STAGING,
				SBPropertyIndexFeedBusManagerHome.class.getName());

		if (mgr == null) {
			DefaultLogger.error(this, "Unable to get property index feed manager");
		}

		return mgr;
	}
}