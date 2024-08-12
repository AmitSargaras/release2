package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.trx.AbstractLeiDateValidationTrxOperation;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;

public class CheckerApproveUpdateLeiDateValidationOperation extends AbstractLeiDateValidationTrxOperation{
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateLeiDateValidationOperation() {
		super();
	}
	
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LEI_DATE_VALIDATION;
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
		ILeiDateValidationTrxValue trxValue = getLeiDateValidationTrxValue(anITrxValue);
		trxValue = updateActualLeiDateValidation(trxValue);
		trxValue = updateLeiDateValidationTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ILeiDateValidationTrxValue updateActualLeiDateValidation(ILeiDateValidationTrxValue anICCLeiDateValidationTrxValue)
			throws TrxOperationException {
		try {
			ILeiDateValidation staging = anICCLeiDateValidationTrxValue.getStagingLeiDateValidation();
			ILeiDateValidation actual = anICCLeiDateValidationTrxValue.getLeiDateValidation();

			ILeiDateValidation updatedLeiDateValidation = getLeiDateValidationBusManager().updateToWorkingCopy(actual, staging);
			anICCLeiDateValidationTrxValue.setLeiDateValidation(updatedLeiDateValidation);

			return anICCLeiDateValidationTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}
