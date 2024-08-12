/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/CheckerApproveUpdateOperation.java,v 1.8 2005/08/30 09:48:22 hshii Exp $
 */
package com.integrosys.cms.app.feed.trx.forex;

// java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/30 09:48:22 $ Tag: $Name: $
 */
public class CheckerApproveUpdateOperation extends AbstractForexTrxOperation {

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
		return ICMSConstant.ACTION_CHECKER_APPROVE_FOREX_FEED_GROUP;
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
		IForexFeedGroupTrxValue trxValue = getForexFeedGroupTrxValue(anITrxValue);
		trxValue = createStagingForexFeedGroup(trxValue);
		trxValue = updateActualForexFeedGroup(trxValue);
		trxValue = updateForexFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIForexFeedGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IForexFeedGroupTrxValue updateActualForexFeedGroup(IForexFeedGroupTrxValue anIForexFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			IForexFeedGroup staging = anIForexFeedGroupTrxValue.getStagingForexFeedGroup();
			IForexFeedGroup actual = anIForexFeedGroupTrxValue.getForexFeedGroup();

			IForexFeedGroup updatedActualForexFeedGroup = getForexFeedBusManager()
					.updateToWorkingCopy(actual, staging);

			anIForexFeedGroupTrxValue.setForexFeedGroup(updatedActualForexFeedGroup);
			return anIForexFeedGroupTrxValue;
		}
		/*
		 * catch(ConcurrentUpdateException ex) { throw new
		 * TrxOperationException(ex); }
		 */catch (ForexFeedGroupException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualForexFeedGroup(): " + ex.toString());
		}
	}
}