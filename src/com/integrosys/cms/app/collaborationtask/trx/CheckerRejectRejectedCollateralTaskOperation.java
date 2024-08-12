package com.integrosys.cms.app.collaborationtask.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Mar 29, 2006 Time: 2:26:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheckerRejectRejectedCollateralTaskOperation extends AbstractCollateralTaskTrxOperation {

	public CheckerRejectRejectedCollateralTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_REJECTED_COLLATERAL_TASK;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICollateralTaskTrxValue trxValue = super.getCollateralTaskTrxValue(anITrxValue);
		trxValue = super.createStagingCollateralTask(trxValue);
		trxValue = super.updateCollateralTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
