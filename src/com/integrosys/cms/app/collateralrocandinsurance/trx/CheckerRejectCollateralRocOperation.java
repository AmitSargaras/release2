package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerRejectCollateralRocOperation extends AbstractCollateralRocTrxOperation {

	public CheckerRejectCollateralRocOperation() {
		super();
	}
	/**
	 * Get the operation name of the current operation
	 *
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_ROC;
	}
	/**
	 * Process the transaction 1. Update the transaction record
	 * 
	 * @param anITrxValue
	 *            - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 *             if encounters any error during the processing of the
	 *             transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICollateralRocTrxValue trxValue = super.getCollateralRocTrxValue(anITrxValue);
		trxValue = super.updateCollateralRocTrx(trxValue);
		return super.prepareResult(trxValue);
	}
}
