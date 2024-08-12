/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/CheckerApproveUpdateOperation.java,v 1.3 2005/08/30 09:48:02 hshii Exp $
 */
package com.integrosys.cms.app.feed.trx.mutualfunds;

// java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedGroupException;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class CheckerApproveUpdateOperation extends AbstractMutualFundsTrxOperation {

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
		return ICMSConstant.ACTION_CHECKER_APPROVE_MUTUAL_FUNDS_FEED_GROUP;
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
		IMutualFundsFeedGroupTrxValue trxValue = getMutualFundsFeedGroupTrxValue(anITrxValue);
		trxValue = createStagingMutualFundsFeedGroup(trxValue);
		trxValue = updateActualMutualFundsFeedGroup(trxValue);
		trxValue = updateMutualFundsFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIMutualFundsFeedGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IMutualFundsFeedGroupTrxValue updateActualMutualFundsFeedGroup(IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			IMutualFundsFeedGroup staging = anIMutualFundsFeedGroupTrxValue.getStagingMutualFundsFeedGroup();
			IMutualFundsFeedGroup actual = anIMutualFundsFeedGroupTrxValue.getMutualFundsFeedGroup();

			IMutualFundsFeedGroup updatedFeedGroup = getMutualFundsFeedBusManager().updateToWorkingCopy(actual, staging);
			anIMutualFundsFeedGroupTrxValue.setMutualFundsFeedGroup(updatedFeedGroup);
			return anIMutualFundsFeedGroupTrxValue;
		}
		/*
		 * catch(ConcurrentUpdateException ex) { throw new
		 * TrxOperationException(ex); }
		 */catch (MutualFundsFeedGroupException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualMutualFundsFeedGroup(): " + ex.toString());
		}
	}
}