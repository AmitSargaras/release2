/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/CheckerApproveUpdateOperation.java,v 1.3 2005/08/30 09:48:02 hshii Exp $
 */
package com.integrosys.cms.app.approvalmatrix.trx;

// java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.approvalmatrix.bus.ApprovalMatrixException;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/30 09:48:02 $ Tag: $Name: $
 */
public class CheckerApproveUpdateOperation extends AbstractApprovalMatrixTrxOperation {

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
		return ICMSConstant.ACTION_CHECKER_APPROVE_APROVAL_MATRIX_GROUP;
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
		IApprovalMatrixTrxValue trxValue = getApprovalMatrixGroupTrxValue(anITrxValue);
		//trxValue = createStagingApprovalMatrixGroup(trxValue);
		trxValue = updateActualApprovalMatrixGroup(trxValue);
		trxValue = updateApprovalMatrixGroupTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual checklist from the staging checklist
	 * @param anIApprovalMatrixGroupTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the checklist trx value
	 * @throws TrxOperationException on errors
	 */
	private IApprovalMatrixTrxValue updateActualApprovalMatrixGroup(IApprovalMatrixTrxValue anIApprovalMatrixGroupTrxValue)
			throws TrxOperationException {
		try {
			IApprovalMatrixGroup staging = anIApprovalMatrixGroupTrxValue.getStagingApprovalMatrixGroup();
			IApprovalMatrixGroup actual = anIApprovalMatrixGroupTrxValue.getApprovalMatrixGroup();

			IApprovalMatrixGroup updatedFeedGroup = getApprovalMatrixBusManager().updateToWorkingCopy(actual, staging);
			anIApprovalMatrixGroupTrxValue.setApprovalMatrixGroup(updatedFeedGroup);
			return anIApprovalMatrixGroupTrxValue;
		}
		/*
		 * catch(ConcurrentUpdateException ex) { throw new
		 * TrxOperationException(ex); }
		 */catch (ApprovalMatrixException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualApprovalMatrixGroup(): " + ex.toString());
		}
	}
}