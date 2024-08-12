package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Transaction operation for maker to update and submit existing facility master
 * 
 * @author Chong Jun Yong
 * 
 */
public class MakerUpdateFacilityOperation extends AbstractFacilityTrxOperation {

	public String getOperationName() {
		return FacilityTrxController.ACTION_MAKER_UPDATE;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		IFacilityTrxValue trxValue = (IFacilityTrxValue) value;

		trxValue = createStagingFacilityMaster(trxValue);
		trxValue = updateTransaction(trxValue);

		OBCMSTrxResult trxResult = new OBCMSTrxResult();
		trxResult.setTrxValue(trxValue);

		return trxResult;
	}

}
