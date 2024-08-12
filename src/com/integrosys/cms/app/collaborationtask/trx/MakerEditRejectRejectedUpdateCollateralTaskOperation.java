package com.integrosys.cms.app.collaborationtask.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Mar 29, 2006 Time: 4:55:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class MakerEditRejectRejectedUpdateCollateralTaskOperation extends AbstractCollateralTaskTrxOperation {

	public MakerEditRejectRejectedUpdateCollateralTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECT_REJECTED_UPDATE_COLLATERAL_TASK;
	}

	/**
	 * Process the transaction 1. Create Staging record 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICollateralTaskTrxValue trxValue = createStagingCollateralTask(getCollateralTaskTrxValue(anITrxValue));
		trxValue = updateCollateralTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
