package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerApproveUpdateCollateralRocOperation extends AbstractCollateralRocTrxOperation{

	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateCollateralRocOperation() {
		super();
	}
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_COLLATERAL_ROC;
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
		ICollateralRocTrxValue trxValue = getCollateralRocTrxValue(anITrxValue);
		trxValue = updateActualCollateralRoc(trxValue);
		trxValue = updateCollateralRocTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ICollateralRocTrxValue updateActualCollateralRoc(ICollateralRocTrxValue anICCCollateralRocTrxValue)
			throws TrxOperationException {
		try {
			ICollateralRoc staging = anICCCollateralRocTrxValue.getStagingCollateralRoc();
			ICollateralRoc actual = anICCCollateralRocTrxValue.getCollateralRoc();

			ICollateralRoc updatedCollateralRoc = getCollateralRocBusManager().updateToWorkingCopy(actual, staging);
			anICCCollateralRocTrxValue.setCollateralRoc(updatedCollateralRoc);

			return anICCCollateralRocTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
