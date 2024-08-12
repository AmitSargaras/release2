package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.bus.OBCreditApproval;

/**
 * @author govind.sahu
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateOperation extends AbstractCreditApprovalTrxOperation {
	/**
	 * Default Constructor
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
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CREDIT_APPROVAL;
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
		ICreditApprovalTrxValue trxValue = getCreditApprovalTrxValue(anITrxValue);
		trxValue = createStagingCreditApproval(trxValue);
		trxValue = updateActualCreditApproval(trxValue);
		trxValue = updateCreditApprovalTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ICreditApprovalTrxValue updateActualCreditApproval(ICreditApprovalTrxValue anICCCreditApprovalTrxValue)
			throws TrxOperationException {
		try {
			ICreditApproval staging = anICCCreditApprovalTrxValue.getStagingCreditApproval();
			//ICreditApproval actual = anICCCreditApprovalTrxValue.getCreditApproval();
			ICreditApproval actual = new OBCreditApproval();
			if(!(anICCCreditApprovalTrxValue.getReferenceID()==null))
			{
			actual.setId(Long.parseLong(anICCCreditApprovalTrxValue.getReferenceID()));
			}
			else{
				throw new CreditApprovalException("Reference Id Is Null");
			}
			ICreditApproval updatedCreditApproval = getCreditApprovalBusManager().updateToWorkingCopy(actual, staging);
			anICCCreditApprovalTrxValue.setCreditApproval(updatedCreditApproval);

			return anICCCreditApprovalTrxValue;
		}
		catch (Exception ex) {
			 ex.printStackTrace();
			  DefaultLogger.debug(this, "Failed Updating Actual CreditApproval" + ex);
			  throw new TrxOperationException(
					"Failed to Updating Actual Credit Approval");
			
		}


		
	}
}
