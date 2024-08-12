package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;

/**
 * @author abhijit.rudrakshawar
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateFacilityNewMasterOperation extends AbstractFacilityNewMasterTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateFacilityNewMasterOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_FACILITY_NEW_MASTER;
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
		IFacilityNewMasterTrxValue trxValue = getFacilityNewMasterTrxValue(anITrxValue);
		trxValue = updateActualFacilityNewMaster(trxValue);
		trxValue = updateFacilityNewMasterTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IFacilityNewMasterTrxValue updateActualFacilityNewMaster(IFacilityNewMasterTrxValue anICCFacilityNewMasterTrxValue)
			throws TrxOperationException {
		try {
			IFacilityNewMaster staging = anICCFacilityNewMasterTrxValue.getStagingFacilityNewMaster();
			IFacilityNewMaster actual = anICCFacilityNewMasterTrxValue.getFacilityNewMaster();

			IFacilityNewMaster updatedFacilityNewMaster = getFacilityNewMasterBusManager().updateToWorkingCopy(actual, staging);
			anICCFacilityNewMasterTrxValue.setFacilityNewMaster(updatedFacilityNewMaster);

			return anICCFacilityNewMasterTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
