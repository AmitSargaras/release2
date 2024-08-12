package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Transaction Operation for checker approve collateral deletion transaction,
 * and transaction is allowed to be stp to host for the delete operation.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckerApproveDeleteCollateralStpPassedOperation extends AbstractCollateralTrxOperation {

	private static final long serialVersionUID = 1484088607693330566L;

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICollateralTrxValue trxValue = getCollateralTrxValue(value);

		ICollateral actualCollateral = trxValue.getCollateral();
		actualCollateral.setCollateralStatus(ICMSConstant.HOST_COL_STATUS_DELETED);
		actualCollateral.setStatus(ICMSConstant.STATE_DELETED);

		try {
			getActualCollateralBusManager().updateCollateral(actualCollateral);
		}
		catch (CollateralException e) {
			throw new TrxOperationException("Failed to update actual collateral for deletion, collateral ["
					+ actualCollateral + "]", e);
		}

		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_PASS_COL;
	}

}