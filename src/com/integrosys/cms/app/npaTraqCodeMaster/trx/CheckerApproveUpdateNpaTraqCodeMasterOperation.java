package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;

/**
 * @author Santosh.Sonmankar
 * Checker approve Operation to approve update made by maker
 */
public class CheckerApproveUpdateNpaTraqCodeMasterOperation extends AbstractNpaTraqCodeMasterTrxOperation{
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateNpaTraqCodeMasterOperation() {
		super();
	}
	
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_NPA_TRAQ_CODE_MASTER;
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
		INpaTraqCodeMasterTrxValue trxValue = getNpaTraqCodeMasterTrxValue(anITrxValue);
		trxValue = updateActualNpaTraqCodeMaster(trxValue);
		trxValue = updateNpaTraqCodeMasterTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private INpaTraqCodeMasterTrxValue updateActualNpaTraqCodeMaster(INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue)
			throws TrxOperationException {
		try {
			INpaTraqCodeMaster staging = anICCNpaTraqCodeMasterTrxValue.getStagingNpaTraqCodeMaster();
			INpaTraqCodeMaster actual = anICCNpaTraqCodeMasterTrxValue.getNpaTraqCodeMaster();

			INpaTraqCodeMaster updatedNpaTraqCodeMaster = getNpaTraqCodeMasterBusManager().updateToWorkingCopy(actual, staging);
			anICCNpaTraqCodeMasterTrxValue.setNpaTraqCodeMaster(updatedNpaTraqCodeMaster);

			return anICCNpaTraqCodeMasterTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}
