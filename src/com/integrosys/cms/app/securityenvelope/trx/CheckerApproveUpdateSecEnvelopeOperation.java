package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.SecEnvelopeException;

/**
 * Title: CLIMS Description: Copyright: Integro Technologies Sdn Bhd Author:
 * Erene Wong Date: Feb 07, 2010
 */

public class CheckerApproveUpdateSecEnvelopeOperation extends AbstractSecEnvelopeTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateSecEnvelopeOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_SECENV;
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
		ISecEnvelopeTrxValue trxValue = getSecEnvelopeTrxValue(anITrxValue);
        trxValue = updateActualSecEnvelope(trxValue);
		trxValue = updateSecEnvelopeTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ISecEnvelopeTrxValue updateActualSecEnvelope(ISecEnvelopeTrxValue anICCSecEnvelopeTrxValue)
			throws TrxOperationException {
		try {
			ISecEnvelope staging = anICCSecEnvelopeTrxValue.getStagingSecEnvelope();
			ISecEnvelope actual = anICCSecEnvelopeTrxValue.getSecEnvelope();

			ISecEnvelope updatedSecEnvelope = getSecEnvelopeBusManager().updateToWorkingCopy(actual, staging);
			anICCSecEnvelopeTrxValue.setSecEnvelope(updatedSecEnvelope);

			return anICCSecEnvelopeTrxValue;
		}
		catch (SecEnvelopeException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualSecEnvelope(): " + ex.toString());
		}
	}
    
}
