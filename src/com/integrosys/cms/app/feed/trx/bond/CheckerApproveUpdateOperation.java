/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/CheckerApproveUpdateOperation.java,v 1.3 2005/08/30 09:48:02 hshii Exp $
 */
package com.integrosys.cms.app.feed.trx.bond;

// java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/30 09:48:02 $ Tag: $Name: $
 */
public class CheckerApproveUpdateOperation extends AbstractBondTrxOperation {

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
		return ICMSConstant.ACTION_CHECKER_APPROVE_BOND_FEED_GROUP;
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
		IBondFeedGroupTrxValue trxValue = getBondFeedGroupTrxValue(anITrxValue);
		trxValue = createStagingBondFeedGroup(trxValue);
		trxValue = updateActualBondFeedGroup(trxValue);
		trxValue = updateBondFeedGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIBondFeedGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IBondFeedGroupTrxValue updateActualBondFeedGroup(IBondFeedGroupTrxValue anIBondFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			IBondFeedGroup staging = anIBondFeedGroupTrxValue.getStagingBondFeedGroup();
			IBondFeedGroup actual = anIBondFeedGroupTrxValue.getBondFeedGroup();

			IBondFeedGroup updatedFeedGroup = getBondFeedBusManager().updateToWorkingCopy(actual, staging);
			anIBondFeedGroupTrxValue.setBondFeedGroup(updatedFeedGroup);
			return anIBondFeedGroupTrxValue;
		}
		/*
		 * catch(ConcurrentUpdateException ex) { throw new
		 * TrxOperationException(ex); }
		 */catch (BondFeedGroupException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualBondFeedGroup(): " + ex.toString());
		}
	}
}