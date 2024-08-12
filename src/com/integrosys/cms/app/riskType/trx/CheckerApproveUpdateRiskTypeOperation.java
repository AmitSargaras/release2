package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.riskType.bus.IRiskType;

/**
 * @author Santosh.Sonmankar
 * Checker approve Operation to approve update made by maker
 */
public class CheckerApproveUpdateRiskTypeOperation extends AbstractRiskTypeTrxOperation{
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateRiskTypeOperation() {
		super();
	}
	
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_RISK_TYPE;
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
		IRiskTypeTrxValue trxValue = getRiskTypeTrxValue(anITrxValue);
		trxValue = updateActualRiskType(trxValue);
		trxValue = updateRiskTypeTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IRiskTypeTrxValue updateActualRiskType(IRiskTypeTrxValue anICCRiskTypeTrxValue)
			throws TrxOperationException {
		try {
			IRiskType staging = anICCRiskTypeTrxValue.getStagingRiskType();
			IRiskType actual = anICCRiskTypeTrxValue.getRiskType();

			IRiskType updatedRiskType = getRiskTypeBusManager().updateToWorkingCopy(actual, staging);
			anICCRiskTypeTrxValue.setRiskType(updatedRiskType);

			return anICCRiskTypeTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}
