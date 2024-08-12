package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Transaction operation for checker approve submitted facility master
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckerApproveFacilityOperation extends AbstractFacilityTrxOperation {

	private static final long serialVersionUID = -4098550591025537485L;

	private String operationName = FacilityTrxController.ACTION_CHECKER_APPROVE;

	public String getOperationName() {
		return this.operationName;
	}

	/**
	 * <p>
	 * To set the operation name for this checker approve facility workflow,
	 * which then to be used by workflow engine.
	 * <p>
	 * Default value would be
	 * {@link FacilityTrxController#ACTION_CHECKER_APPROVE}
	 * @param operationName the operation name for this workflow
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		IFacilityTrxValue trxValue = (IFacilityTrxValue) value;

		trxValue = updateActualFacilityMasterFromStaging(trxValue);
		trxValue = updateTransaction(trxValue);

		OBCMSTrxResult trxResult = new OBCMSTrxResult();
		trxResult.setTrxValue(trxValue);

		return trxResult;
	}
}
