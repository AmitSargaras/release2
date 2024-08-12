/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stockindex/CheckerApproveUpdateOperation.java,v 1.3 2005/08/30 09:48:56 hshii Exp $
 */
package com.integrosys.cms.app.feed.trx.stockindex;

// java

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/30 09:48:56 $ Tag: $Name: $
 */
public class CheckerApproveUpdateOperation extends AbstractStockIndexTrxOperation {

	protected static Log logger = LogFactory.getLog(CheckerApproveUpdateOperation.class);

	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_STOCK_INDEX_FEED_GROUP;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {

		logger.debug("###############################" + ((ICMSTrxValue) anITrxValue).getRemarks());

		IStockIndexFeedGroupTrxValue trxValue = getStockIndexFeedGroupTrxValue(anITrxValue);
		trxValue = createStagingStockIndexFeedGroup(trxValue);
		trxValue = updateActualStockIndexFeedGroup(trxValue);
		trxValue = updateStockIndexFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIStockIndexFeedGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	protected IStockIndexFeedGroupTrxValue updateActualStockIndexFeedGroup(
			IStockIndexFeedGroupTrxValue anIStockIndexFeedGroupTrxValue) throws TrxOperationException {
		try {
			IStockIndexFeedGroup staging = anIStockIndexFeedGroupTrxValue.getStagingStockIndexFeedGroup();
			IStockIndexFeedGroup actual = anIStockIndexFeedGroupTrxValue.getStockIndexFeedGroup();

			IStockIndexFeedGroup updatedGroup = getStockIndexFeedBusManager().updateToWorkingCopy(actual, staging);

			anIStockIndexFeedGroupTrxValue.setStockIndexFeedGroup(updatedGroup);
			return anIStockIndexFeedGroupTrxValue;
		}
		catch (Exception ex) {
			logger.error("error invoking 'updateActualStockIndexFeedGroup'", ex);
			throw new TrxOperationException("Exception in updateActualStockIndexFeedGroup(): " + ex.toString());
		}
	}

}