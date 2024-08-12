package com.integrosys.cms.app.function.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class SystemCloseOperation extends AbstractTeamFunctionGrpTrxOperation {

	private static final long serialVersionUID = -5362173114522477878L;

	public SystemCloseOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_TEAM_FUNCTION_GRP;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITeamFunctionGrpTrxValue trxValue = super.getTeamFunctionGrpTrxValue(anITrxValue);
		trxValue = updateTeamFunctionGrpTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
