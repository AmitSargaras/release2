package com.integrosys.cms.app.relationshipmgr.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author dattatray.thorat
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateRelationshipMgrOperation extends AbstractRelationshipMgrTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateRelationshipMgrOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_RELATIONSHIP_MGR;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		
		IRelationshipMgrTrxValue trxValue = getRelationshipMgrTrxValue(anITrxValue);
		trxValue = updateActualRelationshipMgr(trxValue);
		trxValue = updateRelationshipMgrTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IRelationshipMgrTrxValue updateActualRelationshipMgr(IRelationshipMgrTrxValue anICCRelationshipMgrTrxValue)
			throws TrxOperationException {
		try {
			IRelationshipMgr staging = anICCRelationshipMgrTrxValue.getStagingRelationshipMgr();
			IRelationshipMgr actual = anICCRelationshipMgrTrxValue.getRelationshipMgr();

			IRelationshipMgr updatedRelationshipMgr = getRelationshipMgrBusManager().updateToWorkingCopy(actual, staging);
			anICCRelationshipMgrTrxValue.setRelationshipMgr(updatedRelationshipMgr);

			return anICCRelationshipMgrTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
