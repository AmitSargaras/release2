package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Transaction operation for maker to close facility transaction
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 * 
 */
public class MakerCloseFacilityOperation extends AbstractFacilityTrxOperation {

	private static final String DEFAULT_OPERATION_NAME = FacilityTrxController.ACTION_MAKER_CLOSE;

	private String operationName;

	public MakerCloseFacilityOperation() {
		operationName = DEFAULT_OPERATION_NAME;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		IFacilityTrxValue trxValue = (IFacilityTrxValue) value;

		trxValue = updateTransaction(trxValue);

		OBCMSTrxResult trxResult = new OBCMSTrxResult();
		trxResult.setTrxValue(trxValue);

		return trxResult;
	}

}
