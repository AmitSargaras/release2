/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stock/CheckerApproveUpdateOperation.java,v 1.3 2005/08/30 09:48:45 hshii Exp $
 */
package com.integrosys.cms.app.feed.trx.stock;

// java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/30 09:48:45 $ Tag: $Name: $
 */
public class CheckerApproveUpdateOperation extends AbstractStockTrxOperation {

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
		return ICMSConstant.ACTION_CHECKER_APPROVE_STOCK_FEED_GROUP;
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
		IStockFeedGroupTrxValue trxValue = getStockFeedGroupTrxValue(anITrxValue);
		trxValue = createStagingStockFeedGroup(trxValue);
		trxValue = updateActualStockFeedGroup(trxValue);
		trxValue = updateStockFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIStockFeedGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IStockFeedGroupTrxValue updateActualStockFeedGroup(IStockFeedGroupTrxValue anIStockFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			
			IStockFeedGroup staging = anIStockFeedGroupTrxValue.getStagingStockFeedGroup();
			IStockFeedGroup actual = anIStockFeedGroupTrxValue.getStockFeedGroup();

			IStockFeedGroup updatedFeedGroup = getStockFeedBusManager().updateToWorkingCopy(actual, staging);
			anIStockFeedGroupTrxValue.setStockFeedGroup(updatedFeedGroup);
			return anIStockFeedGroupTrxValue;
		}
		/*
		 * catch(ConcurrentUpdateException ex) { throw new
		 * TrxOperationException(ex); }
		 */catch (StockFeedGroupException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualStockFeedGroup(): " + ex.toString());
		}
	}
}