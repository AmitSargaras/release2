/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/ReadBondGroupOperation.java,v 1.5 2003/08/19 07:52:43 btchng Exp $
 */
package com.integrosys.cms.app.digitalLibrary.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

/**
 * This operation allows to read bond feed group transaction using transaction
 * id or primary key
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/19 07:52:43 $ Tag: $Name: $
 */
public class ReadDigitalLibraryOperation extends AbstractDigitalLibraryTrxOperation implements ITrxReadOperation {

	/**
	 * Defaulc Constructor
	 */
	public ReadDigitalLibraryOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_DIGITAL_LIBRARY_GROUP;
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

		IDigitalLibraryTrxValue vv = (IDigitalLibraryTrxValue) value;

		try {

			DefaultLogger.debug(this, "reference ID = " + vv.getReferenceID());
			DefaultLogger.debug(this, "trx id = " + vv.getTransactionID());

			String trxId = vv.getTransactionID();

			ICMSTrxValue trxValue = null;

			SBCMSTrxManager trxManager = getTrxManager();

			// Get the actual records first.

			if ((trxId == null) || trxId.equals("")) {

				IDigitalLibraryGroup feedGroup = getDigitalLibraryBusManager().getDigitalLibraryGroup(ICMSConstant.DIGITAL_LIBRARY_GROUP_TYPE);

				DefaultLogger.debug(this, "bond feed group id = " + feedGroup.getDigitalLibraryGroupID());

				trxValue = trxManager.getTrxByRefIDAndTrxType(String.valueOf(feedGroup.getDigitalLibraryGroupID()),
						ICMSConstant.INSTANCE_DIGITAL_LIBRARY_GROUP);

				vv = new OBDigitalLibraryTrxValue(trxValue);
				vv.setDigitalLibraryGroup(feedGroup);

			}
			else {

				trxValue = trxManager.getTransaction(trxId);

				vv = new OBDigitalLibraryTrxValue(trxValue);

				if (vv.getReferenceID() != null) {
					DefaultLogger.debug(this, "reference id = " + vv.getReferenceID());

					IDigitalLibraryGroup feedGroup = getDigitalLibraryBusManager().getDigitalLibraryGroup(
							Long.parseLong(vv.getReferenceID()));
					vv.setDigitalLibraryGroup(feedGroup);

					if ((feedGroup != null) && (feedGroup.getFeedEntries() != null)) {
						DefaultLogger.debug(this, "number of actual feed entries for " + "the actual feed group = "
								+ feedGroup.getFeedEntries().length);
					}
					else {
						DefaultLogger.debug(this, "there is no actual feed group found");
					}

					vv.setDigitalLibraryGroup(feedGroup);

				}

			}

			DefaultLogger.debug(this, "transaction found for bond group, trx ID is " + trxValue.getTransactionID());

			// Go get the staging records.

			if (vv.getStagingReferenceID() != null) {

				DefaultLogger.debug(this, "staging reference id = " + vv.getStagingReferenceID());

				IDigitalLibraryGroup stagingFeedGroup = getStagingDigitalLibraryBusManager().getDigitalLibraryGroup(
						Long.parseLong(trxValue.getStagingReferenceID()));
				vv.setStagingDigitalLibraryGroup(stagingFeedGroup);

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