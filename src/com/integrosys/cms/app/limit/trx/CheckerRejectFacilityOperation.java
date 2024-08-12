package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * transaction operation for checker reject submitted facility master
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckerRejectFacilityOperation extends AbstractFacilityTrxOperation {

	public String getOperationName() {
		return FacilityTrxController.ACTION_CHECKER_REJECT;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		IFacilityTrxValue trxValue = (IFacilityTrxValue) value;

		trxValue = updateTransaction(trxValue);

		OBCMSTrxResult trxResult = new OBCMSTrxResult();
		trxResult.setTrxValue(trxValue);

		return trxResult;
	}

}
