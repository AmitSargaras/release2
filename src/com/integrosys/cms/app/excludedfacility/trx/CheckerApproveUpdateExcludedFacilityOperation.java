package com.integrosys.cms.app.excludedfacility.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;

public class CheckerApproveUpdateExcludedFacilityOperation extends AbstractExcludedFacilityTrxOperation{

	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateExcludedFacilityOperation() {
		super();
	}
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_EXCLUDED_FACILITY;
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
		IExcludedFacilityTrxValue trxValue = getExcludedFacilityTrxValue(anITrxValue);
		trxValue = updateActualExcludedFacility(trxValue);
		trxValue = updateExcludedFacilityTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IExcludedFacilityTrxValue updateActualExcludedFacility(IExcludedFacilityTrxValue anICCExcludedFacilityTrxValue)
			throws TrxOperationException {
		try {
			IExcludedFacility staging = anICCExcludedFacilityTrxValue.getStagingExcludedFacility();
			IExcludedFacility actual = anICCExcludedFacilityTrxValue.getExcludedFacility();

			IExcludedFacility updatedExcludedFacility = getExcludedFacilityBusManager().updateToWorkingCopy(actual, staging);
			anICCExcludedFacilityTrxValue.setExcludedFacility(updatedExcludedFacility);

			return anICCExcludedFacilityTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
