package com.integrosys.cms.app.limit.trx;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Transaction operation for maker create and submit facility master to checker
 * 
 * @author Chong Jun Yong
 * 
 */
public class MakerCreateFacilityOperation extends AbstractFacilityTrxOperation {

	public String getOperationName() {
		return FacilityTrxController.ACTION_MAKER_CREATE;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		IFacilityTrxValue trxValue = (IFacilityTrxValue) value;

		trxValue = createStagingFacilityMaster(trxValue);

		if (StringUtils.isNotBlank(trxValue.getTransactionID())) {
			trxValue = updateTransaction(trxValue);
		}
		else {
			trxValue = createTransaction(trxValue);
		}

		OBCMSTrxResult trxResult = new OBCMSTrxResult();
		trxResult.setTrxValue(trxValue);

		return trxResult;
	}

}
