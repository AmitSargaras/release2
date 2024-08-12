package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.limit.bus.FacilityMasterReplicationUtils;

/**
 * Transaction operation for system approve submitted stp facility master
 *
 * @author Andy Wong
 *
 */
public class SystemApproveFacilityOperation extends AbstractFacilityTrxOperation {

	public String getOperationName() {
		return FacilityTrxController.ACTION_SYSTEM_APPROVE;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		IFacilityTrxValue trxValue = (IFacilityTrxValue) value;

		trxValue = updateTransaction(trxValue);
		OBCMSTrxResult trxResult = new OBCMSTrxResult();
		trxResult.setTrxValue(trxValue);

		return trxResult;
	}
}