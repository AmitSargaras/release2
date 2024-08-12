package com.integrosys.cms.app.function.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerSubmitOperationCreate extends AbstractTeamFunctionGrpTrxOperation {
	public MakerSubmitOperationCreate() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SUBMIT_TEAM_FUNCTION_GRP_CREATE;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITeamFunctionGrpTrxValue trxValue = createStagingTeamFunctionGrp(getTeamFunctionGrpTrxValue(anITrxValue));

		trxValue = createTeamFunctionGrpTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
