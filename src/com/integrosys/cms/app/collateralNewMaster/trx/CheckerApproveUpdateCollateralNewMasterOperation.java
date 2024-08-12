package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;

/**
 * @author abhijit.rudrakshawar
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateCollateralNewMasterOperation extends AbstractCollateralNewMasterTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateCollateralNewMasterOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_COLLATERAL_NEW_MASTER;
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
		ICollateralNewMasterTrxValue trxValue = getCollateralNewMasterTrxValue(anITrxValue);
		trxValue = updateActualCollateralNewMaster(trxValue);
		trxValue = updateCollateralNewMasterTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ICollateralNewMasterTrxValue updateActualCollateralNewMaster(ICollateralNewMasterTrxValue anICCCollateralNewMasterTrxValue)
			throws TrxOperationException {
		try {
			ICollateralNewMaster staging = anICCCollateralNewMasterTrxValue.getStagingCollateralNewMaster();
			ICollateralNewMaster actual = anICCCollateralNewMasterTrxValue.getCollateralNewMaster();

			ICollateralNewMaster updatedCollateralNewMaster = getCollateralNewMasterBusManager().updateToWorkingCopy(actual, staging);
			anICCCollateralNewMasterTrxValue.setCollateralNewMaster(updatedCollateralNewMaster);

			return anICCCollateralNewMasterTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
