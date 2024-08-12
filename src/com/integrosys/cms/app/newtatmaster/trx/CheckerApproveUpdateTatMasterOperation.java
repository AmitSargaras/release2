package com.integrosys.cms.app.newtatmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;

public class CheckerApproveUpdateTatMasterOperation extends AbstractTatMasterTrxOperation{
	

	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateTatMasterOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_TAT_MASTER;
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
		ITatMasterTrxValue trxValue = getTatMasterTrxValue(anITrxValue);
		trxValue = updateActualTatMaster(trxValue);
		trxValue = updateTatMasterTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ITatMasterTrxValue updateActualTatMaster(ITatMasterTrxValue anICCTatMasterTrxValue)
			throws TrxOperationException {
		try {
			INewTatMaster staging = anICCTatMasterTrxValue.getStagingtatMaster();
			INewTatMaster actual = anICCTatMasterTrxValue.getTatMaster();

			INewTatMaster updatedTatMaster = getTatMasterBusManager().updateToWorkingCopy(actual, staging);
			anICCTatMasterTrxValue.setTatMaster(updatedTatMaster);

			return anICCTatMasterTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}


}
