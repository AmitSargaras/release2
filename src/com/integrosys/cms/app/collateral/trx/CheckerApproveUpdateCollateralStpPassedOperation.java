package com.integrosys.cms.app.collateral.trx;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Transaction Operation for checker approve transaction, and transaction is
 * allowed to be stp to host and pass the stp validation.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckerApproveUpdateCollateralStpPassedOperation extends AbstractCollateralTrxOperation {

	private static final long serialVersionUID = 1203129879615200986L;

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICollateralTrxValue trxValue = getCollateralTrxValue(value);

		if (StringUtils.isNotBlank(trxValue.getReferenceID())) {
			trxValue = super.updateActualCollateral(trxValue);
		}
		else {
			trxValue = super.createActualCollateral(trxValue);
		}

		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_PASS_COL;
	}

}
