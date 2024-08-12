package com.integrosys.cms.app.function.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerUpdateRejectedOperation extends AbstractTeamFunctionGrpTrxOperation {
	public MakerUpdateRejectedOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_TEAM_FUNCTION_GRP;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITeamFunctionGrpTrxValue trxValue = createStagingTeamFunctionGrp(getTeamFunctionGrpTrxValue(anITrxValue));
		trxValue = updateTeamFunctionGrpTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
