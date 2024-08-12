package com.integrosys.cms.app.function.trx;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerApproveUpdateOperation extends AbstractTeamFunctionGrpTrxOperation {
	public CheckerApproveUpdateOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_TEAM_FUNCTION_GRP;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ITeamFunctionGrpTrxValue trxValue = getTeamFunctionGrpTrxValue(anITrxValue);
//		trxValue = createStagingTeamFunctionGrp(trxValue);
		trxValue = createUpdateActualTeamFunctionGrp(trxValue);
		trxValue = updateTeamFunctionGrpTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	private ITeamFunctionGrpTrxValue createUpdateActualTeamFunctionGrp(
			ITeamFunctionGrpTrxValue anITeamFunctionGrpTrxValue) throws TrxOperationException {
		try {
			List staging = anITeamFunctionGrpTrxValue.getStagingTeamFunctionGrps();
			List actual = anITeamFunctionGrpTrxValue.getTeamFunctionGrps();

			if (actual == null) {
				actual = new ArrayList();
			}

			List updatedActualTeamFunctionGrp = getTeamFunctionGrpBusManager().updateToWorkingCopy(actual, staging);

			anITeamFunctionGrpTrxValue.setTeamFunctionGrps(updatedActualTeamFunctionGrp);

			return anITeamFunctionGrpTrxValue;
		}
		/*
		 * catch (TeamFunctionGrpException ex) { throw new
		 * TrxOperationException(ex); }
		 */
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualTeamFunctionGrp(): " + ex.toString());
		}
	}
}
