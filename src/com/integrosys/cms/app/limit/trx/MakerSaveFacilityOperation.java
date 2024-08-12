package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Transaction operation for maker saving copy into draft.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public class MakerSaveFacilityOperation extends AbstractFacilityTrxOperation {

	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		String fromState = value.getFromState();
		value = super.preProcess(value);

		if (value.getToState().equals(value.getFromState()) && ICMSConstant.STATE_DRAFT.equals(value.getToState())) {
			/*
			 * set back the from state to 'ND', prevent fromState = 'DRAFT' and
			 * also toState = 'DRAFT'
			 */
			value.setFromState(fromState);
		}

		return value;
	}

	public String getOperationName() {
		return FacilityTrxController.ACTION_MAKER_SAVE;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		IFacilityTrxValue trxValue = (IFacilityTrxValue) value;

		trxValue = createStagingFacilityMaster(trxValue);
		if (value.getTransactionID() != null) {
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
